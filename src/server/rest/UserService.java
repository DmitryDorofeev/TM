package server.rest;

import server.db.DataBaseService;
import shared.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user/")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    @POST
    @Path("login/")
    public User login() {
        DataBaseService db = new DataBaseService();
        db.connect();
        return new User("fghvjhbknlm");
    }

}