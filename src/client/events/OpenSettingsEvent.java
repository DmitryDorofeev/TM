package client.events;

import com.google.gwt.event.shared.GwtEvent;
import shared.Task;

/**
 * Created by dmitry on 05.05.15.
 */
public class OpenSettingsEvent extends GwtEvent<OpenSettingsEventHandler> {

    public static Type<OpenSettingsEventHandler> TYPE = new Type<OpenSettingsEventHandler>();

    private Task task;

    public OpenSettingsEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    @Override
    public Type<OpenSettingsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OpenSettingsEventHandler handler) {
        handler.showSettings(this);
    }
}
