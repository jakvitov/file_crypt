package crypt_functions;

import javax.crypto.SecretKey;
import java.io.Serializable;

/**
 * A class holding all info for sucessfull encryption and decryption
 */

public class CryptKey implements Serializable {

    private SecretKey key;
    private byte [] initVector;
    private final String suffix = ".key";

    public CryptKey(SecretKey key, byte[] initVector) {
        this.key = key;
        this.initVector = initVector;
    }

    public SecretKey getKey() {
        return key;
    }

    public byte[] getInitVector() {
        return initVector;
    }


}
