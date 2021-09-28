package com.eftc.config;

import com.eftc.thymeleaf.MyFF4JDialect;
import org.ff4j.FF4j;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass({FF4j.class})
@ComponentScan(value = {"org.ff4j.aop", "org.ff4j.spring"})
public class ClientFF4JConfig {
    @Qualifier("ff4jDataSource")
    @Autowired
    private DataSource dataSource;

    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));

        // Audit capabilities
        ff4j.audit(true);

        return ff4j;
    }

    @Bean
    public MyFF4JDialect myFF4JDialect() {
        MyFF4JDialect dialect = new MyFF4JDialect();
        dialect.setFF4J(getFF4j());

        return dialect;
    }
}
