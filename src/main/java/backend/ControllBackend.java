package backend;

import crypt_functions.CryptKey;
import crypt_functions.KeyFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/** This class provides a connection of the frontend and backed via one class */
public class ControllBackend {

    private KeyFactory keyFactory;
    private CryptKey scopeKey;

    public ControllBackend() {
        this.keyFactory = new KeyFactory();
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

}
