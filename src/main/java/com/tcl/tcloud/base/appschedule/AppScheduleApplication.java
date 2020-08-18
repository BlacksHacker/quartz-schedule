package com.tcl.tcloud.base.appschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppScheduleApplication.class, args);
    }

}
