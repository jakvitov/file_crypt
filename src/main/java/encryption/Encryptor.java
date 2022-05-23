package encryption;

import crypt_functions.CryptKey;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * A class used to encrypt and decrypt bytes of data using loaded crypt keys
 */
public class Encryptor {

    /** Return encrypted byte[] using the key*/
    public static byte [] encrypt(byte [] inputData, CryptKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        final String algorithm = "AES/CBC/PKCS5PADDING";
        SecretKey secrKey = key.getKey();
        byte [] iVector = key.getInitVector();

        Cipher cipher = Cipher.getInstance(algorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iVector);

        cipher.init(Cipher.ENCRYPT_MODE, secrKey, ivParameterSpec);

        return cipher.doFinal(inputData);
    }

    /** Return decrypted byte [] using key*/
    public static byte [] decrypt(byte [] encryptedData, CryptKey key) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKey secrKey = key.getKey();
        byte [] iVector = key.getInitVector();

        final String algorithm = "AES/CBC/PKCS5PADDING";
        Cipher cipher = Cipher.getInstance(algorithm);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iVector);
        cipher.init(Cipher.DECRYPT_MODE, secrKey, ivParameterSpec);

        return cipher.doFinal(encryptedData);
    }

}
