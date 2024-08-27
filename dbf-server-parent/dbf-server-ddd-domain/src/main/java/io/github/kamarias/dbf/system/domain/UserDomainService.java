package io.github.kamarias.dbf.system.domain;

import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.gateway.UserRoleStoreGateway;
import io.github.kamarias.dbf.system.gateway.UserStoreGateway;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.UserDomainTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.utils.regular.RegularUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDomainService {


    private final UserDomainTranslate translate;

    private final UserStoreGateway userStoreGateway;

    private final UserRoleStoreGateway userRoleStoreGateway;


    public UserDomainService(UserDomainTranslate translate, UserStoreGateway userStoreGateway, UserRoleStoreGateway userRoleStoreGateway) {
        this.translate = translate;
        this.userStoreGateway = userStoreGateway;
        this.userRoleStoreGateway = userRoleStoreGateway;
    }


    /**
     * 根据账号查找用户
     * 该方法支持通过电话号码、邮箱或账号名进行用户查找
     * 利用正则表达式判断输入的账号类型，从而进行不同类型的查询
     *
     * @param account 用户账号，可以是电话号码、邮箱或账号名
     * @return 返回UserModel对象，如果没有找到匹配的用户，则返回 null
     */
    public DDDContext<String, UserModel> findUser(String account) {
        UserDto userDto;
        if (RegularUtils.verifyPhoneNumber(account)) {
            userDto = userStoreGateway.selectUserByPhone(account);
        } else if (RegularUtils.verifyMail(account)) {
            userDto = userStoreGateway.selectUserByEmail(account);
        } else {
            userDto = userStoreGateway.selectUserByAccount(account);
        }
        return Objects.isNull(userDto) ? DDDContext.error("查询用户不存在") : DDDContext.success(translate.toUserModelByUserDto(userDto));
    }


    public DDDContext<Void, Void> matchesPassword(String loginPassWord, String databasePassWord) {
        // @TODO 后续引入加密算法，当前数据库密钥先不加密

        return loginPassWord.equals(databasePassWord) ?
                DDDContext.success() : DDDContext.error("密码匹配失败");
    }

    public DDDContext<Void, Void> createUserVerify(UserModel model) {
        if (userStoreGateway.phoneExists(model.getPhone())) {
            return DDDContext.error("电话号码已存在");
        }
        if (userStoreGateway.accountExists(model.getAccount())) {
            return DDDContext.error("账号已存在");
        }
        if (userStoreGateway.emailExists(model.getEmail())) {
            return DDDContext.error("邮箱已存在");
        }
        return DDDContext.success();
    }


    public DDDContext<Void, Void> createUser(UserModel model) {
        UserDto userDto = translate.toUserDtoByUserModel(model);
        if (!userStoreGateway.creatUser(userDto)) {
            return DDDContext.error("创建用户失败");
        }
        // 权限信息后续补充
        Set<String> roleIds = new HashSet<>();
        userRoleStoreGateway.maintainUserRole(model.getId(), roleIds);
        return DDDContext.success();
    }

    public DDDContext<Void, List<RoleModel>> findUserRole(String userId) {
        List<RoleDto> roles = userRoleStoreGateway.findRoleListByAccount(userId);
        List<RoleModel> rms = translate.toRoleModelListByRoleDtoList(roles);
        return DDDContext.success(rms);
    }
}
