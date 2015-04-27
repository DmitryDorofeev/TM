package server.rest;

import shared.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public class TasksService {
    @GET
    @Path("/")
    public Response getAll() {
        return null;
    }
}
