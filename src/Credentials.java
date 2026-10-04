import java.io.Serial;
import java.io.Serializable;

public class Credentials implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String website;
    private final String username;
    private final String password;

    public Credentials(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Website: " + website + "\nUsername: " + username + "\nPassword: " + password;
    }

}