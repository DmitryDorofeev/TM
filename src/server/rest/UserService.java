package server.rest;

import server.db.DataBaseService;
import shared.Response;
import shared.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Path("user/")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    Map<String, String> sessions = new HashMap<String, String>();

    @POST
    @Path("login/")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        if (email != null) {
            try {
                User user = DataBaseService.getInstance().getUser(email);
                return new Response(200, user);
            } catch (Exception e) {

            }
        }
        return new Response(400, new User(""));
    }

    @GET
    @Path("login/")
    public Response getUser(@Context HttpServletRequest req) {
        String email;
        HttpSession session = req.getSession(true);
        email = sessions.get(session.getId());
        if (email != null) {
            try {
                User user = DataBaseService.getInstance().getUser(email);
                return new Response(200, user);
            } catch (Exception e) {

            }
        }
        return new Response(400, new User(session.getId()));
    }

    @POST
    @Path("signup/")
    public Response signup(@FormParam("email") String email, @FormParam("password") String password, @Context HttpServletRequest req) {
        try {
            DataBaseService.getInstance().addUser(email, password);
            return new Response(200, null);
        }
        catch (Exception e) {
            return new Response(500, null);
        }
    }

}