package com.yada.spos.mag.exception

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.web.servlet.{HandlerExceptionResolver, ModelAndView}
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver

/**
  * Created by pangChangSong on 2016/9/11.
  * 公共的异常处理类
  */
class CommonExceptionResolver extends SimpleMappingExceptionResolver with HandlerExceptionResolver {

  override def doResolveException(request: HttpServletRequest, response: HttpServletResponse, handler: Any, e: Exception): ModelAndView = {
    logger.error("", e)
    val model = super.doResolveException(request, response, handler, e)
    model.addObject("msg", if (e.getMessage != null && e.getMessage.isEmpty) null else e.getMessage)
  }
}
