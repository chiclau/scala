package com.yada.spos.mag.core

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.typesafe.scalalogging.LazyLogging
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

/**
  * 分页处理
  */
@Component
class PageInterceptor extends HandlerInterceptorAdapter with LazyLogging {
  override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: scala.Any): Boolean = {
    val r = super.preHandle(request, response, handler)
    if (!r) r
    else if (request.getMethod.equalsIgnoreCase("get")) {
      val key = request.getRequestURI
      val queryString = request.getQueryString
      val cacheQueryString = request.getSession.getAttribute(key)
      logger.info(s"key[$key]queryString[$queryString]cacheQueryString[$cacheQueryString]")
      if (queryString != null && !queryString.contains("page=")) {
        response.sendRedirect(request.getRequestURI + "?" + queryString + "&page=0")
        false
      } else if (queryString != null) {
        request.getSession.setAttribute(key, queryString)
        true
      } else if (cacheQueryString != null) {
        // 查询条件为空，但是session中对应的条件，则使用session中的条件
        response.sendRedirect(request.getRequestURI + "?" + cacheQueryString.toString)
        false
      } else true
    } else true
  }

  override def postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: scala.Any, modelAndView: ModelAndView): Unit = {
    val (curQueryString, firstQueryString, preQueryString, nextQueryString, lastQueryString) = if (request.getQueryString == null) ("", "", "", "", "")
    else {
      val queryString = request.getQueryString

      var pageNumber: String = request.getParameter("page")
      if (pageNumber == null || pageNumber.isEmpty) pageNumber = "0"
      val curPage = pageNumber.toInt
      val prePage = if (curPage > 1) curPage - 1 else 0
      val totalPage = modelAndView.getModel.get("page").asInstanceOf[Page[_]].getTotalPages - 1
      val nextPage = if (curPage < totalPage) curPage + 1 else curPage
      (queryString,
        queryString.replace(s"page=$curPage", "page=0"),
        queryString.replace(s"page=$curPage", s"page=$prePage"),
        queryString.replace(s"page=$curPage", s"page=$nextPage"),
        queryString.replace(s"page=$curPage", s"page=$totalPage"))
    }
    request.setAttribute("firstQueryString", firstQueryString)
    request.setAttribute("preQueryString", preQueryString)
    request.setAttribute("nextQueryString", nextQueryString)
    request.setAttribute("lastQueryString", lastQueryString)
    request.setAttribute("curQueryString", curQueryString)
    super.postHandle(request, response, handler, modelAndView)
  }
}
