package client.widgets.graph;

import client.TasksService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Days;
import shared.Response;
import java.util.Date;
import java.util.Map;

/**
 * Created by dmitry on 27.04.15.
 */
public class GraphWidget extends Composite {

    @UiField
    FlowPanel panel;
    @UiField
    GraphStyle dynamic;
    @UiField
    Label year;

    TasksService tasksService;
    SimpleEventBus eventBus;

    interface GraphStyle extends CssResource {
        String row();
        String element();
        String many();
        String middle();
        String few();
        String slave();
        String disabled();
    }

    interface GraphUiBinder extends UiBinder<Widget, GraphWidget> {
    }

    private static GraphUiBinder uiBinder = GWT.create(GraphUiBinder.class);

    public GraphWidget(final SimpleEventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.eventBus = eventBus;
        tasksService = GWT.create(TasksService.class);

        Date now = new Date();
        DateTimeFormat format = DateTimeFormat.getFormat("yyyy");
        String yearString = format.format(now);
        year.setText(yearString);

        StringBuilder firsdDay = new StringBuilder();
        firsdDay.append(yearString).append("-01-01");
        final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
        final Date januaryFirst = dateFormat.parse(firsdDay.toString());
        DateTimeFormat weekdayFormat = DateTimeFormat.getFormat("c");
        final int dayOfWeek = Integer.parseInt(weekdayFormat.format(januaryFirst));

        StringBuilder lastDay = new StringBuilder();
        lastDay.append(yearString).append("-12-31");
        final Date decemberLast = dateFormat.parse(lastDay.toString());
        final int lastDayOfWeek = Integer.parseInt(weekdayFormat.format(decemberLast));

        tasksService.getYearTasks(yearString, new MethodCallback<Response<Days>>() {

            public void onFailure(Method method, Throwable throwable) {

            }

            public void onSuccess(Method method, Response<Days> resp) {

                for (int i = 0; i < 7; ++i) {
                    HTMLPanel row = new HTMLPanel("");
                    row.addStyleName(dynamic.row());
                    for (int day = 0; day < 53; ++day) {


                        Date currentDate = CalendarUtil.copyDate(januaryFirst);
                        CalendarUtil.addDaysToDate(currentDate, 7*day + i - dayOfWeek + 1);
                        HTMLPanel div = new HTMLPanel("");
                        div.addStyleName(dynamic.element());
                        div.getElement().setAttribute("data-date", currentDate.toString());
                        if (day == 0 && i < dayOfWeek - 1) {
                            div.addStyleName(dynamic.disabled());
                        }
                        if (day == 52 && i > lastDayOfWeek - 1) {
                            div.addStyleName(dynamic.disabled());
                        }
                        if (resp.data.days.containsKey(dateFormat.format(currentDate))) {
                            if (resp.data.days.get(dateFormat.format(currentDate)) > 10) {
                                div.addStyleName(dynamic.many());
                            }
                            if (resp.data.days.get(dateFormat.format(currentDate)) > 5) {
                                div.addStyleName(dynamic.middle());
                            }
                            if (resp.data.days.get(dateFormat.format(currentDate)) > 1) {
                                div.addStyleName(dynamic.few());
                            }
                        }
                        row.add(div);
                    }
                    panel.add(row);
                }
            }
        });

    }

}
