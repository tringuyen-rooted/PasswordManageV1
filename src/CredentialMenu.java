import java.util.*;

public class CredentialMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(Account account, AccountManager managerEsquire){

        linebreak();
        System.out.println("Hello " + account.getUser() + "!");
        while (true) {
            linebreak();
            System.out.println("What would you like to do?\n[S]how Saved Credentials | [A]dd Credentials | [R]emove Credentials | [L]og Out");

            char response = Character.toUpperCase(scanner.nextLine().charAt(0));

            if (response == 'L') {
                System.out.println("Logging Out...");
                break;
            }

            switch (response) {
                case ('S'):
                    showList(account);
                    break;
                case ('A'):
                    addCredential(account, managerEsquire);
                    break;
                case ('R'):
                    removeCredential(account, managerEsquire);
                    break;
                default: System.out.println("Please enter a valid option");
            }
        }
    }

    private void showList(Account account){
        if (account.getCredentialsList().isEmpty()) {
            System.out.println("You do not currently have any saved credentials.");
            return;
        }
        for (Credentials c : account.getCredentialsList()){
            System.out.println(c);
            System.out.println();
        }
    }

    private void addCredential(Account account, AccountManager manager){

        linebreak();
        System.out.print("Enter website name (or 'quit): ");
        String website = scanner.nextLine();
        if (website.equalsIgnoreCase("quit")) return;

        linebreak();
        System.out.print("Enter account username (or 'quit): ");
        String username = scanner.nextLine();
        if (username.equalsIgnoreCase("quit")) return;

        linebreak();
        System.out.print("Enter account password (or 'quit'): ");
        String password = scanner.nextLine();
        if (password.equalsIgnoreCase("quit")) return;

        account.addCredential(website, username, password);
        manager.save();
        System.out.println("Credential added successfully!");

    }

    private void removeCredential(Account account, AccountManager manager){
        if (account.getCredentialsList().isEmpty()) {
            System.out.println("No credentials available to remove.");
            return;
        }

        System.out.print("Enter the website/service name of the credential to remove:");
        String targetSite = scanner.nextLine();

       boolean removed = account.getCredentialsList().removeIf(
               credentials -> credentials.getWebsite().equalsIgnoreCase(targetSite)
       );
       if (removed) {
           manager.save();
           System.out.println("Credential Removed Successfully");
       } else {
           System.out.println("No matching credential found.");
       }

    }

    void linebreak(){
        System.out.println("========================");
    }

}

