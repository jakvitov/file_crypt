package crypt_functions;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A class used to encrypt, decrypt files and generate new secret keys
 */

public class CryptCentral {

    private final String cyperAlgo = "AES/CBC/PKCS5PADDING";

    //Generate new key and save it to the file targetName - return true on success
    public SecretKey generateNewKey() throws NoSuchAlgorithmException {
        SecureRandom secRandom = new SecureRandom();
        SecretKey secrKey = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, secRandom);
            secrKey = keyGen.generateKey();
        }
        catch (java.security.NoSuchAlgorithmException NSAE){
            throw new NoSuchAlgorithmException();
        }
        return secrKey;
    }

   //Create return initialization vector for the AES encr. algo as a byte array and save it to a config. file
    public byte[] createInitializationVector(){
        byte[] initVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initVector);
        return initVector;
    }

    public CryptKey generateKeyVecPair() throws NoSuchAlgorithmException {
        SecretKey key = generateNewKey();
        byte [] iVector = createInitializationVector();
        CryptKey cryptKey = new CryptKey(key, iVector);
        return cryptKey;
    }

}
