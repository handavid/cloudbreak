package com.sequenceiq.freeipa.api.model;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/testflow")
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/testflow", description = "Test flow", protocols = "http,https")
public interface FlowTestEndpoint {
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Trigger a new helloworld flow", nickname = "testFlow")
    void triggerTestFlow();
}
