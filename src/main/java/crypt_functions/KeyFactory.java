package crypt_functions;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.security.NoSuchAlgorithmException;

/**
 * A factory for crating new encryption keys
 */

public class KeyFactory {

    private final String suffix = ".key";

    public KeyFactory(){}

    /** Generate new key and return it */
    public CryptKey generateKey() throws NoSuchAlgorithmException {
        CryptCentral central = new CryptCentral();
        return central.generateKeyVecPair();
    }

    /** Save key into the given file */
    public void saveKey(CryptKey crKey, String targetFile) throws IOException {
        String finalName = targetFile + this.suffix;
        File target = new File(finalName);
        //In case we would overwrite the given file
        if (target.exists()){
            throw new FileAlreadyExistsException(finalName);
        }
        try {
            ObjectOutputStream ooe = new ObjectOutputStream(new FileOutputStream(finalName));
            ooe.writeObject(crKey);
        }
        catch (IOException IOE){
            throw new IOException(IOE);
        }
    }
    /** Load the key from the given file*/
    public CryptKey loadKey(String fileName) throws IOException, ClassNotFoundException {
        String finalName = fileName + this.suffix;
        File target = new File(finalName);
        if (target.exists() == false){
            throw new FileNotFoundException();
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(finalName));
            return (CryptKey) ois.readObject();
        }
        catch (ClassNotFoundException CNFE){
            throw new ClassNotFoundException();
        }
        catch (IOException IOE){
            throw new IOException(IOE);
        }
    }

}
