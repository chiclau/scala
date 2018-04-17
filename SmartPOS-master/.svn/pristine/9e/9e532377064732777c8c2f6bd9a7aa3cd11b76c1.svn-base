package com.yada.spos.heart

import java.io.File

import com.typesafe.config.ConfigFactory

/**
  * 心跳服务程序入口
  */
object HeartServerApp extends App {
  // 设置配置文件
  if (new File("application.conf").exists()) System.setProperty("config.file", "application.conf")
  else System.setProperty("config.resource", "application.conf")
  // 加载配置文件
  val config = ConfigFactory.load()
  //指定系统属性，读取不到，默认取系统属性
  System.setProperty("spring.profiles.active", config.getString("springProfiles"))
  // 初始化spring容器
  SpringAplicationContext
}
