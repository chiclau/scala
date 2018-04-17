package com.yada.spos.db.test.config;

import com.yada.spos.db.dao.AppsAssociateAppGroupDao;
import com.yada.spos.db.dao.AppsUnAssociateAppGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * Created by pangChangSong on 2016/9/12.
 * test环境的config
 */
@Configuration
@PropertySource("classpath:application-uat.properties")
@Profile("uat")
public class UatConfig {

    @Autowired
    private Environment env;

    @Bean
    public AppsAssociateAppGroupDao appsAssociateAppGroupDao() {
        return new AppsAssociateAppGroupDao();
    }

    @Bean
    public AppsUnAssociateAppGroupDao appsUnAssociateAppGroupDao() {
        return new AppsUnAssociateAppGroupDao();
    }

    @Bean(name = "dataSource")
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        return dataSource;
    }

    @Bean(name = "jpaVendorAdapter")
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setDatabase(Database.DEFAULT);
        jpaVendorAdapter.setGenerateDdl(false);
        return jpaVendorAdapter;
    }
}