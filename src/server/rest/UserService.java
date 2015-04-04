package server.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("user")
public class UserService {

    @POST
    @Path("login")
    public String login() {
        return "login";
    }

}