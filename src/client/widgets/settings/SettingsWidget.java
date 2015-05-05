package client.widgets.settings;

import client.TasksService;
import client.events.OpenSettingsEvent;
import client.events.OpenSettingsEventHandler;
import client.events.UpdateTaskEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Response;
import shared.Task;

/**
 * Created by dmitry on 05.05.15.
 */
public class SettingsWidget extends Composite {

    @UiField
    HTMLPanel panel;
    @UiField
    Label title;
    @UiField
    SettingsStyles dynamic;
    @UiField
    DivElement opened;
    @UiField
    DivElement closed;
    @UiField
    DivElement image;
    @UiField
    Button editButton;
    @UiField
    TextBox titleBox;
    @UiField
    Button okButton;
    @UiField
    DivElement editTitle;
    @UiField
    DivElement staticTitle;
    @UiField
    HTMLPanel layer;

    Task task;

    interface SettingsStyles extends CssResource {
        String show();
        String show_inline();
        String semitransparent();
        String hide();
    }

    interface SettingsUiBinder extends UiBinder<Widget, SettingsWidget> {
    }

    private static SettingsUiBinder uiBinder = GWT.create(SettingsUiBinder.class);

    public SettingsWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        final TasksService tasksService = GWT.create(TasksService.class);

        eventBus.addHandler(OpenSettingsEvent.TYPE, new OpenSettingsEventHandler() {
            public void showSettings(OpenSettingsEvent event) {
                Task task = event.getTask();
                SettingsWidget.this.task = task;
                title.setText(task.title);
                titleBox.setText(task.title);
                if (task.opened) {
                    opened.addClassName(dynamic.show_inline());
                    closed.removeClassName(dynamic.show_inline());
                    image.addClassName(dynamic.semitransparent());
                } else {
                    closed.addClassName(dynamic.show_inline());
                    opened.removeClassName(dynamic.show_inline());
                    image.removeClassName(dynamic.semitransparent());
                }
                panel.addStyleName(dynamic.show());
            }
        });

        layer.sinkEvents(Event.ONCLICK);
        layer.addHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                panel.removeStyleName(dynamic.show());
            }
        }, ClickEvent.getType());

        editButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                editTitle.removeClassName(dynamic.hide());
                staticTitle.addClassName(dynamic.hide());
            }
        });

        okButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tasksService.updateTitle(task.id, titleBox.getText(), new MethodCallback<Response<Boolean>>() {
                    public void onFailure(Method method, Throwable throwable) {

                    }

                    public void onSuccess(Method method, Response<Boolean> booleanResponse) {
                        String newTitle = titleBox.getText();
                        editTitle.addClassName(dynamic.hide());
                        staticTitle.removeClassName(dynamic.hide());
                        title.setText(newTitle);
                        eventBus.fireEvent(new UpdateTaskEvent(newTitle, task.id));
                    }
                });
            }
        });
    }
}
