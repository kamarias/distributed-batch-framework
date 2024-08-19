package io.github.kamarias.dbf.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.kamarias.dbf.event.DbfExecuteAddEvent;
import io.github.kamarias.dbf.registry.DefaultExecutorInstance;
import io.github.kamarias.dbf.registry.RegistryService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class DbfExecuteAddListener implements ApplicationListener<DbfExecuteAddEvent> {


    private final RegistryService registryService;

    public DbfExecuteAddListener(RegistryService registryService) {
        this.registryService = registryService;
    }


    @Override
    public void onApplicationEvent(DbfExecuteAddEvent event) {
        CuratorFramework client = event.getClient();
        TreeCacheEvent treeCacheEvent = event.getEvent();
        String[] split = treeCacheEvent.getData().getPath().split("/");
        String string = new String(treeCacheEvent.getData().getData(), StandardCharsets.UTF_8);
        Map<String, String> metadata = JSON.parseObject(string, new TypeReference<Map<String, String>>() {
        });
        DefaultExecutorInstance instance = new DefaultExecutorInstance(split[2], split[3], split[4], metadata);
        registryService.register(instance);
    }

}
