package OnlineLibrary.src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administration {
    protected List<Book> books;
    protected Admin[] admins;
    protected List<User> users;

    public Administration() {

        //admin constructor

        try {
            File admin = new File("src\\OnlineLibrary\\Files\\Admins.txt");
            Scanner scanner = new Scanner(admin);
            this.admins = new Admin[3];
            int counter = 0;
            while (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(", ");
                admins[counter] = new Admin(input[0], input[1]);
                counter++;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //books constructor

        this.books = new ArrayList<>();

        try {

            File catalogueTXT = new File("src\\OnlineLibrary\\Files\\Catalogue.txt");
            Scanner sc = new Scanner(catalogueTXT);
            List<String[]> books = new ArrayList<>();
            while (sc.hasNextLine()) {
                books.add(sc.nextLine().split(", "));
            }
            if (books.size() != 0) {
                for (int i = 0; i < books.size(); i++) {
                    String name = books.get(i)[1];
                    File directory = new File("src\\OnlineLibrary\\Files\\" + name + ".ser");
                    Scanner scanner = new Scanner(directory);
                    FileInputStream newInput = new FileInputStream(directory);
                    ObjectInputStream input = new ObjectInputStream(newInput);
                    Book readCase = (Book) input.readObject();
                    this.books.add(readCase);
                    input.close();
                    newInput.close();
                    scanner.close();
                }

            }
            sc.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // users constructor
        this.users = new ArrayList<>();

        try {
            File usersTxt = new File("src\\OnlineLibrary\\Files\\Users\\Users.txt");
            Scanner scan = new Scanner(usersTxt);
            List<String[]> usersList = new ArrayList<>();
            while (scan.hasNextLine()) {
                usersList.add(scan.nextLine().split(", "));
            }
            if (usersList.size() != 0) {
                for (int i = 0; i < usersList.size(); i++) {
                    String name = usersList.get(i)[1];
                    File directory = new File("src\\OnlineLibrary\\Files\\Users\\" + name + ".ser");
                    Scanner scanner = new Scanner(directory);
                    FileInputStream newInput = new FileInputStream(directory);
                    ObjectInputStream input = new ObjectInputStream(newInput);
                    User readCase = (User) input.readObject();
                    this.users.add(readCase);
                    input.close();
                    newInput.close();
                    scanner.close();
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mainMenu() {
        System.out.println("Welcome! Choose one of the following options:");
        System.out.println("");
        System.out.println("Sign in as administrator");
        System.out.println("Sign in as user");
        System.out.println("Sign up as user");
        System.out.println("Exit");
        System.out.println("");
        System.out.print("Enter your choice here: ");
        System.out.println("");
        Administration.choiceExecution();
    }

    public static void adminMenu() {
        System.out.println("Choose one of the following options:");
        System.out.println("");
        System.out.println("Add a book");
        System.out.println("Delete a book");
        System.out.println("Sign out");
        System.out.println("");
        System.out.print("Enter your choice here: ");
        System.out.println("");
    }

    public static void userMenu() {
        System.out.println("Choose one of the following options:");
        System.out.println("");
        System.out.println("Search for a book");
        System.out.println("Deposit");
        System.out.println("Sign out");
        System.out.println("");
        System.out.print("Enter your choice here: ");
        System.out.println("");
    }

    public static void postSearchMenu() {
        System.out.println("");
        System.out.println("Rent the book");
        System.out.println("Search for another book");
        System.out.println("Deposit");
        System.out.println("Sign out");
    }

    public static void postInvalidSearchMenu() {
        System.out.println("");
        System.out.println("Search for another book");
        System.out.println("Deposit");
        System.out.println("Sign out");
    }

    public static void choiceExecution() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "Sign in as administrator":
                Administration.signInAsAdmin();
                Administration.adminMenu();
                Administration.adminCommandExecution();
                break;
            case "Sign in as user":
                Administration.signInAsUser();
                Administration.userChoiceExecution();
                // метод за влизане като потребител
                break;
            case "Sign up as user":
                Administration.registration();
                // метод за регистрация като потребител
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                System.out.println("Wrong command! Try again.");
                System.out.println("");
                Administration.mainMenu();
                Administration.choiceExecution();
                break;
        }
    }

    public static boolean adminObjectsCheck(String adminName, String adminPassword) {
        Administration adminsArr = new Administration();
        boolean correct = false;
        for (int i = 0; i < 3; i++) {
            if (adminName.equals(adminsArr.admins[i].adminName)) {
                if (adminsArr.admins[i].adminPassword.equals(adminPassword)) {
                    correct = true;
                    break;
                }
            }
        }
        return correct;
    }

    public static void signInAsAdmin() {
        Scanner scanner = new Scanner(System.in);
        String adminName;
        String adminPassword;


        System.out.println("Enter your admin name:");
        adminName = scanner.nextLine();
        System.out.println("Enter your admin password:");
        adminPassword = scanner.nextLine();
        if (Administration.adminObjectsCheck(adminName, adminPassword)) {
            System.out.println("Hello, " + adminName);
        } else {
            System.out.println("Wrong name or password!");
            System.out.println("");
            Administration.mainMenu();
        }

    }

    public static void adminCommandExecution() {
        Scanner scanner = new Scanner(System.in);
        Administration catalogue = new Administration();
        String command = scanner.nextLine().toLowerCase();
        switch (command) {
            case "add a book":
                catalogue.setBooks();
                Administration.nextAdminCommand();
                break;
            case "delete a book":
                catalogue.deleteBook();
                Administration.nextAdminCommand();
                break;
            case "sign out":
                Administration.mainMenu();
                break;
            default:
                System.out.println("Wrong command! Try again.");
                System.out.println("");
                Administration.adminMenu();
                Administration.adminCommandExecution();
                break;
        }
    }

    public void setBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter author:");
        String author = scanner.nextLine();
        System.out.println("Enter content:");
        String content = scanner.nextLine();
        System.out.println("Enter isbn:");
        String isbn = scanner.nextLine();
        this.books.add(new Book(title, author, content, isbn));
        System.out.println("The book was added.");
        this.books.get(this.books.size() - 1).serializer(this.books.get(this.books.size() - 1).isbn, this.books.get(this.books.size() - 1));
        this.books.get(this.books.size() - 1).addToTxtFile(this.books.get(this.books.size() - 1).title, this.books.get(this.books.size() - 1).isbn);
    }

    public void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.books.size(); i++) {
            System.out.println(this.books.get(i).title);
        }
        System.out.println("Enter isbn:");
        String isbn = scanner.nextLine();
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).isbn.equals(isbn)) {
                this.books.get(i).deleteFromTxtFile(this.books.get(i).isbn);
                this.books.get(i).deleteSerFile(this.books.get(i).isbn);
                this.books.remove(i);
                System.out.println("The book was deleted.");
                break;
            }
        }

    }

    public static void nextAdminCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Continue/Exit");
        String next = scanner.nextLine().toLowerCase();
        if (next.equals("continue")) {
            Administration.adminMenu();
            Administration.adminCommandExecution();
        } else if (next.equals("exit")) {
            Administration.mainMenu();
            Administration.choiceExecution();
        }
    }

    public static void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        User newOne = new User(username, password, email);
        if (newOne.usernameVerify(username)) {
            System.out.println("This username already exists.");
            Administration.mainMenu();
        }
        if (!newOne.verifyPassword(password)) {
            System.out.println("Password should contain 8 characters, 1 digit, 1 special symbol.");
            Administration.registration();
        }
        if (!newOne.verifyEmail(email)) {
            System.out.println("Wrong email format.");
            Administration.registration();
        }
        newOne.addToTxtFile(username);
        newOne.serializer(newOne);
        System.out.println("Your registration was successful!");
        Administration.mainMenu();
    }

    public static void signInAsUser() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String enteredPass;
        System.out.println("Enter your username:");
        username = scanner.nextLine();
        System.out.println("Enter your password:");
        enteredPass = scanner.nextLine();

        Administration users = new Administration();
        boolean existingUser = false;
        for (int i = 0; i < users.users.size(); i++) {
            if (username.equals(users.users.get(i).username)) {
                if (users.users.get(i).checkPassword(enteredPass)) {
                    try {
                        File currentUser = new File("src\\OnlineLibrary\\Files\\CurrentUser.txt");
                        FileWriter currentUserWriter = new FileWriter(currentUser);
                        currentUserWriter.write(username + ", " + users.users.get(i).userID);
                        currentUserWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Administration.userMenu();
                } else {
                    System.out.println("Wrong username or password!");
                    System.out.println("");
                    Administration.mainMenu();
                }
                existingUser = true;
            }
        }
        if (!existingUser) {
            System.out.println("There is no user with such name!");
            System.out.println("");
            Administration.mainMenu();
        }
    }

    public static void userChoiceExecution() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "Search for a book":
                Administration.searchForBook();
                Administration.userPostSearchChoiceExecution();
                break;
            case "Deposit":
                Administration.deposit();
                Administration.userMenu();
                Administration.userChoiceExecution();
                break;
            case "Sign out":
                Administration.mainMenu();
                break;
            default:
                System.out.println("Wrong command! Try again.");
                System.out.println("");
                Administration.userMenu();
                Administration.userChoiceExecution();
                break;
        }
    }

    public static void userPostSearchChoiceExecution() {
        Administration.postSearchMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.print("Enter your choice here: ");
        System.out.println("");
        String command = scanner.nextLine();
        switch (command) {
            case "Rent the book":
                Administration.rentABook();
                break;
            case "Deposit":
                Administration.deposit();
                Administration.userPostSearchChoiceExecution();
                break;
            case "Search for another book":
                Administration.searchForBook();
                Administration.userPostSearchChoiceExecution();
            case "Sign out":
                Administration.mainMenu();
                break;
            default:
                System.out.println("Wrong command! Try again.");
                System.out.println("");
                Administration.postSearchMenu();
                Administration.userPostSearchChoiceExecution();
                break;
        }
    }

    public static void userPostInvalidSearchChoiceExecution() {
        Administration.postInvalidSearchMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.print("Enter your choice here: ");
        System.out.println("");
        String command = scanner.nextLine();
        switch (command) {
            case "Deposit":
                Administration.deposit();
                Administration.userPostSearchChoiceExecution();
                break;
            case "Search for another book":
                Administration.searchForBook();
                Administration.userPostSearchChoiceExecution();
            case "Exit":
                System.exit(0);
                break;
            default:
                System.out.println("Wrong command! Try again.");
                System.out.println("");
                Administration.postSearchMenu();
                Administration.userPostSearchChoiceExecution();
                break;
        }
    }

    public static void searchForBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Title:");
        String title = scanner.nextLine();
        Administration lookingFor = new Administration();
        boolean found = false;
        for (int i = 0; i < lookingFor.books.size(); i++) {
            if (lookingFor.books.get(i).searchForABook(title)) {
                found = true;
                System.out.println(lookingFor.books.get(i).title);
                System.out.println(lookingFor.books.get(i).author);
                try {
                    Administration users = new Administration();
                    User currentUser = null;
                    File currentUserInfo = new File("src\\OnlineLibrary\\Files\\CurrentUser.txt");
                    Scanner scanner1 = new Scanner(currentUserInfo);
                    String[] userInfo = scanner1.nextLine().split(", ");
                    for (int j = 0; j < users.users.size(); j++) {
                        if (userInfo[1].equals(users.users.get(j).userID)) {
                            currentUser = users.users.get(j);
                        }
                    }
                    assert currentUser != null;
                    FileWriter searchedBook = new FileWriter("src\\OnlineLibrary\\Files\\" + currentUser.userID + "SearchedBooks.txt", true);
                    searchedBook.write(title + " - searched\n");
                    searchedBook.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            }
        }
        if (!found) {
            System.out.println("Sorry! There is no book with this title in our library.");
            Administration.userPostInvalidSearchChoiceExecution();
        }
    }

    public static void rentABook() {
        try {
            Administration users = new Administration();
            User currentUser = null;
            File currentUserInfo = new File("src\\OnlineLibrary\\Files\\CurrentUser.txt");
            Scanner scanner = new Scanner(currentUserInfo);
            String[] userInfo = scanner.nextLine().split(", ");
            for (int i = 0; i < users.users.size(); i++) {
                if (userInfo[1].equals(users.users.get(i).userID)) {
                    currentUser = users.users.get(i);
                }
            }
            scanner.close();

            assert currentUser != null;
            if (currentUser.deposit < 2) {
                System.out.println("Not enough money!");
                Administration.userPostSearchChoiceExecution();
            } else {
                currentUser.deposit = currentUser.deposit - 2;
                System.out.println("The book was rented successfully!");
                System.out.println("Current deposit amount: " + currentUser.deposit);
                currentUser.serializer(currentUser);
                System.out.println("");
                File bookSearched = new File("src\\OnlineLibrary\\Files\\" + currentUser.userID + "SearchedBooks.txt");
                Scanner scanner1 = new Scanner(bookSearched);
                List<String[]> searched = new ArrayList<>();
                while (scanner1.hasNextLine()) {
                    searched.add(scanner1.nextLine().split(" - "));
                }
                Administration books = new Administration();
                int lastSearch = searched.size() - 1;
                for (int i = 0; i < books.books.size(); i++) {
                    if (books.books.get(i).title.equals(searched.get(lastSearch)[0])) {
                        FileWriter rentedBook = new FileWriter(bookSearched, true);
                        rentedBook.write(books.books.get(i).title + " - rented\n");
                        rentedBook.close();
                        System.out.println(books.books.get(i).content);
                    }
                }
                Administration.backMenu();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void backMenu() {
        System.out.println("");
        System.out.println("Back");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        switch (command){
            case "Back":
                Administration.userMenu();
                Administration.userChoiceExecution();
            case default:
                System.exit(0);
                break;
        }
    }

    public static void deposit() {
        try {
            Administration users = new Administration();
            User currentUser = null;
            File currentUserInfo = new File("src\\OnlineLibrary\\Files\\CurrentUser.txt");
            Scanner scanner = new Scanner(currentUserInfo);
            String[] userInfo = scanner.nextLine().split(", ");
            for (int i = 0; i < users.users.size(); i++) {
                if (userInfo[1].equals(users.users.get(i).userID)) {
                    currentUser = users.users.get(i);
                }
            }
            scanner.close();
            assert currentUser != null;
            System.out.println("Current amount: " + currentUser.deposit);
            Scanner scannerDep = new Scanner(System.in);
            System.out.println("Enter the deposit amount:");
            double depositAmount = Double.parseDouble(scannerDep.nextLine());
            currentUser.deposit = currentUser.deposit + depositAmount;
            System.out.println("Current amount: " + currentUser.deposit);
            currentUser.serializer(currentUser);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
