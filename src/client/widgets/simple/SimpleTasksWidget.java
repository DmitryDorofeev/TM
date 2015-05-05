package client.widgets.simple;

import client.TasksService;
import client.events.CloseTaskEvent;
import client.events.CloseTaskEventHandler;
import client.widgets.task.TaskWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.Task;
import java.util.List;

/**
 * Created by dmitry on 27.04.15.
 */
public class SimpleTasksWidget extends Composite {

    @UiField
    FormPanel addForm;
    @UiField
    Label title;
    @UiField
    DivElement preloader;
    @UiField
    TasksStyle dynamic;
    @UiField
    FlowPanel content;
    @UiField
    TextBox taskTitle;

    TasksService tasksService;
    SimpleEventBus eventBus;

    interface TasksStyle extends CssResource {
        String hidden();
    }

    interface TasksUiBinder extends UiBinder<Widget, SimpleTasksWidget> {
    }

    private static TasksUiBinder uiBinder = GWT.create(TasksUiBinder.class);

    public SimpleTasksWidget(final SimpleEventBus eventBus, String title, String time) {
        this.eventBus = eventBus;
        initWidget(uiBinder.createAndBindUi(this));
        this.title.setText(title);
        taskTitle.getElement().setAttribute("placeholder", "Название задачи");
        this.tasksService = GWT.create(TasksService.class);
        this.tasksService.getLongTasks(time, new MethodCallback<Response<List<Task>>>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<List<Task>> response) {
                content.clear();
                for (Task task : response.data) {
                    content.add(new TaskWidget(eventBus, task));
                }
            }
        });

        addForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                event.cancel();
                addTask(taskTitle.getText());

            }
        });

        eventBus.addHandler(CloseTaskEvent.TYPE, new CloseTaskEventHandler() {
            public void close(CloseTaskEvent event) {

            }
        });
    }

    public void addTask(String title) {
        this.tasksService.addOne(title, new MethodCallback<Response<Task>>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<Task> taskResponse) {
                content.add(new TaskWidget(eventBus, taskResponse.data));
                taskTitle.setText("");
            }
        });
    }
}
