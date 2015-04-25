package client.events;

import com.google.gwt.event.shared.GwtEvent;
import shared.User;

/**
 * Created by dmitry on 25.04.15.
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {

    public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

    private User user;

    public LoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginEventHandler handler) {
        handler.showApp(this);
    }
}
