package shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dmitry on 04.05.15.
 */
public class Days implements Serializable {

    public Map<String, Integer> days;

    @JsonCreator
    public Days(@JsonProperty("days") Map<String, Integer> days) {
        this.days = days;
    }

    public Days() {
    }
}
