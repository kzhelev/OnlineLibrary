package OnlineLibrary.src;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements java.io.Serializable {
    protected String username;
    private String password;
    protected String email;
    protected String userID;
    protected LocalDateTime registrationDay;
    protected double deposit;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = String.valueOf(Instant.now().getEpochSecond());
        this.registrationDay = LocalDateTime.now();
        this.deposit = 0;
    }

    public boolean checkPassword(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyPassword(String password) {
        Pattern digits = Pattern.compile("[\\d+][a-z]|[A-Z][\\w+]");
        Matcher matcher = digits.matcher(password);
        if (matcher.find() && password.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyEmail(String email) {
        Pattern digits = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcher = digits.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean makeDeposit() {
        Scanner scanner = new Scanner(System.in);
        try {
            int deposit = Integer.parseInt(scanner.nextLine());
            this.deposit += deposit;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addToTxtFile(String username) {
        try {
            File users = new File("src\\OnlineLibrary\\Files\\Users\\Users.txt");
            FileWriter fileWriter = new FileWriter(users, true);
            fileWriter.write(username + ", " + this.userID + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializer(User name) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\OnlineLibrary\\Files\\Users\\" + this.userID + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(name);
            fileOut.close();
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean usernameVerify(String username) {
        try {
            File users = new File("src\\OnlineLibrary\\Files\\Users\\Users.txt");
            Scanner scanner = new Scanner(users);
            String[] info = new String[2];
            boolean exist = false;
            while (scanner.hasNextLine()) {
                info = scanner.nextLine().split(", ");
                if (username.equals(info[0])) {
                    exist = true;
                    break;
                }

            }
            return exist;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
