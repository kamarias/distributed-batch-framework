package io.github.kamarias.dto;

/**
 * DDD上下文类，用于封装请求和响应信息
 * 提供了成功和错误上下文的创建方法
 *
 * @param <Q> 请求参数类型
 * @param <R> 响应参数类型
 */
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

    /**
     * 创建一个成功的DDDContext对象
     * @return 返回一个成功状态的DDDContext对象，带有默认消息和空的请求与响应
     */
    public static <Q, R> DDDContext<Q, R> success() {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", null, null);
    }

    /**
     * 创建一个带有自定义消息的成功DDDContext对象
     * @param msg 自定义的成功消息
     * @return 返回一个成功状态的DDDContext对象，带有自定义消息和空的请求与响应
     */
    public static <Q, R> DDDContext<Q, R> success(String msg) {
        return new DDDContext<>(SUCCESS_CODE, msg, null, null);
    }

    /**
     * 创建一个包含请求的成功的DDDContext对象
     * @param request 请求参数
     * @return 返回一个成功状态的DDDContext对象，带有请求参数和空的响应
     */
    public static <Q, R> DDDContext<Q, R> request(Q request) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", ContextRequest.create(request), null);
    }

    /**
     * 创建一个包含请求和自定义消息的成功的DDDContext对象
     * @param msg 自定义消息
     * @param request 请求参数
     * @return 返回一个成功状态的DDDContext对象，带有请求参数和自定义消息
     */
    public static <Q, R> DDDContext<Q, R> request(String msg, Q request) {
        return new DDDContext<>(SUCCESS_CODE, msg, ContextRequest.create(request), null);
    }

    /**
     * 创建一个包含响应的成功的DDDContext对象
     * @param response 响应参数
     * @return 返回一个成功状态的DDDContext对象，带有响应参数和空的请求
     */
    public static <Q, R> DDDContext<Q, R> success(R response) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", null, ContextResponse.create(response));
    }

    /**
     * 创建一个带有自定义消息和响应的成功的DDDContext对象
     * @param msg 自定义消息
     * @param response 响应参数
     * @return 返回一个成功状态的DDDContext对象，带有自定义消息和响应参数
     */
    public static <Q, R> DDDContext<Q, R> success(String msg, R response) {
        return new DDDContext<>(SUCCESS_CODE, msg, null, ContextResponse.create(response));
    }

    /**
     * 创建一个包含请求和响应的成功的DDDContext对象
     * @param request 请求参数
     * @param response 响应参数
     * @return 返回一个成功状态的DDDContext对象，带有请求和响应参数
     */
    public static <Q, R> DDDContext<Q, R> success(Q request, R response) {
        return new DDDContext<>(SUCCESS_CODE, "处理成功", ContextRequest.create(request), ContextResponse.create(response));
    }

    /**
     * 创建一个带有自定义消息、请求和响应的成功的DDDContext对象
     * @param msg 自定义消息
     * @param request 请求参数
     * @param response 响应参数
     * @return 返回一个成功状态的DDDContext对象，带有自定义消息、请求和响应参数
     */
    public static <Q, R> DDDContext<Q, R> success(String msg, Q request, R response) {
        return new DDDContext<>(SUCCESS_CODE, msg, ContextRequest.create(request), ContextResponse.create(response));
    }

    /**
     * 创建一个错误的DDDContext对象
     * @return 返回一个错误状态的DDDContext对象，带有默认消息和空的请求与响应
     */
    public static <Q, R> DDDContext<Q, R> error() {
        return new DDDContext<>(ERROR_CODE, "未知错误", null, null);
    }

    /**
     * 创建一个带有自定义消息的错误DDDContext对象
     * @param msg 自定义的错误消息
     * @return 返回一个错误状态的DDDContext对象，带有自定义消息和空的请求与响应
     */
    public static <Q, R> DDDContext<Q, R> error(String msg) {
        return new DDDContext<>(ERROR_CODE, msg, null, null);
    }

    /**
     * 创建一个包含响应的错误DDDContext对象
     * @param response 响应参数
     * @return 返回一个错误状态的DDDContext对象，带有未知错误消息和响应参数
     */
    public static <Q, R> DDDContext<Q, R> error(R response) {
        return new DDDContext<>(ERROR_CODE, "未知错误", null, ContextResponse.create(response));
    }

    /**
     * 创建一个带有自定义消息和响应的错误DDDContext对象
     * 此方法专用于处理需要返回错误信息和相关响应数据的情况
     *
     * @param msg 自定义的错误消息
     * @param response 响应数据
     * @return 返回一个错误状态的DDDContext对象，带有自定义消息和响应参数
     */
    public static <Q, R> DDDContext<Q, R> error(String msg, R response) {
        return new DDDContext<>(ERROR_CODE, msg, null, ContextResponse.create(response));
    }

    /**
     * 创建一个默认消息错误的 DDDContext 上下文，包含请求和响应
     * @param request 请求参数
     * @param response 响应参数
     * @return 返回错误的响应上下文
     */
    public static <Q, R> DDDContext<Q, R> error(Q request, R response) {
        return new DDDContext<>(ERROR_CODE, "未知错误", ContextRequest.create(request), ContextResponse.create(response));
    }

    /**
     * 创建一个错误的 DDDContext 上下文，包含自定义消息、请求和响应
     * @param msg 错误消息
     * @param request 请求参数
     * @param response 响应参数
     * @return 返回错误的响应上下文
     */
    public static <Q, R> DDDContext<Q, R> error(String msg, Q request, R response) {
        return new DDDContext<>(ERROR_CODE, msg, ContextRequest.create(request), ContextResponse.create(response));
    }

    /**
     * 判断处理是否成功
     *
     * @return 返回处理结果，true表示成功，false表示失败
     */
    public boolean isSuccess() {
        if (this.getCode() == null) {
            return false;
        }
        return SUCCESS_CODE.equals(this.getCode());
    }

    /**
     * 判断处理是否失败
     *
     * @return 返回处理结果，true表示失败，false表示成功
     */
    public boolean isError() {
        return !isSuccess();
    }

    // Getters and private Setters

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

    // Private constructor for object creation restriction
    private DDDContext(Integer code, String msg, ContextRequest<Q> request, ContextResponse<R> response) {
        this.code = code;
        this.msg = msg;
        this.request = request;
        this.response = response;
    }
}
