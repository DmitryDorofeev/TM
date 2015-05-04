package client.widgets.day;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.Task;
import java.util.List;

/**
 * Created by dmitry on 27.04.15.
 */
public class DayTasksWidget extends Composite {

    @UiField
    FormPanel addForm;
    @UiField
    DivElement preloader;
    @UiField
    TasksStyle dynamic;
    @UiField
    FlowPanel tasks;
    @UiField
    FlowPanel closed;
    @UiField
    TextBox taskTitle;

    TasksService tasksService;
    SimpleEventBus eventBus;

    interface TasksStyle extends CssResource {
        String hidden();
    }

    interface TasksUiBinder extends UiBinder<Widget, DayTasksWidget> {
    }

    private static TasksUiBinder uiBinder = GWT.create(TasksUiBinder.class);

    public DayTasksWidget(final SimpleEventBus eventBus) {

        this.eventBus = eventBus;

        initWidget(uiBinder.createAndBindUi(this));
        taskTitle.getElement().setAttribute("placeholder", "Название задачи");
        this.tasksService = GWT.create(TasksService.class);
        this.tasksService.getAll(new MethodCallback<Response<List<Task>>>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<List<Task>> response) {
                tasks.clear();
                closed.clear();
                for (Task task : response.data) {
                    if (task.opened) {
                        tasks.add(new TaskWidget(eventBus, task));
                    }
                    else {
                        closed.add(new TaskWidget(eventBus, task));
                    }
                }
            }
        });

        addForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                event.cancel();
                if (taskTitle.getText() != "") {
                    addTask(taskTitle.getText());
                }
            }
        });

        eventBus.addHandler(CloseTaskEvent.TYPE, new CloseTaskEventHandler() {
            public void close(final CloseTaskEvent event) {
                tasksService.closeTask(event.getTask().id, new MethodCallback<Response<Task>>() {
                    public void onFailure(Method method, Throwable throwable) {

                    }

                    public void onSuccess(Method method, Response<Task> stringResponse) {
                        tasks.remove(event.getTarget());
                        closed.add(event.getTarget());
                    }
                });
            }
        });
    }

    public void addTask(String title) {
        this.tasksService.addOne(title, new MethodCallback<Response<Task>>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<Task> taskResponse) {
                tasks.add(new TaskWidget(eventBus, taskResponse.data));
                taskTitle.setText("");
            }
        });
    }
}
