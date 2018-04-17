package com.yada.spos.front

import java.io.File

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

/**
  * 前置程序入口
  */
object FrontApp extends App with LazyLogging {
  // 设置配置文件
  if (new File("application.conf").exists()) System.setProperty("config.file", "application.conf")
  else System.setProperty("config.resource", "application.conf")
  // 加载配置文件
  val config = ConfigFactory.load()
  //指定系统属性，读取不到，默认取系统属性
  System.setProperty("spring.profiles.active", config.getString("springProfiles"))
  // 初始化spring上下文
  SpringContext
}
