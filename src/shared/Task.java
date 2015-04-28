package shared;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

/**
 * Created by dmitry on 14.04.15.
 */

public class Task implements Serializable {

    public String title;

    @JsonCreator
    public Task(@JsonProperty("title") String title) {
        this.title = title;
    }

    public Task() {
    }
}
