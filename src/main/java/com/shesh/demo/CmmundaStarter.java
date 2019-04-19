package com.shesh.demo;

import org.camunda.bpm.application.ProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shesh on 4/17/19.
 */


@SpringBootApplication
@ProcessApplication
public class CmmundaStarter {
    public static void main(String[] args) {
        SpringApplication.run(CmmundaStarter.class);
    }
}
