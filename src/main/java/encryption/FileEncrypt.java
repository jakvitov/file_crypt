package encryption;


import crypt_functions.CryptKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * A class that handles encryption and decryption of the whole files
 */

public class FileEncrypt {

    public FileEncrypt(){}

    /** Encrypt the whole file given its name and key*/
    public void encryptFile(String fileName, CryptKey key) throws IOException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path file = Paths.get(fileName);
        byte [] fileBytes = Files.readAllBytes(file);

        byte [] encryptedBytes = Encryptor.encrypt(fileBytes, key);

        System.out.println();
        Files.write(file, encryptedBytes);
    }

    /**Given a specific fileName encrypt given file using the key*/
    public void decryptFile(String fileName, CryptKey key) throws IOException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path file = Paths.get(fileName);
        byte [] encrBytes = Files.readAllBytes(file);

        byte [] decryptedBytes = Encryptor.decrypt(encrBytes, key);
        Files.write(file, decryptedBytes);
    }
}
