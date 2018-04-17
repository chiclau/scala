package com.yada.spos.front

import javax.ws.rs.Path

import com.yada.spos.front.rs.{Acq, Spos}

@Path("/frontRS")
class RootRS {

  @Path("/acq")
  def getAcq = classOf[Acq]

  @Path("/v1")
  def getSpos = classOf[Spos]
}
