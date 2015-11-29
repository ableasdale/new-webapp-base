package com.example.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Api(value = "/hello", description = "Say Hello!")
public class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Basic Swagger Annotation Example", notes = "Additional commentary goes here")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Server Exception")})
    public Response sayHello() {
        JsonObject value = Json.createObjectBuilder()
                .add("firstName", "TEST")
                .add("lastName", "TEST2")
                .add("message", "TEST MESSAGE")
                .build();
        return Response.status(200).entity(value).build();
    }
}