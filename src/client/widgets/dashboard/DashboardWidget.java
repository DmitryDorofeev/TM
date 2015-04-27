package client.widgets.dashboard;

import client.widgets.tasks.TasksWidget;
import client.widgets.toolbar.ToolbarWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import shared.User;

/**
 * Created by dmitry on 27.04.15.
 */
public class DashboardWidget extends Composite {

    @UiField
    SimplePanel panel;

    interface DashboardUiBinder extends UiBinder<Widget, DashboardWidget>{
    }

    public static DashboardUiBinder uiBinder = GWT.create(DashboardUiBinder.class);

    public DashboardWidget(SimpleEventBus eventBus, User user) {
        initWidget(uiBinder.createAndBindUi(this));
        panel.add(new ToolbarWidget(eventBus, user));
        panel.add(new TasksWidget(eventBus));
    }
}
