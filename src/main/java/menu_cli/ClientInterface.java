package menu_cli;

import crypt_functions.CryptKey;
import crypt_functions.KeyFactory;
import encryption.FileEncrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * A basic console interface for the application
 */

public class ClientInterface {

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
        KeyFactory keyFactory = new KeyFactory();
        FileEncrypt fEncrypt = new FileEncrypt();
        CryptKey key;
        try {
            key = keyFactory.loadKey("klic");
            fEncrypt.encryptFile("/home/jakub/Java/CryptSw/src/main/tests/testText1", key);
            fEncrypt.decryptFile("/home/jakub/Java/CryptSw/src/main/tests/testText1", key);
        }
        catch (ClassNotFoundException CNFE){
            System.out.println("Class not found!");
            return;
        }
        catch (InvalidAlgorithmParameterException | NoSuchPaddingException  | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException NSAE){
            System.out.println("Error while decrypting the file!");
            return;
        }
        catch (InvalidKeyException IKE){
            System.out.println("Wrong key!");
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

        ClientInterface clientInterface = new ClientInterface();
        clientInterface.printDirectory();

    }
}
