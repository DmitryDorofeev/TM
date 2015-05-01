package server.rest;

import server.db.DataBaseService;
import shared.Response;
import shared.Task;
import shared.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public class TasksService {

    @GET
    public Response<List<Task>> getAll(@Context HttpServletRequest req) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        try {
            List<Task> tasks = DataBaseService.getInstance().getTasksList(currentUser);
            return new Response<List<Task>>(200, tasks);
        }
        catch (Exception e) {
            return new Response<List<Task>>(400, e.getMessage());
        }
    }

    @POST
    @Path("add/")
    public Response<Task> addOne(@Context HttpServletRequest req, @FormParam("task") String taskTitle) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        try {
            Task task = DataBaseService.getInstance().addTask(taskTitle, currentUser);
            return new Response<Task>(200, task);
        }
        catch (Exception e) {
            return new Response<Task>(400, e.getMessage());
        }
    }

}
