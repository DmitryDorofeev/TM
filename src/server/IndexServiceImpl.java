package server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import client.IndexService;

public class IndexServiceImpl extends RemoteServiceServlet implements IndexService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}