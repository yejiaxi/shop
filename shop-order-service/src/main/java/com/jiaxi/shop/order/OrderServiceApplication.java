package com.jiaxi.shop.order;


import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.jiaxi.common.utils.IDWorker;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubboConfiguration
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class,args);
    }

    @Bean
    public IDWorker getIDWorker(){
        return new IDWorker(1,1);
    }
}
