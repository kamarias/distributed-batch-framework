package io.github.kamarias.dbf.app;


import io.github.kamarias.dbf.process.Command;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;


import java.util.Map;

//@RestController
public class ProcessController {

    private final ApplicationContext applicationContext;

    public ProcessController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

//    @PostMapping("/start")
//    public String start(@RequestBody Map<String, Object> map) {
    public String start(Map<String, Object> map) {
        String taskName = String.valueOf(map.get("taskName"));
        Object a = null;
        try {
            a = applicationContext.getBean(taskName);
        } catch (NoSuchBeanDefinitionException e) {
            return "start if Fail";
        }

        if (a instanceof Command) {
            ((Command) a).start(map);
            return "ok";
        }
        return "start if Fail";
    }


}
