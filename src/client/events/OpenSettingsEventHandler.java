package client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by dmitry on 25.04.15.
 */
public interface OpenSettingsEventHandler extends EventHandler {
    void showSettings(OpenSettingsEvent event);
}
