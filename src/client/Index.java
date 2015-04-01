package client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Index implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final FormPanel form = new FormPanel();
        form.setAction("/login");
        form.setMethod(FormPanel.METHOD_POST);

        Panel loginPanel = new VerticalPanel();

        final Button button = new Button("Login");
        final TextBox loginBox = new TextBox();
        final TextBox passwordBox = new PasswordTextBox();
        final Label label = new Label();
        form.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                Window.alert("azaz");
                event.cancel();
            }
        });
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    IndexService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
                form.submit();
            }
        });

        loginPanel.add(loginBox);
        loginPanel.add(passwordBox);
        loginPanel.add(button);

        form.add(loginPanel);

        RootPanel.get("login-form").add(form);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
