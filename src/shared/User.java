package shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dmitry on 14.04.15.
 */

public class User implements Serializable {
    public String login;

    @JsonCreator
    public User(@JsonProperty("login") String login) {
        this.login = login;
    }

    public User() {
    }
}
