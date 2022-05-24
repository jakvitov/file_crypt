package menu_cli;

import crypt_functions.CryptKey;
import crypt_functions.KeyFactory;
import encryption.DirectoryEncrypt;
import encryption.FileEncrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
        try {
            KeyFactory keyFactory = new KeyFactory();
            CryptKey key = keyFactory.generateKey("1234");
            DirectoryEncrypt dirEncr = new DirectoryEncrypt();
            dirEncr.decryptDirectory(Path.of("/home/jakub/Java/CryptSw/src/main/tests/test_dir"), key);
        }
        catch (InvalidAlgorithmParameterException | NoSuchPaddingException  | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException IKSE){
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
