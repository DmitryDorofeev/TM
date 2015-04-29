package shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by dmitry on 18.04.15.
 */

public class Response<T> {
    public int status;
    public T data;

    @JsonCreator
    public Response(@JsonProperty("status") int status, @JsonProperty("data") T data) {
        this.status = status;
        this.data = data;
    }

    public Response() {
    }
}
