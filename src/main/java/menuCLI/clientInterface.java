package menuCLI;

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
            System.out.println("- /" + i);
        }
    }

    public static void main(String[] args) {
        clientInterface clientInterface = new clientInterface();
        clientInterface.printDirectory();

    }
}
