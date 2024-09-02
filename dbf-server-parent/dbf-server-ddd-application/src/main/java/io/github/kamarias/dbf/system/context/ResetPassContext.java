package io.github.kamarias.dbf.system.context;

public class ResetPassContext {

    /**
     * 重置用户
     */
    private String userId;

    /**
     * 重置密码
     */
    private String passWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
