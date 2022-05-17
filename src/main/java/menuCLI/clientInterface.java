package menuCLI;

import cryptFunctions.cryptCentral;

import java.io.File;

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
        cryptCentral crypt = new cryptCentral();
        if (crypt.generateKeyVecPair("eliska")){
            System.out.println("Error while creating the Key - Vector pair.");
        }
        System.out.println(crypt.loadKey("eliska"));

        clientInterface clientInterface = new clientInterface();
        clientInterface.printDirectory();

    }
}
