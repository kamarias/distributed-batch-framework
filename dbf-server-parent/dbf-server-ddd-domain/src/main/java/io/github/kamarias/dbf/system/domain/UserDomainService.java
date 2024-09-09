package io.github.kamarias.dbf.system.domain;

import io.github.kamarias.dbf.enums.BoolFlagEnum;
import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.gateway.PermissionStoreGateway;
import io.github.kamarias.dbf.system.gateway.RoleStoreGateway;
import io.github.kamarias.dbf.system.gateway.UserRoleStoreGateway;
import io.github.kamarias.dbf.system.gateway.UserStoreGateway;
import io.github.kamarias.dbf.system.model.QueryUserModel;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.model.UserTableModel;
import io.github.kamarias.dbf.system.translate.UserDomainTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.utils.regular.RegularUtils;
import io.github.kamarias.vo.PageVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecution;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.config.TransactionManagementConfigUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDomainService {


    private final UserDomainTranslate translate;

    private final UserStoreGateway userStoreGateway;

    private final UserRoleStoreGateway userRoleStoreGateway;

    private final RoleStoreGateway roleStoreGateway;

    private final PermissionStoreGateway permissionStoreGateway;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public UserDomainService(UserDomainTranslate translate, UserStoreGateway userStoreGateway, UserRoleStoreGateway userRoleStoreGateway, RoleStoreGateway roleStoreGateway, PermissionStoreGateway permissionStoreGateway) {
        this.translate = translate;
        this.userStoreGateway = userStoreGateway;
        this.userRoleStoreGateway = userRoleStoreGateway;
        this.roleStoreGateway = roleStoreGateway;
        this.permissionStoreGateway = permissionStoreGateway;
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
        boolean matches = bCryptPasswordEncoder.matches(loginPassWord, databasePassWord);
        return matches ? DDDContext.success() : DDDContext.error("密码匹配失败");
    }

    public DDDContext<Void, Void> updateUserVerify(UserModel model) {
        UserDto userDto = userStoreGateway.selectUserByUserId(model.getId());
        if (!model.getPhone().equals(userDto.getPhone()) && userStoreGateway.phoneExists(model.getPhone())) {
            return DDDContext.error("电话号码已存在");
        }
        if (!model.getAccount().equals(userDto.getAccount()) && userStoreGateway.accountExists(model.getAccount())) {
            return DDDContext.error("账号已存在");
        }
        if (!model.getEmail().equals(userDto.getEmail()) && userStoreGateway.emailExists(model.getEmail())) {
            return DDDContext.error("邮箱已存在");
        }
        return DDDContext.success();
    }

    public DDDContext<Void, Void> insertUserVerify(UserModel model) {
        if (userStoreGateway.phoneExists(model.getPhone())) {
            return DDDContext.error("电话号码已存在");
        }
        if (userStoreGateway.accountExists(model.getAccount())) {
            return DDDContext.error("账号已存在");
        }
        if (userStoreGateway.emailExists(model.getEmail())) {
            return DDDContext.error("邮箱已存在");
        }
        if (userStoreGateway.emailExists(model.getPassWord())) {
            return DDDContext.error("用户密码为空");
        }
        return DDDContext.success();
    }


    @Transactional(rollbackFor = Exception.class)
    public DDDContext<Void, Void> createUser(UserModel model) {
        UserDto userDto = translate.toUserDtoByUserModel(model);
        userDto.setPassWord(encryptPassword(model.getPassWord()));
        userDto = userStoreGateway.creatUser(userDto);
        if (Objects.isNull(userDto)) {

            return DDDContext.error("创建用户失败");
        }
        // 权限信息后续补充
        userRoleStoreGateway.maintainUserRole(userDto.getId(), model.getRoleIds());
        return DDDContext.success();
    }

    public DDDContext<Void, List<RoleModel>> findUserRole(String userId) {
        List<RoleDto> roles = userRoleStoreGateway.findRoleListByAccount(userId);
        List<RoleModel> rms = translate.toRoleModelListByRoleDtoList(roles);
        return DDDContext.success(rms);
    }


    public DDDContext<Void, RoleModel> randomGetRoleByUserId(String userId) {
        RoleDto dto = roleStoreGateway.randomFindRoleByUserId(userId);
        if (Objects.isNull(dto)) {
            return DDDContext.error("改用户未绑定角色，请联系管理员");
        }
        RoleModel roleModel = translate.toRoleModelByRoleDto(dto);
        return DDDContext.success(roleModel);
    }

    public DDDContext<Void, RoleModel> getRoleByUserIdAndRoleId(String userId, String roleId) {
        RoleDto dto = roleStoreGateway.findRoleByUserIdAndRoleId(userId, roleId);
        if (Objects.isNull(dto)) {
            return DDDContext.error("该用户没有当前角色，请联系管理员");
        }
        RoleModel roleModel = translate.toRoleModelByRoleDto(dto);
        return DDDContext.success(roleModel);
    }

    public DDDContext<Void, Set<String>> getPermissionCodesByRoleId(String roleId) {
        Set<String> permissionCodeByRoleId = permissionStoreGateway.getPermissionCodeByRoleId(roleId);
        return Objects.isNull(permissionCodeByRoleId) ? DDDContext.success(new HashSet<>())
                : DDDContext.success(permissionCodeByRoleId);
    }

    public DDDContext<Void, Boolean> resetPassword(String userId, String passWord) {
        UserDto userDto = userStoreGateway.selectUserByUserId(userId);
        if (Objects.isNull(userDto)) {
            return DDDContext.error("用户不存在");
        }
        userDto.setPassWord(encryptPassword(passWord));
        Boolean bol = userStoreGateway.updateUser(userDto);
        return bol ? DDDContext.success(true) : DDDContext.error("更新密码失败");
    }


    public DDDContext<QueryUserModel, PageVO<UserTableModel>> queryUserTableList(QueryUserModel qum) {
        PageVO<UserDto> a = userStoreGateway.queryUserTableList(qum);
        if (Objects.isNull(a)) {
            return DDDContext.error("查询数据异常");
        }
        PageVO<UserTableModel> page = translate.toUserTableModelPageByUserDtoPage(a);
        return DDDContext.success(page);
    }

    private String encryptPassword(String passWord) {
        return bCryptPasswordEncoder.encode(passWord);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户id
     * @return
     */
    public DDDContext<Void, Boolean> updateUserStatus(String userId) {
        UserDto userDto = userStoreGateway.selectUserByUserId(userId);
        if (Objects.isNull(userDto)) {
            return DDDContext.error("用户不存在");
        }
        userDto.setStatus(BoolFlagEnum.YES.getCode().equals(userDto.getStatus()) ? BoolFlagEnum.NOT.getCode() : BoolFlagEnum.YES.getCode());
        Boolean bol = userStoreGateway.updateUser(userDto);
        return bol ? DDDContext.success(true) : DDDContext.error("更新用户状态失败");

    }

    @Transactional(rollbackFor = Exception.class)
    public DDDContext<Void, Boolean> deleteUser(String userId) {
        boolean bol = userStoreGateway.deleteUserByUserId(userId);
        if (!bol) {
            return DDDContext.error("删除用户失败");
        }
        userRoleStoreGateway.removeUserRoleByUserId(userId);
        return DDDContext.success(true);
    }


    public DDDContext<Void, Void> updateUser(UserModel model) {
        UserDto userDto = userStoreGateway.selectUserByUserId(model.getId());
        if (Objects.isNull(userDto)) {
            return DDDContext.error("更新用户不存在");
        }
        userDto = translate.toUserDtoByUserModel(model);
        Boolean bol = userStoreGateway.updateUser(userDto);
        if (Objects.isNull(bol) || !bol) {
            return DDDContext.error("更新用户失败");
        }
        // 权限信息后续补充
        boolean b = userRoleStoreGateway.maintainUserRole(userDto.getId(), model.getRoleIds());
        return b ? DDDContext.success() : DDDContext.error("更新用户失败");
    }


    public DDDContext<Void, UserModel> getUserInfo(String userId) {
        UserDto userDto = userStoreGateway.selectUserByUserId(userId);
        if (Objects.isNull(userDto)) {
            return DDDContext.error("用户不存在");
        }
        UserModel model = translate.toUserModelByUserDto(userDto);

        Set<String> roleIds = userRoleStoreGateway.getRoleIdsByUserId(userId);
        model.setRoleIds(roleIds);

        return DDDContext.success(model);
    }


}
