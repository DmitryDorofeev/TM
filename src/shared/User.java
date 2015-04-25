package shared;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

/**
 * Created by dmitry on 14.04.15.
 */

public class User implements Serializable {

    public String email;
    @JsonIgnore
    public String hashedPassword;
    @JsonIgnore
    public String salt;

    @JsonCreator
    public User(@JsonProperty("email") String email) {
        this.email = email;
    }

    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public User() {
    }
}
