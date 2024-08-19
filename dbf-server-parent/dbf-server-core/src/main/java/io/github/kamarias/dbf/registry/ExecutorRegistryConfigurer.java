package io.github.kamarias.dbf.registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ExecutorRegistryConfigurer {


    @Bean(value = "dbfRegistryService")
    public RegistryService registryService() {
        return new DefaultRegistryService();
    }

}
