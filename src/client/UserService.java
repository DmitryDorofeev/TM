package client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import shared.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user/")
public interface UserService extends RestService {

    @POST
    @Path("login/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void login(@FormParam("email") String email, @FormParam("password") String password, MethodCallback<Response> callback);

    @GET
    @Path("login/")
    void getUser(MethodCallback<Response> callback);

    @POST
    @Path("signup/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void signip(@FormParam("email") String email, @FormParam("password") String password, MethodCallback<Response> callback);

    @POST
    @Path("logout/")
    void logout(MethodCallback<Response> callback);

}
