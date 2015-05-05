package client.widgets.tasks;

import client.TasksService;
import client.widgets.day.DayTasksWidget;
import client.widgets.settings.SettingsWidget;
import client.widgets.simple.SimpleTasksWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Created by dmitry on 27.04.15.
 */
public class TasksWidget extends Composite {

    @UiField
    TasksStyle dynamic;
    @UiField
    FlowPanel panel;

    TasksService tasksService;
    SimpleEventBus eventBus;

    interface TasksStyle extends CssResource {
        String hidden();
    }

    interface TasksUiBinder extends UiBinder<Widget, TasksWidget> {
    }

    private static TasksUiBinder uiBinder = GWT.create(TasksUiBinder.class);

    public TasksWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.eventBus = eventBus;
        panel.add(new SimpleTasksWidget(eventBus, "Год", "year"));
        panel.add(new SimpleTasksWidget(eventBus, "Месяц", "month"));
        panel.add(new SimpleTasksWidget(eventBus, "Неделя", "week"));
        panel.add(new DayTasksWidget(eventBus));
        panel.add(new SettingsWidget(eventBus));
    }

}
