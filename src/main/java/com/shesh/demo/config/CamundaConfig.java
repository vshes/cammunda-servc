package com.shesh.demo.config;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shesh on 4/17/19.
 */

@Configuration
@PropertySource("classpath:hibernate.properties")
public class CamundaConfig {


    @Autowired
    Environment env;

    @Bean
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .username("sa").password("")
                .url("jdbc:h2:~/test")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
        return transactionManager;
    }


//    @Bean
//    @PersistenceContext(unitName = "default")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//
//        entityManagerFactory.setDataSource(dataSource());
//        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        Properties properties = new Properties();
////        entityManagerFactory
//        System.out.println("TTTTTT"+entityManagerFactory.getPersistenceUnitName());
//    entityManagerFactory.setPackagesToScan(new String[] {});
//        properties.putAll(jpaProps());
//        entityManagerFactory.setJpaProperties(properties);
//        return entityManagerFactory;
//    }

    private Map<String,Object> jpaProps() {
        Map<String,Object> hm = new HashMap<>();
        hm.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
        return hm;
    }


    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(){
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource());
        configuration.setTransactionManager(transactionManager(dataSource()));
        configuration.setDatabaseSchemaUpdate("true");
//        configuration.setHistory("true");
        configuration.setJobExecutorActivate(true);
        return configuration;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine(){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return  processEngine.getTaskService();
    }
}
