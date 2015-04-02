package server;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import client.LoginService;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/")
public class LoginServiceImpl implements LoginService {

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void login(MethodCallback<String> callback) {
    }
}