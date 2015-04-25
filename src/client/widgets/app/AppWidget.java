package client.widgets.app;

import client.UserService;
import client.events.LoginEvent;
import client.events.LoginEventHandler;
import client.widgets.login.LoginWidget;
import client.widgets.toolbar.ToolbarWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.User;

/**
 * Created by dmitry on 15.04.15.
 */
public class AppWidget extends Composite {

    @UiField
    Label message;

    @UiField
    DivElement messagebox;

    @UiField
    HTMLPanel container;

    interface AppUiBinder extends UiBinder<Widget, AppWidget> {
    }

    private static AppUiBinder uiBinder = GWT.create(AppUiBinder.class);

    public AppWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        UserService userService = GWT.create(UserService.class);

        userService.getUser(new MethodCallback<Response>() {
            public void onFailure(Method method, Throwable throwable) {
                container.clear();
                container.add(new LoginWidget(eventBus));
                showMessage(throwable.getMessage());
            }

            public void onSuccess(Method method, Response resp) {
                container.clear();
                if (resp.status == 200) {
                    container.add(new ToolbarWidget(eventBus, resp.data));
                }
                else {
                    container.add(new LoginWidget(eventBus));
                }
            }
        });

        eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
            public void showApp(LoginEvent event) {
                container.clear();
                container.add(new ToolbarWidget(eventBus, event.getUser()));
            }
        });

    }

    public void showMessage(String msg) {
        messagebox.addClassName("show");
        message.setText(msg);
        Timer timer = new Timer() {
            public void run() {
                messagebox.removeClassName("show");
                messagebox.addClassName("hide");
            }
        };
        timer.schedule(5000);
    }
}
