package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/")
public interface LoginService extends RestService {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public void login(MethodCallback<String> callback);
}
