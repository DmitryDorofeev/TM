package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import shared.Days;
import shared.Response;
import shared.Task;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public interface TasksService extends RestService {

    @GET
    void getAll(MethodCallback<Response<List<Task>>> callback);

    @GET
    @Path("year/{year}/")
    void getYearTasks(@PathParam("year") String year, MethodCallback<Response<Days>> callback);

    @GET
    @Path("{time}/")
    void getLongTasks(@PathParam("time") String time, MethodCallback<Response<List<Task>>> callback);

    @GET
    @Path("{id}/")
    void getOne(@PathParam("id") int id, MethodCallback<Response<Task>> callback);

    @POST
    @Path("add/")
    void addOne(@FormParam("task") String taskTitle, MethodCallback<Response<Task>> callback);

    @POST
    @Path("{id}/close")
    void closeTask(@PathParam("id") int id, MethodCallback<Response<Task>> callback);

    @POST
    @Path("{id}/update")
    void updateTitle(@PathParam("id") int id, @FormParam("title") String title, MethodCallback<Response<Boolean>> callback);
}
