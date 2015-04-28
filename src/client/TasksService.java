package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import shared.Response;
import shared.ResponseTask;
import shared.ResponseTasks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public interface TasksService extends RestService {

    @GET
    void getAll(MethodCallback<ResponseTasks> callback);

    @GET
    @Path("{id}/")
    void getOne(@PathParam("id") int id, MethodCallback<ResponseTask> callback);
}
