package client.widgets.toolbar;

import client.events.LoginEvent;
import client.events.LoginEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import shared.User;

/**
 * Created by dmitry on 18.04.15.
 */
public class ToolbarWidget extends Composite {

    @UiField
    Label email;

    interface ToolbarUiBinder extends UiBinder<Widget, ToolbarWidget> {
    }

    private static ToolbarUiBinder uiBinder = GWT.create(ToolbarUiBinder.class);

    public ToolbarWidget(SimpleEventBus eventBus, User user) {
        initWidget(uiBinder.createAndBindUi(this));
        email.setText(user.email);
    }
}
