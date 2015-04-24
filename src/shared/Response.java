package shared;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dmitry on 18.04.15.
 */
public class Response {
    public int status;
    public Object data;

    @JsonCreator
    public Response(@JsonProperty("status") int status, @JsonProperty("data") Object data) {
        this.status = status;
        this.data = data;
    }

    public Response() {
    }
}
