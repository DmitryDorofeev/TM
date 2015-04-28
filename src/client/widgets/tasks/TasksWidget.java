package client.widgets.tasks;

import client.TasksService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import javafx.scene.layout.FlowPane;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.ResponseTasks;

/**
 * Created by dmitry on 27.04.15.
 */
public class TasksWidget extends Composite {

    @UiField
    DivElement preloader;
    @UiField
    TasksStyle style;
    @UiField
    FlowPanel content;

    interface TasksStyle extends CssResource {
        String hidden();
    }
    interface TasksUiBinder extends UiBinder<Widget, TasksWidget> {
    }

    private static TasksUiBinder uiBinder = GWT.create(TasksUiBinder.class);

    public TasksWidget(SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        TasksService tasksService = GWT.create(TasksService.class);
        tasksService.getAll(new MethodCallback<ResponseTasks>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, ResponseTasks response) {
                content.clear();
                for (int i = 0; i < response.data.length; ++i) {
                    Window.alert(response.data[i].title);
                }
            }
        });
    }
}
