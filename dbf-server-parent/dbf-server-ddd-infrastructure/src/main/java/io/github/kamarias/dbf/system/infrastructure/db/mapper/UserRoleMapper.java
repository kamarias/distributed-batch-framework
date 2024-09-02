package io.github.kamarias.dbf.system.infrastructure.db.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import io.github.kamarias.dbf.system.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {


    /**
     * 通过用户Id获取拥有的角色编码
     *
     * @param userId 用户Id
     * @return 返回的权限编码
     */
    @Select("select tr.code " +
            "from t_user tu join t_user_role tur on tu.id = tur.user_id " +
            "join t_role tr on tur.role_id = tr.id " +
            "where tu.del_flag = 0 and tu.id = #{userId}")
    List<String> getRoleCodeByUserId(@Param("userId") String userId);


    /**
     * 通过用户Id获取拥有的角色Id
     *
     * @param userId 用户Id
     * @return 返回的权限Id
     */
    @Select("select tr.id " +
            "from t_user tu join t_user_role tur on tu.id = tur.user_id " +
            "join t_role tr on tur.role_id = tr.id " +
            "where tu.del_flag = 0 and tr.del_flag = 0 and tu.id = #{userId}")
    Set<String> getRoleIdsByUserId(@Param("userId") String userId);



    int batchInsert(List<UserRoleEntity> userRoleEntityList);

    /**
     * 查询角色可选选项
     * @return 返回可选选项
     */
    @Select("select r.id, r.name, r.code, r.description " +
            "from t_role r join t_user_role tur on r.id = tur.role_id " +
            "where del_flag = 0 and tur.user_id = #{userId}")
    List<RoleEntity> findRoleListByUserId(@Param("userId") String userId);


}
