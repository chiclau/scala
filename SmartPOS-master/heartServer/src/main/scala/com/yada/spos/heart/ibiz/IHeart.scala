package com.yada.spos.heart.ibiz

/**
  * 心跳处理接口
  */
trait IHeart {
  /**
    * 处理方法
    *
    * @param msgID     信息唯一标识
    * @param firmCode  厂商代码
    * @param sn        设备SN
    * @param timestamp 时间戳
    * @param sign      签名
    * @param reqBody   请求正文
    * @return
    */
  def handle(msgID: String,
             firmCode: String,
             sn: String,
             timestamp: String,
             sign: String,
             reqBody: HeartReq): HeartResp
}

/**
  * 心跳请求体
  *
  * @param state   终端状态
  * @param station 基站信息
  */
case class HeartReq(state: String, station: String)

/**
  * 心跳响应体
  *
  * @param retCode 返回码 00-成功01-失败
  * @param retMsg  响应信息
  */
case class HeartResp(retCode: String, retMsg: Option[String])