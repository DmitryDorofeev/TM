package client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("IndexService")
public interface IndexService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use IndexService.App.getInstance() to access static instance of dmitrydorofeevServiceAsync
     */
    public static class App {
        private static IndexServiceAsync ourInstance = GWT.create(IndexService.class);

        public static synchronized IndexServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
