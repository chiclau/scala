package com.yada.spos.mag.service.ext

import java.nio.file.Paths

import net.dongliu.apk.parser.bean.Icon
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

/**
  * Created by fengm on 2016/9/4.
  */
@Component
class ApkIconHandler {

  def iconToFile(icon: Icon, dir: String, pkgName: String): Unit = {
    //得到apk图标
    val iconFile = Paths.get(dir, s"${pkgName}_ICON.png").toFile
    //如果存在就强制删除
    if (iconFile.exists()) FileUtils.forceDelete(iconFile)
    //将数据写到响应文件
    FileUtils.writeByteArrayToFile(iconFile, icon.getData)
  }
}
