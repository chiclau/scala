package com.yada.spos.common.rs

import javax.ws.rs.ext.{ContextResolver, Provider}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.stereotype.Component

/**
  * Created by pangChangSong on 2016/10/12.
  * 使得json支持scala的类型
  */
@Provider
@Component
class JsonObjectMapperProvider extends ContextResolver[ObjectMapper] {
  val defaultObjectMapper = new ObjectMapper()
  defaultObjectMapper.registerModule(DefaultScalaModule)

  override def getContext(`type`: Class[_]): ObjectMapper = {
    defaultObjectMapper
  }
}
