import java.io.Serial;
import java.io.Serializable;
import java.util.*;
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String user;
    private final String password;
    private ArrayList<Credentials> credentialsList;

    public Account(String user, String password){
        this.user = user;
        this.password = password;
        this.credentialsList = new ArrayList<>();
    }

    public String getUser() {return user;}
    public String getPassword() {return password;}
    public ArrayList<Credentials> getCredentialsList() {return credentialsList;}


    public void addCredential(String temp1, String temp2, String temp3){
        if (credentialsList == null) {
            credentialsList = new ArrayList<>();
        }
        credentialsList.add(new Credentials(temp1, temp2, temp3));
    }

    public void removeCredential(int i){
        if ((credentialsList != null) && ((i >= 0) && i < credentialsList.size())){
            credentialsList.remove(i);
        }
    }

}
