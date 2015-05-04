package server.rest;

import server.db.DataBaseService;
import shared.Days;
import shared.Response;
import shared.Task;
import shared.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @GET
    @Path("{time}")
    public Response<List<Task>> getLongTasks(@Context HttpServletRequest req, @PathParam("time") String time) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        try {
            List<Task> tasks = DataBaseService.getInstance().getLongTasksList(currentUser, time);
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

    @POST
    @Path("{id}/close")
    public Response<Task> addOne(@Context HttpServletRequest req, @PathParam("id") int taskId) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        try {
            DataBaseService.getInstance().closeTask(taskId);
            return new Response<Task>(200, "OK");
        }
        catch (Exception e) {
            return new Response<Task>(400, e.getMessage());
        }
    }

    @GET
    @Path("year/{year}/")
    public Response<Days> getYearTasks(@Context HttpServletRequest req, @PathParam("year") String year) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        try {
            Map<String, Integer> list = DataBaseService.getInstance().getTasksByYear(currentUser, year);
            return new Response<Days>(200, new Days(list));
        }
        catch (Exception e) {
            return new Response<Days>(400, e.getMessage());
        }
    }
}
