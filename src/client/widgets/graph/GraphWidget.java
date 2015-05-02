package client.widgets.graph;

import client.TasksService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Created by dmitry on 27.04.15.
 */
public class GraphWidget extends Composite {

    @UiField
    FlowPanel panel;
    @UiField
    GraphStyle dynamic;

    TasksService tasksService;
    SimpleEventBus eventBus;

    interface GraphStyle extends CssResource {
        String row();
        String element();
        String many();
        String middle();
        String few();
        String slave();
    }

    interface GraphUiBinder extends UiBinder<Widget, GraphWidget> {
    }

    private static GraphUiBinder uiBinder = GWT.create(GraphUiBinder.class);

    public GraphWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.eventBus = eventBus;

        for (int i = 0; i < 7; ++i) {
            HTMLPanel row = new HTMLPanel("");
            row.addStyleName(dynamic.row());
            for (int day = 0; day < 53; ++day) {
                HTMLPanel div = new HTMLPanel("");
                div.addStyleName(dynamic.element());
                div.getElement().setAttribute("data-info", "hi");
                if (day * i == 30) {
                    div.addStyleName(dynamic.few());
                }
                if (day * i == 45) {
                    div.addStyleName(dynamic.middle());
                }
                if (day * i == 5) {
                    div.addStyleName(dynamic.many());
                }
                row.add(div);
            }
            panel.add(row);
        }

    }

}
