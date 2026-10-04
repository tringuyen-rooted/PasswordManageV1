import java.util.*;
public class Prompts {
    Scanner scanner = new Scanner(System.in);
    AccountManager manager = new AccountManager();

    Prompts(){
        askQuestions();
    }

    private void askQuestions(){
        System.out.println("Welcome to Branched Password Manager!");

        while (true) {
            linebreak();
            System.out.println("What would you like to do?\n[L]og In | [C]reate New User | [D]elete a User | [Q]uit");
            char response = Character.toUpperCase(scanner.nextLine().charAt(0));

            switch (response){
                case ('L'):
                    login();
                    break;
                case ('C'):
                    create();
                    break;
                case('D'):
                    delete();
                    break;
                case('Q'):
                    System.out.println("Have a nice day! :)");
                    System.exit(1);
                default:
                    System.out.println("Please choose a valid option");
            }
        }
    }

    public void login(){
        String temp1;
        String temp2;
        while (true) {
            linebreak();
            System.out.print("(Type 'quit' to go back)\nEnter username:");
            temp1 = scanner.nextLine();
            if (manager.compareUser(temp1)){
                break;
            }
            else if (temp1.equalsIgnoreCase("quit")) return;
            System.out.println("Invalid Username");
        }
        while (true) {
            linebreak();
            System.out.print("(Type 'quit' to go back)\nEnter password: ");
            temp2 = scanner.nextLine();
            if (manager.comparePassword(temp1, temp2)){
                break;
            }
            else if (temp2.equalsIgnoreCase("quit")) return;
            System.out.println("Incorrect Password");
        }
        temp2 = "";
        System.out.println("Login Successful");
        (new CredentialMenu()).displayMenu(manager.getAccount(temp1),manager);
        temp1 = "";
    }

    public void create(){
        String temp1 = " ";
        String temp2 = " ";
        while (((temp1.length() < 5) || (temp1.length() > 20)) || manager.compareUser(temp1)){
            linebreak();
            System.out.print("(Type 'quit' to go back)\nPlease enter a username (Between 5-20 Characters): ");
            temp1 = scanner.nextLine();
            if (temp1.equalsIgnoreCase("quit")) return;
            else if(temp1.length() < 5) System.out.println("Username to short.");
            else if(temp1.length() > 20) System.out.println("Username to long.");
            else if(manager.compareUser(temp1)) System.out.println("Username taken");
        }
        while ((temp2.length() < 5) || (temp2.length() > 20)){
            linebreak();
            System.out.print("(Type 'quit' to go back)\nPlease enter a password (Between 5-20 Characters): ");
            temp2 = scanner.nextLine();
            if (temp2.equalsIgnoreCase("quit")) return;
            else if(temp2.length() < 5){
                System.out.println("Password to short.");
            }
            else if(temp2.length() > 20){
                System.out.println("Password to long.");
            }
        }
        manager.addAccount(temp1, temp2);
        temp1 = "";
        temp2 = "";
    }

    public void delete(){
        String temp1;
        String temp2;
        while (true) {
            linebreak();
            System.out.print("(Type 'quit' to go back)\nEnter the user you would like to delete:");
            temp1 = scanner.nextLine();
            if (manager.compareUser(temp1)){
                break;
            }
            else if (temp1.equalsIgnoreCase("quit")) return;
            System.out.println("Invalid Username");
        }
        while (true) {
            linebreak();
            System.out.print("(Type 'quit' to go back)\nEnter password to confirm: ");
            temp2 = scanner.nextLine();
            if (manager.comparePassword(temp1, temp2)){
                break;
            }
            else if (temp2.equalsIgnoreCase("quit")) return;
            System.out.println("Incorrect Password");
        }
        temp2 = "";
        manager.removeAccount(temp1);
    }

    void linebreak(){
        System.out.println("========================");
    }

}
