package shared;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

/**
 * Created by dmitry on 14.04.15.
 */

public class User implements Serializable {
    @JsonProperty("email")
    public String email;
    @JsonIgnore
    public String hashedPassword;
    @JsonIgnore
    public String salt;

    @JsonCreator
    public User(String email) {
        this.email = email;
    }

    @JsonCreator
    public User(String email, String hashedPassword, String salt) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public User() {
    }
}
