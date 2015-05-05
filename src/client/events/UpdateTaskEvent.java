package client.events;

import com.google.gwt.event.shared.GwtEvent;
import shared.Task;

/**
 * Created by dmitry on 05.05.15.
 */
public class UpdateTaskEvent extends GwtEvent<UpdateTaskEventHandler> {

    public static Type<UpdateTaskEventHandler> TYPE = new Type<UpdateTaskEventHandler>();

    private String title;
    private int id;

    public UpdateTaskEvent(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public Type<UpdateTaskEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateTaskEventHandler handler) {
        handler.update(this);
    }
}
