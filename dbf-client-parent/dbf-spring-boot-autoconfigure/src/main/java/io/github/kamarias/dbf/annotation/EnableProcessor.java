package io.github.kamarias.dbf.annotation;

import io.github.kamarias.dbf.ProcessorComponentRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ProcessorComponentRegistrar.class)
public @interface EnableProcessor {

    String[] scanBasePackages() default {};

}
