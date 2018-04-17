package com.yada.spos.mag.filter

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.config.ConfigurableSiteMeshFilter
import org.springframework.http.MediaType

/**
  * SiteMesh过滤器
  */
class SiteMeshFilter extends ConfigurableSiteMeshFilter {
  override def applyCustomConfiguration(builder: SiteMeshFilterBuilder): Unit = {
    // 只针对text/html的类型进行处理
    builder
      .setMimeTypes(Array(MediaType.TEXT_HTML_VALUE): _*)
      .addDecoratorPath("/*.do", "/WEB-INF/layout/default.jsp")
      .addExcludedPath("/login.do")
  }
}
