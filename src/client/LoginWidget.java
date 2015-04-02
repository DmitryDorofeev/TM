package client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * Created by dmitry on 02.04.15.
 */
public class LoginWidget extends Composite {

    @UiField TextBox loginField;
    @UiField PasswordTextBox passwordField;
    @UiField Button loginButton;
    @UiField Label error;

    interface LoginUiBinder extends UiBinder<Widget, LoginWidget> {}
    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

    protected String username;
    protected String password;

    public LoginWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                LoginWidget.this.username = loginField.getText();
                LoginWidget.this.password = passwordField.getText();
                LoginWidget.this.login();
            }
        });
    }

    public void login() {
        LoginService loginService = GWT.create(LoginService.class);
        loginService.login(new MethodCallback<String>() {
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Fail");
            }

            public void onSuccess(Method method, String label) {
                Window.alert(label);
            }
        });
    }

}
