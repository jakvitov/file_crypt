package backend;

import crypt_functions.CryptKey;
import crypt_functions.KeyFactory;
import encryption.DirectoryEncrypt;
import encryption.FileEncrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/** This class provides a connection of the frontend and backed via one class */
public class ControllBackend {

    private KeyFactory keyFactory;
    private CryptKey scopeKey;
    private FileEncrypt fEncrypt;
    private DirectoryEncrypt dirEncrypt;

    public ControllBackend() {
        this.keyFactory = new KeyFactory();
        this.fEncrypt = new FileEncrypt();
        this.dirEncrypt = new DirectoryEncrypt();
    }

    public void generateKey() throws NoSuchAlgorithmException {
        this.scopeKey = this.keyFactory.generateKey();
    }

    public void generateKey(String pin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.scopeKey = this.keyFactory.generateKey(pin);
    }

    public void saveKey(Path savePath) throws IOException {
        this.keyFactory.saveKey(this.scopeKey, savePath.toAbsolutePath().toString());
    }

    public void loadKey(Path loadPath) throws IOException, ClassNotFoundException {
        this.scopeKey =  this.keyFactory.loadKey(loadPath.toAbsolutePath().toString());
    }

    public String loadManual() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/text/manual.txt")));
    }

    public boolean isKeyLoaded(){
        if (this.scopeKey == null) {
            return false;
        }
        return true;
    }

    public void encryptFile(Path file) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.fEncrypt.encryptFile(file.toAbsolutePath().toString(), this.scopeKey);
    }

    public void decryptFile(Path file) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.fEncrypt.decryptFile(file.toAbsolutePath().toString(), this.scopeKey);
    }

    public void encryptDirectory(Path directory) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.dirEncrypt.encryptDirectory(directory, this.scopeKey);
    }

    public void decrpytDirectory(Path directory) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.dirEncrypt.decryptDirectory(directory, this.scopeKey);
    }


}
