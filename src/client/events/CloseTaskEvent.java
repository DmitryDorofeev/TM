package client.events;

import com.google.gwt.event.shared.GwtEvent;
import shared.Task;
import shared.User;

/**
 * Created by dmitry on 25.04.15.
 */
public class CloseTaskEvent extends GwtEvent<CloseTaskEventHandler> {

    public static Type<CloseTaskEventHandler> TYPE = new Type<CloseTaskEventHandler>();

    private Task task;

    public CloseTaskEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    @Override
    public Type<CloseTaskEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CloseTaskEventHandler handler) {
        handler.close(this);
    }
}
