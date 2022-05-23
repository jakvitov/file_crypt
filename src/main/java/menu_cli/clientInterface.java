package menu_cli;

import crypt_functions.CryptKey;
import crypt_functions.cryptCentral;
import crypt_functions.keyFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * A basic console interface for the application
 */

public class clientInterface {

    private File currentDirectory = new File("./");

    //Print the current directory where the software is launched, if a key is detected, notify the user
    public void printDirectory(){
        System.out.println("Files in the current directory:");
        String [] files = currentDirectory.list();
        for (String i : files){
            if (i.endsWith(".key")){
                System.out.println("- /" + i + " -> Available key");
                continue;
            }
            else if (i.endsWith(".vec")){
                System.out.println("- /" + i + " -> Available vector");
                continue;
            }
            System.out.println("- /" + i);
        }
    }

    public static void main(String[] args) {
        keyFactory keyFactory = new keyFactory();
        CryptKey key;
        try {
            key = keyFactory.generateKey();
            keyFactory.saveKey(key, "klic");
            keyFactory.loadKey("klic");
        }
        catch (NoSuchAlgorithmException NSAE){
            System.out.println("Algorithm not found!");
            return;
        }
        catch (ClassNotFoundException CNFE){
            System.out.println("Class not found!");
            return;
        }
        catch (FileNotFoundException FNFE){
            System.out.println("File not found!");
            return;
        }
        catch (IOException IOE){
            System.out.println("Error while saving the key.");
            return;
        }

        clientInterface clientInterface = new clientInterface();
        clientInterface.printDirectory();

    }
}
