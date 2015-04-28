package server.rest;

import shared.Response;
import shared.ResponseTask;
import shared.ResponseTasks;
import shared.Task;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public class TasksService {
    @GET
    @Path("/")
    public ResponseTasks getAll() {
        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {

        }
        Task[] tasks = {new Task("hi"), new Task("azz")};
        return new ResponseTasks(200, tasks);
    }
}
