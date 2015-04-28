package client.cells.tasks;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import shared.Task;

/**
 * Created by dmitry on 28.04.15.
 */
public class TasksCell extends AbstractCell<Task> {

    interface Templates extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<div>{0}</div>")
        SafeHtml cell(SafeHtml value);
    }
    private static Templates templates = GWT.create(Templates.class);
    @Override
    public void render(Context context, Task value, SafeHtmlBuilder sb) {
        if (value == null) {
            return;
        }
        SafeHtml safeValue = SafeHtmlUtils.fromString(value.title);
        SafeHtml rendered = templates.cell(safeValue);
        sb.append(rendered);
    }
}
