package io.github.kamarias.dbf.system.infrastructure.db.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

//
//    /**
//     * 查询角色列表
//     *
//     * @param page    分页参数
//     * @param wrapper 查询条件
//     * @return 返回翻译结果
//     */
//    @Select("select r.id,r.name,r.code,r.status,r.description,r.create_by,r.create_time,r.update_by,r.update_time,r.del_flag " +
//            "from t_role r join t_role_permission rp on r.id = rp.role_id " +
//            "join t_permission p on rp.permission_id = p.id ${ew.customSqlSegment}")
//    Page<RoleEntity> getList(Page<RoleEntity> page, @Param(Constants.WRAPPER) QueryWrapper<RoleEntity> wrapper);
//
//
//    /**
//     * 查询角色可选选项
//     * @return 返回可选选项
//     */
//    @Select("select r.id, r.name, r.code, r.description " +
//            "from t_role r " +
//            "where del_flag = 0")
//    List<RoleOptionsVO> getRoleOptions();
//
//    /**
//     * 查询角色可选选项
//     * @return 返回可选选项
//     */
//    @Select("select r.id, r.name, r.code, r.description " +
//            "from t_role r join t_user_role tur on r.id = tur.role_id " +
//            "where del_flag = 0 and tur.user_id = #{userId}")
//    List<RoleOptionsVO> getRoleOptionsByUserId(@Param("userId") String userId);

}
