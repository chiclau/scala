package com.yada.spos.mag.core.shiro

/**
  * 角色列表
  * Created by nickDu on 2016/9/14.
  */
object Role extends Enumeration {
  type Role = Value
  val FIRMMAG = Value("FirmMagRole")
  val FIRMCREATE = Value("FirmCreateRole")
  val FIRMUPDATE = Value("FirmUpdateRole")
  val PRODUCTMAG = Value("ProductMagRole")
  val PRODUCTCREATE = Value("ProductCreateRole")
  val PRODUCTUPDATE = Value("ProductUpdateRole")
  val DEVICEMAG = Value("DeviceMagRole")
  val TERMSNMAG = Value("TermSnMagRole")
  val MPOSMAG = Value("MPosMagRole")
  val APPMAG = Value("AppMagRole")
  val APPGROUPMAG = Value("AppGroupMagRole")
  val DEVICEPARAMMAG = Value("DeviceParamMagRole")
  val OTAMAG = Value("OtaMagRole")
  val APPGROUPCREATE = Value("AppGroupCreateRole")
  val GROUPREDEVICE = Value("GroupReDeviceRole")
  val GROUPDEDEVICE = Value("GroupDeDeviceRole")
}
