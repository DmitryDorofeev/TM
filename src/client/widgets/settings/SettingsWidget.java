package client.widgets.settings;

import client.events.OpenSettingsEvent;
import client.events.OpenSettingsEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
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

    interface SettingsStyles extends CssResource {
        String show();
        String show_inline();
    }

    interface SettingsUiBinder extends UiBinder<Widget, SettingsWidget> {
    }

    private static SettingsUiBinder uiBinder = GWT.create(SettingsUiBinder.class);

    public SettingsWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        eventBus.addHandler(OpenSettingsEvent.TYPE, new OpenSettingsEventHandler() {
            public void showSettings(OpenSettingsEvent event) {
                Task task = event.getTask();
                title.setText(task.title);
                if (task.opened) {
                    opened.addClassName(dynamic.show_inline());
                }
                else {
                    closed.addClassName(dynamic.show_inline());
                }
                panel.addStyleName(dynamic.show());
            }
        });

        panel.sinkEvents(Event.ONCLICK);
        panel.addHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                panel.removeStyleName(dynamic.show());
            }
        }, ClickEvent.getType());
    }
}
