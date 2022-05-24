package encryption;

import crypt_functions.CryptKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** A class for encryption of all files in the given directory */

public class DirectoryEncrypt {

    public DirectoryEncrypt(){}

    /** Recurisvely tranverse the whole given directory and encrypt each file */
    public void encryptDirectory(Path dirPath, CryptKey key) throws IOException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Stream<Path> pathStream = Files.walk(dirPath);
        FileEncrypt fEncrypt = new FileEncrypt();
        ArrayList<Path> files = (ArrayList<Path>) pathStream.collect(Collectors.toList());
        for (Path i : files){
            if (Files.isDirectory(i) || Files.isWritable(i) == false){
                continue;
            }
            fEncrypt.encryptFile(i.toAbsolutePath().toString(), key);
        }
    }

    /** Recurisvely tranverse the whole given directory and decrypt each file */
    public void decryptDirectory(Path dirPath, CryptKey key) throws IOException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Stream<Path> pathStream = Files.walk(dirPath);
        FileEncrypt fEncrypt = new FileEncrypt();
        ArrayList<Path> files = (ArrayList<Path>) pathStream.collect(Collectors.toList());
        for (Path i : files){
            if (Files.isDirectory(i) || Files.isWritable(i) == false){
                continue;
            }
            fEncrypt.decryptFile(i.toAbsolutePath().toString(), key);
        }
    }
}
