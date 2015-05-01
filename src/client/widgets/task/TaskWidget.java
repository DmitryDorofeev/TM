package client.widgets.task;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import shared.Task;

/**
 * Created by dmitry on 27.04.15.
 */
public class TaskWidget extends Composite {

    @UiField
    Label title;
    @UiField
    HTMLPanel panel;
    @UiField
    HTMLPanel controls;


    interface TaskUiBinder extends UiBinder<Widget, TaskWidget> {
    }

    private static TaskUiBinder uiBinder = GWT.create(TaskUiBinder.class);

    public TaskWidget(SimpleEventBus eventBus, Task task) {
        initWidget(uiBinder.createAndBindUi(this));
        title.setText(task.title);

        panel.sinkEvents(Event.ONMOUSEOVER);
        panel.sinkEvents(Event.ONMOUSEOUT);
        panel.addHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
                controls.addStyleName("show");
            }

        }, MouseOverEvent.getType());

        panel.addHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
                controls.removeStyleName("show");
            }

        }, MouseOutEvent.getType());

    }
}
