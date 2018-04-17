package com.yada.spos.pos

import javax.inject.Singleton
import javax.ws.rs.Path

import com.yada.spos.pos.rs.{Down, Mag, Up}

@Singleton
@Path("/posRS")
class RootRS {
  @Path("/down")
  def getDown = classOf[Down]

  @Path("/up")
  def getUp = classOf[Up]

  @Path("/mag")
  def getMag = classOf[Mag]
}
