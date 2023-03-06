package OnlineLibrary.src;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book implements java.io.Serializable {
    protected String title;
    protected String author;
    protected String content;
    protected String isbn;

    public Book(String title, String author, String content, String isbn) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.isbn = isbn;
    }

    public void serializer(String isbn, Book name) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\OnlineLibrary\\Files\\" + isbn + ".ser");
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

    public void addToTxtFile(String title, String isbn) {
        try {
            File catalogue = new File("src\\OnlineLibrary\\Files\\Catalogue.txt");
            FileWriter fileWriter = new FileWriter(catalogue, true);
            fileWriter.write(title + ", " + isbn + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromTxtFile(String isbn) {
        try {
            File catalogue = new File("src\\OnlineLibrary\\Files\\Catalogue.txt");
            Scanner scanner = new Scanner(catalogue);
            List<String> fileContent = new ArrayList<>();
            String[] lineContent = new String[2];
            while (scanner.hasNextLine()) {
                lineContent = scanner.nextLine().split(", ");
                if (!lineContent[1].equals(isbn)) {
                    fileContent.add(lineContent[0] + ", " + lineContent[1]);
                }
            }
            scanner.close();
            FileWriter fileWriter = new FileWriter(catalogue);
            fileWriter.write("");
            fileWriter.close();

            fileWriter = new FileWriter(catalogue, true);
            for (int i = 0; i < fileContent.size(); i++) {
                fileWriter.write(fileContent.get(i) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSerFile(String isbn) {
        File toDelete = new File("src\\OnlineLibrary\\Files\\" + isbn + ".ser");
        if (toDelete.delete()) {
            System.out.println("The .ser file is deleted.");
        }
    }

    public boolean searchForABook(String title) {
        if (this.title.equals(title)) {
            return true;
        } else {
            return false;
        }
    }
}
