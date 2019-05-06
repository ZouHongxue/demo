package com.dubbo.provider;

import com.dubbo.sample.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/24 11:15 AM
 */
@Service(version = "1.0.0")
public class DefaultDemoService implements DemoService {
    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
