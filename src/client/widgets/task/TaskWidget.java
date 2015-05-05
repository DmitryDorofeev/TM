package client.widgets.task;

import client.events.CloseTaskEvent;
import client.events.OpenSettingsEvent;
import client.events.UpdateTaskEvent;
import client.events.UpdateTaskEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
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
    @UiField
    Button doneButton;
    @UiField
    Button paramsButton;
    @UiField
    TaskStyle dynamic;
    Task task;

    interface TaskStyle extends CssResource {
        String closed();
        String title_line();
    }

    interface TaskUiBinder extends UiBinder<Widget, TaskWidget> {
    }

    private static TaskUiBinder uiBinder = GWT.create(TaskUiBinder.class);

    public TaskWidget(final SimpleEventBus eventBus, final Task task) {
        initWidget(uiBinder.createAndBindUi(this));
        title.setText(task.title);
        this.task = task;
        if (!task.opened) {
            panel.addStyleName(dynamic.closed());
            title.addStyleName(dynamic.title_line());
        }
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

        doneButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (task.opened) {
                    panel.addStyleName(dynamic.closed());
                    title.addStyleName(dynamic.title_line());
                    task.opened = false;
                    eventBus.fireEvent(new CloseTaskEvent(TaskWidget.this));
                    doneButton.addStyleName("hide");
                }
            }
        });

        paramsButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new OpenSettingsEvent(task));
            }
        });

        eventBus.addHandler(UpdateTaskEvent.TYPE, new UpdateTaskEventHandler() {
            public void update(UpdateTaskEvent event) {
                if (event.getId() == task.id) {
                    title.setText(event.getTitle());
                }
            }
        });

    }

    public Task getTask() {
        return this.task;
    }
}
