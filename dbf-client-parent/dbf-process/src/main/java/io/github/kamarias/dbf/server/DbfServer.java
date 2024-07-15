package io.github.kamarias.dbf.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;


import java.util.Map;
import java.util.concurrent.*;

public class DbfServer {


    private Thread thread;


    public void start(final String address, final int port) {
        thread = new Thread(() -> {
            // param
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(
                    0,
                    200,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(2000),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r, "xxl-job, EmbedServer bizThreadPool-" + r.hashCode());
                        }
                    },
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            throw new RuntimeException("xxl-job, EmbedServer bizThreadPool is EXHAUSTED!");
                        }
                    });
            try {

                // start server
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline()
                                        .addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS))  // beat 3N, close if idle
                                        .addLast(new HttpServerCodec())
                                        .addLast(new HttpObjectAggregator(5 * 1024 * 1024))  // merge request & reponse to FULL
                                        .addLast(new EmbedHttpServerHandler( "accessToken", bizThreadPool));
                            }
                        })
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                // bind
                ChannelFuture future = bootstrap.bind(port).sync();

//                logger.info(">>>>>>>>>>> xxl-job remoting server start success, nettype = {}, port = {}", EmbedServer.class, port);

                // start registry
//                startRegistry(appname, address);

                // wait util stop
                future.channel().closeFuture().sync();

            } catch (InterruptedException e) {
//                logger.info(">>>>>>>>>>> xxl-job remoting server stop.");
            } catch (Exception e) {
//                logger.error(">>>>>>>>>>> xxl-job remoting server error.", e);
            } finally {
                // stop
                try {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
                }
            }
        });
        thread.setDaemon(true);    // daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
        thread.start();
    }

    /**
     * netty_http
     * <p>
     * Copy from : https://github.com/xuxueli/xxl-rpc
     *
     * @author xuxueli 2015-11-24 22:25:15
     */
    public static class EmbedHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        private String accessToken;
        private ThreadPoolExecutor bizThreadPool;

        private final String a = "{\"code\"：500,\"msg\": \"invalid request, HttpMethod not support.\"}";
        private final String b = "{\"code\"：500,\"msg\": \"invalid request, uri-mapping empty.\"}";
        private final String c = "{\"code\"：500,\"msg\": \"The access token is wrong.\"}";

        public EmbedHttpServerHandler(String accessToken, ThreadPoolExecutor bizThreadPool) {
            this.accessToken = accessToken;
            this.bizThreadPool = bizThreadPool;
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            // request parse
            //final byte[] requestBytes = ByteBufUtil.getBytes(msg.content());    // byteBuf.toString(io.netty.util.CharsetUtil.UTF_8);
            String requestData = msg.content().toString(CharsetUtil.UTF_8);
            String uri = msg.uri();
            HttpMethod httpMethod = msg.method();
            boolean keepAlive = HttpUtil.isKeepAlive(msg);
//            String accessTokenReq = msg.headers().get(XxlJobRemotingUtil.XXL_JOB_ACCESS_TOKEN);
            String accessTokenReq = "ffggf";

            // invoke
            bizThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // do invoke
                    Object responseObj = process(httpMethod, uri, requestData, accessTokenReq);

                    // to json
//                    String responseJson = GsonTool.toJson(responseObj);
                    String responseJson = JSON.toJSONString(responseObj);

                    // write response
                    writeResponse(ctx, keepAlive, responseJson);
                }
            });
        }

        private Object process(HttpMethod httpMethod, String uri, String requestData, String accessTokenReq) {
            // valid
            if (HttpMethod.POST != httpMethod) {
                return JSONObject.parseObject(a, Map.class);
            }
            if (uri == null || uri.trim().length() == 0) {
                return JSONObject.parseObject(b, Map.class);
            }
            if (accessToken != null
                    && accessToken.trim().length() > 0
                    && !accessToken.equals(accessTokenReq)) {
                return c;
            }

            // services mapping
            try {
                switch (uri) {
                    case "/idleBeat":
                        Map<String, Object> idleBeatParam = JSON.parseObject(requestData, Map.class);

                        return "executorBiz.idleBeat(idleBeatParam)";
                    default:
                        return "invalid request, uri-mapping(" + uri + ") not found.";
                }
            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                return new ReturnT<String>(ReturnT.FAIL_CODE, "request error:" + ThrowableUtil.toString(e));
                return "ghjh";
            }
        }

        /**
         * write response
         */
        private void writeResponse(ChannelHandlerContext ctx, boolean keepAlive, String responseJson) {
            // write response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(responseJson, CharsetUtil.UTF_8));   //  Unpooled.wrappedBuffer(responseJson)
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");       // HttpHeaderValues.TEXT_PLAIN.toString()
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.writeAndFlush(response);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//            logger.error(">>>>>>>>>>> xxl-job provider netty_http server caught exception", cause);
            ctx.close();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                ctx.channel().close();      // beat 3N, close if idle
//                logger.debug(">>>>>>>>>>> xxl-job provider netty_http server close an idle channel.");
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

}
