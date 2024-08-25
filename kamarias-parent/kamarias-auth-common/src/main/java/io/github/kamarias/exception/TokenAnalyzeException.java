package io.github.kamarias.exception;

public class TokenAnalyzeException extends RuntimeException {

    private Integer code;

    public TokenAnalyzeException(String msg) {
        super(msg);
    }

    public TokenAnalyzeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
