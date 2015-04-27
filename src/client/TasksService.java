package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import shared.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dmitry on 27.04.15.
 */

@Path("tasks/")
public interface TasksService extends RestService {

    @GET
    @Path("/")
    void getAll(MethodCallback<Response> callback);
}
