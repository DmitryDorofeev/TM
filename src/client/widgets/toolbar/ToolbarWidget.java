package client.widgets.toolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by dmitry on 18.04.15.
 */
public class ToolbarWidget extends Composite {

    interface ToolbarUiBinder extends UiBinder<Widget, ToolbarWidget> {
    }

    private static ToolbarUiBinder uiBinder = GWT.create(ToolbarUiBinder.class);

    public ToolbarWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
