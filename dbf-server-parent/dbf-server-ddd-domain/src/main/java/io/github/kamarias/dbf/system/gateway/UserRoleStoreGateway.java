package io.github.kamarias.dbf.system.gateway;

import java.util.Set;

public interface UserRoleStoreGateway {

    /**
     * 维护用户角色信息
     *
     * @param userId 用户ID，用于标识需要维护角色信息的用户
     * @param roles  用户的新角色集合，用于更新用户的角色信息
     * @return 返回true表示用户角色信息维护成功；返回false表示维护失败
     */
    boolean maintainUserRole(String userId, Set<String> roles);

}
