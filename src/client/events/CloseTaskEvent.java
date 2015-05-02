package client.events;

import client.widgets.task.TaskWidget;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;
import shared.Task;

/**
 * Created by dmitry on 25.04.15.
 */
public class CloseTaskEvent extends GwtEvent<CloseTaskEventHandler> {

    public static Type<CloseTaskEventHandler> TYPE = new Type<CloseTaskEventHandler>();

    private TaskWidget widget;

    public CloseTaskEvent(TaskWidget widget) {
        this.widget = widget;
    }

    public Task getTask() {
        return this.widget.getTask();
    }

    public Widget getTarget() {
        return this.widget;
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
