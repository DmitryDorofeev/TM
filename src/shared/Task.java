package shared;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

/**
 * Created by dmitry on 14.04.15.
 */

public class Task implements Serializable {

    public int id;
    public String title;
    public Boolean opened;

    @JsonCreator
    public Task(@JsonProperty("title") String title) {
        this.title = title;
        this.opened = true;
    }

    public Task(@JsonProperty("title") String title, @JsonProperty("id") int id) {
        this.title = title;
        this.opened = true;
        this.id = id;
    }

    public Task(@JsonProperty("title") String title, @JsonProperty("id") int id, @JsonProperty("opened") boolean opened) {
        this.title = title;
        this.opened = opened;
        this.id = id;
    }

    public Task() {
    }

}
