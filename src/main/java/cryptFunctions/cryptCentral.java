package cryptFunctions;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;

/**
 * A class used to encrypt, decrypt files and generate new secret keys
 */

public class cryptCentral {

    //Current loaded key used for encryption
    private SecretKey currentKey;
    private final String suffix = ".key";

    public cryptCentral(){
        currentKey = null;
    }

    //Given a path to the serialized key, load it from the file
    //If not successfull return false
    public boolean loadKey(String filePath){
        File keyFile = new File(filePath);
        if (keyFile.canRead() == false){
            System.out.println("Cannot read from key file!");
            return false;
        }

        try {
            ObjectInputStream keyFileStream = new ObjectInputStream(new FileInputStream(keyFile.getAbsolutePath()));
            this.currentKey = (SecretKey) keyFileStream.readObject();
            return true;
        }
        catch (ClassNotFoundException CNFE){
            System.out.println("Not supported key format!");
            return false;
        }
        catch (IOException IOE){
            System.out.println("Error while reading from the key file!");
            return false;
        }
    }

    //Generate new key and save it to the file targetName - return true on success
    public boolean generateNewKey(String targetName){
        SecureRandom secRandom = new SecureRandom();
        SecretKey secrKey = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, secRandom);
            secrKey = keyGen.generateKey();
        }
        catch (java.security.NoSuchAlgorithmException NSAE){
            System.out.println("Error while generating the key. Encryption algorithm not found.");
            return false;
        }

        try {
            File targetFile = new File(targetName + suffix);
            ObjectOutputStream ooe = new ObjectOutputStream(new FileOutputStream(targetFile.getAbsolutePath()));
            ooe.writeObject(secrKey);
        }
        catch (IOException IOE){
            System.out.println("Error while creating the output stream");
            return false;
        }
        return true;
    }


}
