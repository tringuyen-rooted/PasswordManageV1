import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> accounts;

    AccountManager() {
        this.accounts = ArrayKeystore.startup();
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
    }

    public void addAccount(String username, String password) {
        Account newAccount = new Account(username, password);
        accounts.add(newAccount);
        save();
        System.out.println("Account Successfully Created");
    }

    public void removeAccount(String username) {
        accounts.removeIf(acc -> acc.getUser().equals(username));
        save();
        System.out.println("Account Successfully Deleted");
    }

    public boolean compareUser(String username) {
        return accounts.stream().anyMatch(acc -> acc.getUser().equals(username));
    }

    public boolean comparePassword(String username, String password) {
        return accounts.stream().anyMatch(acc -> acc.getUser().equals(username) && acc.getPassword().equals(password));
    }

    public Account getAccount(String username) {
        for (Account acc : accounts) {
            if (acc.getUser().equals(username)) {
                return acc;
            }
        }
        return null;
    }

    public void save() {
        ArrayKeystore.encryptObject(accounts);
    }
}
