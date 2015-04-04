package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/user/")
public interface UserService extends RestService {

    @POST
    @Path("/login")
    void login(MethodCallback<String> callback);

}
