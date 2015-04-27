package client.widgets.tasks;

import client.TasksService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;

/**
 * Created by dmitry on 27.04.15.
 */
public class TasksWidget extends Composite {

    interface ToolbarUiBinder extends UiBinder<Widget, TasksWidget> {
    }

    private static ToolbarUiBinder uiBinder = GWT.create(ToolbarUiBinder.class);

    public TasksWidget(SimpleEventBus eventBus) {
        TasksService tasksService = GWT.create(TasksService.class);
        tasksService.getAll(new MethodCallback<Response>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response response) {
                Window.alert("zaebis");
            }
        });
    }
}
