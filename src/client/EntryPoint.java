package client;

import client.widgets.app.AppWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class EntryPoint implements com.google.gwt.core.client.EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getHostPageBaseURL() + "api");

        SimpleEventBus eventBus = new SimpleEventBus();

        RootPanel.get("container").add(new AppWidget(eventBus));
    }

}
