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
    private byte[] loadedInitVector;
    private final String suffix = ".key";
    private final String vectorSuffix = ".vec";
    private final String cyperAlgo = "AES/CBC/PKCS5PADDING";

    public cryptCentral(){
        currentKey = null;
        loadedInitVector = null;
    }

    //Given a path to the serialized key, load it from the file
    //If not successfull return false
    public boolean loadKey(String filePath){
        File keyFile = new File(filePath + suffix);
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

   //Create return initialization vector for the AES encr. algo as a byte array and save it to a config. file
    public byte[] createInitializationVector(){
        byte[] initVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initVector);
        return initVector;
    }

    //Save initVector as byte[]
    public boolean saveInitVector(byte[] initVector, String targetName){
        try {
            ObjectOutputStream ooe = new ObjectOutputStream(new FileOutputStream(targetName + vectorSuffix));
            ooe.writeObject(initVector);
        }
        catch (IOException IOE){
            System.out.println("Error while crating the initialization vector file.");
            return false;
        }
        return true;
    }

    //Load innit vector from file with the given targetName
    public boolean loadInitVector(String targetName){
        try{
            ObjectInputStream ooi = new ObjectInputStream(new FileInputStream(targetName + vectorSuffix));
            this.loadedInitVector = (byte[]) ooi.readObject();
        }
        catch (ClassNotFoundException CNFE){
            System.out.println("Cannot load the vec file.");
            return false;
        }
        catch (IOException IOE){
            System.out.println("Error while reading from the file.");
            return false;
        }
        return true;
    }

    //Generate new key and initialization vector and save them to the targetName file
    //Check of the keys of this name already exist to prevent from overwriting them
    public boolean generateKeyVecPair(String targetName){

        File target = new File(targetName + suffix);
        if (target.exists()){
            System.out.println("Key of this name already exists!");
            return false;
        }

        target = new File(targetName + vectorSuffix);
        if (target.exists()){
            System.out.println("Vector of this name already exists!");
            return false;
        }

        if (generateNewKey(targetName) == false ){
            return false;
        }
        byte [] iVector = createInitializationVector();
        if (saveInitVector(iVector, targetName)){
            return false;
        }
        return true;
    }

    //Load the init vector and key of the given name
    public boolean loadKeyVecPair(String target){
        File keyFile = new File(target + suffix);
        File vectorFile = new File(target + vectorSuffix);

        if (keyFile.exists() == false || keyFile.canRead() == false){
            System.out.println("The key file does not exist or you cannot read it.");
            return false;
        }

        if (vectorFile.exists() == false || vectorFile.canRead() == false){
            System.out.println("The vector file does not exist or you cannot read it.");
            return false;
        }

        if (loadKey(target) == false ){
            System.out.println("Error while loading the key.");
            return false;
        }

        if (loadInitVector(target) == false){
            System.out.println("Loading the init vector failed.");
            return false;
        }

        return true;
    }

}
