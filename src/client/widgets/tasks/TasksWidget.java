package client.widgets.tasks;

import client.TasksService;
import client.widgets.task.TaskWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.Task;
import java.util.List;

/**
 * Created by dmitry on 27.04.15.
 */
public class TasksWidget extends Composite {

    @UiField
    DivElement preloader;
    @UiField
    TasksStyle dynamic;
    @UiField
    FlowPanel content;

    interface TasksStyle extends CssResource {
        String hidden();
    }

    interface TasksUiBinder extends UiBinder<Widget, TasksWidget> {
    }

    private static TasksUiBinder uiBinder = GWT.create(TasksUiBinder.class);

    public TasksWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        TasksService tasksService = GWT.create(TasksService.class);
        tasksService.getAll(new MethodCallback<Response<List<Task>>>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<List<Task>> response) {
                content.clear();
                for (Task task : response.data) {
                    content.add(new TaskWidget(eventBus, task));
                }
            }
        });
    }
}
