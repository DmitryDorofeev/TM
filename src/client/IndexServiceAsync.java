package client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IndexServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
