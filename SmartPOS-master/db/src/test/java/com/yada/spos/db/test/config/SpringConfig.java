package com.yada.spos.db.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by pangChanagSong on 2016/9/12.
 * spring配置
 */
@Configuration
@ComponentScan(basePackages = "com.yada.spos.db")
@Import(SpringJpaTestConfig.class)
public class SpringConfig {

}
