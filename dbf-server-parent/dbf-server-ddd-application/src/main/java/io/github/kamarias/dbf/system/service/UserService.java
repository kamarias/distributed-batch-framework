package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.UserServiceTranslate;
import io.github.kamarias.dto.ContextResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDomainService userDomain;

    private final UserServiceTranslate translate;

    public UserService(UserDomainService userDomain, UserServiceTranslate translate) {
        this.userDomain = userDomain;
        this.translate = translate;
    }

    public ContextResponse<Void> creatUser(UserContext context) {
        // 检验添加用户
        UserModel model = translate.toUserModelByUserContext(context);
        ContextResponse<Void> accountExists = userDomain.createUserVerify(model);
        if (accountExists.isError()) {
            return accountExists;
        }
        ContextResponse<Void> addUser = userDomain.createUser(model);
        if (addUser.isError()) {
            return addUser;
        }
        return ContextResponse.success();
    }
}
