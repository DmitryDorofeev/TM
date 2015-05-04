package client.widgets.graph;

import client.TasksService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import shared.Days;
import shared.Response;
import java.util.Date;

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
    @UiField
    HTMLPanel popup;
    @UiField
    Label popupDate;
    @UiField
    Label popupTasks;

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
        String active();
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
                        CalendarUtil.addDaysToDate(currentDate, 7 * day + i - dayOfWeek + 1);
                        HTMLPanel div = new HTMLPanel("");
                        div.addStyleName(dynamic.element());
                        DateTimeFormat displayFormat = DateTimeFormat.getFormat("dd.MM.yyyy");

                        String key = dateFormat.format(currentDate);

                        div.getElement().setAttribute("data-date", displayFormat.format(currentDate));
                        div.getElement().setAttribute("data-tasks", resp.data.days.containsKey(key) ? resp.data.days.get(key).toString() : "0");



                        if (day == 0 && i < dayOfWeek - 1) {
                            div.addStyleName(dynamic.disabled());
                        }
                        if (day == 52 && i > lastDayOfWeek - 1) {
                            div.addStyleName(dynamic.disabled());
                        }
                        if (resp.data.days.containsKey(key)) {
                            if (resp.data.days.get(key) > 10) {
                                div.addStyleName(dynamic.many());
                            }
                            if (resp.data.days.get(key) > 5) {
                                div.addStyleName(dynamic.middle());
                            }
                            if (resp.data.days.get(key) > 1) {
                                div.addStyleName(dynamic.few());
                            }
                        }
                        div.sinkEvents(Event.ONMOUSEOVER);
                        div.sinkEvents(Event.ONMOUSEOUT);
                        div.addHandler(new MouseOverHandler() {
                            public void onMouseOver(MouseOverEvent event) {
                                popupDate.setText(event.getRelativeElement().getAttribute("data-date"));
                                popupTasks.setText(event.getRelativeElement().getAttribute("data-tasks"));
                                popup.getElement().setAttribute("style", "display: block; left: " + (event.getRelativeX(panel.getElement()) - 75) + "; top: " + (event.getRelativeY(panel.getElement()) - 20));
                                event.getRelativeElement().addClassName(dynamic.active());
                            }
                        }, MouseOverEvent.getType());
                        div.addHandler(new MouseOutHandler() {
                            public void onMouseOut(MouseOutEvent event) {
                                popup.getElement().removeAttribute("style");
                                event.getRelativeElement().removeClassName(dynamic.active());
                            }
                        }, MouseOutEvent.getType());
                        row.add(div);
                    }
                    panel.add(row);
                }
            }
        });

    }

}
