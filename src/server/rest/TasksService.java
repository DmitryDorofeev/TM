package server.rest;

import shared.Response;
import shared.Task;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public class TasksService {
    @GET
    @Path("/")
    public Response<List<Task>> getAll() {
        List<Task> tasks = new LinkedList<Task>();
        tasks.add(new Task("first"));
        tasks.add(new Task("second"));
        return new Response<List<Task>>(200, tasks);
    }
}
