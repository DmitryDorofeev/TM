package client.widgets.toolbar;

import client.UserService;
import client.events.LoginEvent;
import client.events.LoginEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.User;

/**
 * Created by dmitry on 18.04.15.
 */
public class ToolbarWidget extends Composite {

    @UiField
    Label email;
    @UiField
    SimplePanel emailField;
    @UiField
    DivElement userActionsList;
    @UiField
    HTMLPanel logout;

    interface ToolbarUiBinder extends UiBinder<Widget, ToolbarWidget> {
    }

    private static ToolbarUiBinder uiBinder = GWT.create(ToolbarUiBinder.class);

    public ToolbarWidget(SimpleEventBus eventBus, User user) {
        initWidget(uiBinder.createAndBindUi(this));
        email.setText(user.email);
        emailField.sinkEvents(Event.ONCLICK);
        emailField.addHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                userActionsList.toggleClassName("visible");
            }
        }, ClickEvent.getType());
        logout.sinkEvents(Event.ONCLICK);
        logout.addHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logout();
            }
        }, ClickEvent.getType());
    }

    public void logout() {
        UserService userService = GWT.create(UserService.class);
        userService.logout(new MethodCallback<Response>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response response) {
                if (response.status == 200) {
                    Window.Location.reload();
                }
            }
        });
    }
}
