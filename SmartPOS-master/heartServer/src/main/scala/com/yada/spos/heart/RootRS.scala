package com.yada.spos.heart

import javax.inject.Singleton
import javax.ws.rs.Path

import com.yada.spos.heart.rs.Heart

@Singleton
@Path("/heartServer")
class RootRS {
  @Path("/v1")
  def getHeart = classOf[Heart]
}
