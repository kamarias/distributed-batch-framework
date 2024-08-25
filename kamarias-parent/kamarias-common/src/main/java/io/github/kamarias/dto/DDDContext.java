package io.github.kamarias.dto;

public class DDDContext<Q, R> {

    // 成功状态码
    private static final Integer SUCCESS_CODE = 0;

    // 错误状态码
    private static final Integer ERROR_CODE = 1;

    /**
     * 返回码状态
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 请求参数
     */
    private ContextRequest<Q> request;

    /**
     * 响应参数
     */
    private ContextResponse<R> response;


    public static <Q, R> DDDContext<Q, R> success() {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", null, null);
    }

    public static <Q, R> DDDContext<Q, R> success(String msg) {
        return new DDDContext<>(SUCCESS_CODE, msg, null, null);
    }

    public static <Q, R> DDDContext<Q, R> successReq(Q request) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", ContextRequest.create(request), null);
    }


    public static <Q, R> DDDContext<Q, R> successReq(String msg, Q request) {
        return new DDDContext<>(SUCCESS_CODE, msg, ContextRequest.create(request), null);
    }

    public static <Q, R> DDDContext<Q, R> successResp(R response) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", null, ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> successResp(String msg, R response) {
        return new DDDContext<>(SUCCESS_CODE, msg, null, ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> success( Q request, R response) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", ContextRequest.create(request), ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> success(String msg, Q request, R response) {
        return new DDDContext<>(SUCCESS_CODE, msg, ContextRequest.create(request), ContextResponse.create(response));
    }


    public static <Q, R> DDDContext<Q, R> error() {
        return new DDDContext<>(ERROR_CODE, "未知错误", null, null);
    }

    public static <Q, R> DDDContext<Q, R> error(String msg) {
        return new DDDContext<>(ERROR_CODE, msg, null, null);
    }

    public static <Q, R> DDDContext<Q, R> errorReq(Q request) {
        return new DDDContext<>(ERROR_CODE, "未知错误", ContextRequest.create(request), null);
    }


    public static <Q, R> DDDContext<Q, R> errorReq(String msg, Q request) {
        return new DDDContext<>(ERROR_CODE, msg, ContextRequest.create(request), null);
    }

    public static <Q, R> DDDContext<Q, R> errorResp(R response) {
        return new DDDContext<>(ERROR_CODE, "未知错误", null, ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> errorResp(String msg, R response) {
        return new DDDContext<>(ERROR_CODE, msg, null, ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> error( Q request, R response) {
        return new DDDContext<>(ERROR_CODE, "未知错误", ContextRequest.create(request), ContextResponse.create(response));
    }

    public static <Q, R> DDDContext<Q, R> error(String msg, Q request, R response) {
        return new DDDContext<>(ERROR_CODE, msg, ContextRequest.create(request), ContextResponse.create(response));
    }

    /**
     * 是否处理成功
     *
     * @return 返回处理结果
     */
    public boolean isSuccess() {
        if (this.getCode() == null) {
            return false;
        }
        return SUCCESS_CODE.equals(this.getCode());
    }

    /**
     * 是否处理失败
     *
     * @return 返回处理结果
     */
    public boolean isError() {
        return !isSuccess();
    }


    public Integer getCode() {
        return code;
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContextRequest<Q> getRequest() {
        return request;
    }

    private void setRequest(ContextRequest<Q> request) {
        this.request = request;
    }

    public ContextResponse<R> getResponse() {
        return response;
    }

    private void setResponse(ContextResponse<R> response) {
        this.response = response;
    }

    private DDDContext(Integer code, String msg, ContextRequest<Q> request, ContextResponse<R> response) {
        this.code = code;
        this.msg = msg;
        this.request = request;
        this.response = response;
    }

}
