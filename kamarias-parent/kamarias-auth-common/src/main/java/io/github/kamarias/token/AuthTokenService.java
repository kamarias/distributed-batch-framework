package io.github.kamarias.token;

import io.github.kamarias.bean.AuthLogin;

public interface AuthTokenService {

    <T extends AuthLogin> String createToken(T login);

    boolean deleteToken();

    boolean deleteToken(String token);

    <T extends AuthLogin> T analyzeToken();

    <T extends AuthLogin> T analyzeToken(String str);

    <T extends AuthLogin> void renewalToken(String token);

}
