package crypt_functions;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

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

   /** Generate secret key from hash adding salt */
    public SecretKey generateNewKey(String hash, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(hash.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey finalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return finalKey;
    }

    /** Create initialization Vector from the given hash */
    public byte [] createInitializationVector (String inputHash){
        byte [] hash = Integer.toString(inputHash.hashCode()).getBytes();
        byte [] result = new byte[16];

        for (int i = 0; i < Math.min(17, hash.length); i ++){
            result[i] = hash[i];
        }

        //If the hash is too short we need to expand it to 16 characters
        if (hash.length < 16){
            for (int i = hash.length - 1; i < 16; i++){
                result[i] = '3';
            }
        }
        return result;
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

    /** Generate new Crypt key from the given pin */
    public CryptKey generateKeyVecPair(String pin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey key = generateNewKey(Integer.toString(pin.hashCode()), pin);
        byte [] iVector = createInitializationVector(Integer.toString(pin.hashCode()));
        CryptKey cryptKey = new CryptKey(key, iVector);
        return cryptKey;
    }
}
