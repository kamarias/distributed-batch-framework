package io.github.kamarias.dbf;

import io.github.kamarias.dbf.annotation.EnableProcessor;
import io.github.kamarias.dbf.annotation.Processor;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class ProcessorComponentRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {


    private Environment environment;

    private ResourceLoader resourceLoader;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(EnableProcessor.class.getName());
        Assert.notNull(annotationAttributes, "EnableProcessor Class Is Null");
        String[] processorBasePackages = (String[]) annotationAttributes.get("scanBasePackages");
        Set<String> scanBasePackages = new HashSet<>(Arrays.asList(processorBasePackages));
        if (scanBasePackages.isEmpty()) {
            scanBasePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        }

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider() {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition definition) {
                return definition.getMetadata().hasAnnotation(Processor.class.getName()) &&
                        Objects.nonNull(definition.getMetadata().getAnnotationAttributes(Processor.class.getName())) &&
                        StringUtils.hasText(String.valueOf(definition.getMetadata().getAnnotationAttributes(Processor.class.getName()).get("value")));
            }
        };

        scanner.setEnvironment(environment);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Processor.class));
        for (String basePackage : scanBasePackages) {
            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                if (beanDefinition instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition definition = (AnnotatedBeanDefinition) beanDefinition;
                    Map<String, Object> attributes = definition.getMetadata().getAnnotationAttributes(Processor.class.getName());
                    Assert.notNull(attributes, "Processor Class Is Null");
                    definition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
                    registry.registerBeanDefinition(String.valueOf(attributes.get("value")), definition);
                }

            }
        }

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
