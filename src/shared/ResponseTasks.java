package shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dmitry on 18.04.15.
 */
public class ResponseTasks {
    public int status;
    public Task[] data;

    @JsonCreator
    public ResponseTasks(@JsonProperty("status") int status, @JsonProperty("data") Task[] data) {
        this.status = status;
        this.data = data;
    }

    public ResponseTasks() {
    }
}
