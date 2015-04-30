package client.widgets.login;

import client.UserService;
import client.events.LoginEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.User;

/**
 * Created by dmitry on 02.04.15.
 */
public class LoginWidget extends Composite {

    @UiField
    FormPanel loginForm;
    @UiField
    FormPanel signupForm;
    @UiField
    TextBox loginEmailField;
    @UiField
    PasswordTextBox loginPasswordField;
    @UiField
    TextBox signupEmailField;
    @UiField
    PasswordTextBox signupPasswordField;
    @UiField
    Button loginButton;
    @UiField
    Button signupButton;
    @UiField
    Label error;

    interface LoginUiBinder extends UiBinder<Widget, LoginWidget> {
    }

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
    private final SimpleEventBus eventBus;

    public LoginWidget(final SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(uiBinder.createAndBindUi(this));
        loginEmailField.getElement().setAttribute("placeholder", "email");
        loginPasswordField.getElement().setAttribute("placeholder", "password");
        loginForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                event.cancel();
                LoginWidget.this.login(loginEmailField.getText(), loginPasswordField.getText());
            }
        });
        signupForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                event.cancel();
                LoginWidget.this.signup(signupEmailField.getText(), signupPasswordField.getText());
            }
        });
    }

    public void login(String email, String password) {
        UserService userService = GWT.create(UserService.class);
        userService.login(email, password, new MethodCallback<Response<User>>() {
            public void onFailure(Method method, Throwable throwable) {
                error.setText(throwable.getMessage());
            }

            public void onSuccess(Method method, Response resp) {
                if (resp.status == 200) {
                    User user = (User) resp.data;
                    eventBus.fireEvent(new LoginEvent(user));
                } else {
                    error.setText("Неправильный логин/пароль");
                }
            }
        });
    }

    public void signup(String email, String password) {
        UserService userService = GWT.create(UserService.class);
        userService.signip(email, password, new MethodCallback<Response<User>>() {
            public void onFailure(Method method, Throwable throwable) {
                error.setText(throwable.getMessage());
            }

            public void onSuccess(Method method, Response resp) {
                if (resp.status == 200) {
                    Window.alert("ok");
                } else {
                    Window.alert("not ok");
                }
            }
        });
    }

}
