package io.github.kamarias.dbf.system.infrastructure.db.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.dbf.system.entity.RouterMetaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface RouterMetaMapper extends BaseMapper<RouterMetaEntity> {


    /**
     * 通过路由Id查询路由源数据
     *
     * @param routerId 路由Id
     * @return 路由源数据
     */
    RouterMetaEntity findByRouterIdRouter(@Param("routerId") String routerId);

}
