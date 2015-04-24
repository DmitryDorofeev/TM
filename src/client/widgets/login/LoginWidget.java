package client.widgets.login;

import client.UserService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.User;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dmitry on 02.04.15.
 */
public class LoginWidget extends Composite {

    @UiField
    TextBox loginField;
    @UiField
    PasswordTextBox passwordField;
    @UiField
    Button loginButton;
    @UiField
    Button signupButton;
    @UiField
    Label error;

    interface LoginUiBinder extends UiBinder<Widget, LoginWidget> {
    }

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

    public LoginWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        loginField.getElement().setAttribute("placeholder", "email");
        passwordField.getElement().setAttribute("placeholder", "password");
        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                LoginWidget.this.login(loginField.getText(), passwordField.getText());
            }
        });
        signupButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                LoginWidget.this.signup(loginField.getText(), passwordField.getText());
            }
        });
    }

    public void login(String email, String password) {
        UserService userService = GWT.create(UserService.class);
        userService.login(email, password, new MethodCallback<Response>() {
            public void onFailure(Method method, Throwable throwable) {
                error.setText(throwable.getMessage());
            }

            public void onSuccess(Method method, Response resp) {
                Window.alert("OK");
                if (resp.status == 200) {
                    Window.alert("OKK");
                    User user = (User) resp.data;
                    Window.alert(user.email);
                } else {
                    Window.alert("not ok");
                }
            }
        });
    }

    public void signup(String email, String password) {
        UserService userService = GWT.create(UserService.class);
        userService.signip(email, password, new MethodCallback<Response>() {
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
