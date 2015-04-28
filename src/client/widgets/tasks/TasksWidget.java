package client.widgets.tasks;

import client.TasksService;
import client.cells.tasks.TasksCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import javafx.scene.layout.FlowPane;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.ResponseTasks;
import shared.Task;

import java.util.Arrays;
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

    public TasksWidget(SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        TasksService tasksService = GWT.create(TasksService.class);
        tasksService.getAll(new MethodCallback<ResponseTasks>() {
            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, ResponseTasks response) {
                content.clear();
                CellList<Task> tasks = new CellList<Task>(new TasksCell());
                List<Task> list = Arrays.asList(response.data);
                tasks.setRowData(0, list);
                content.add(tasks);
            }
        });
    }
}
