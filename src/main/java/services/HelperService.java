
package services;


import entities.Process;
import entities.Ddnode;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;

public class HelperService {
    public static byte[] encryptSymmetricKey(SecretKey symmetricKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // contact.getPublicKey returns a public key of type Key
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // skey is the SecretKey used to encrypt the AES data
        byte[] key = cipher.doFinal(symmetricKey.getEncoded());
        return key;
    }
    public static String encryptInput(String input, SecretKey key)throws Exception {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }


    public static String decryptInput(String encryptInput, SecretKey symmetricKey) throws Exception{
        SecretKey key = symmetricKey;
        byte[] iv = new byte[16];
        Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
        // turn the mode of cipher to decryption
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv)); // reuse the key and iv generated before
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(encryptInput));
        return new String(plainText);
    }

    public static SealedObject encryptObject(ArrayList<Process> processes, SecretKey secretKey) throws Exception{
        // generate symmetric key & generate IV
        SecretKey key = secretKey;
        byte[] iv = new byte[16];
        // create cipher
        Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        // create sealed object
        SealedObject sealedEm1 = new SealedObject(processes, cipher);
        return sealedEm1;
    }
    public static SealedObject encryptObject(Ddnode nodes, SecretKey secretKey) throws Exception{
        // generate symmetric key & generate IV
        SecretKey key = secretKey;
        byte[] iv = new byte[16];
        // create cipher
        Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        // create sealed object
        SealedObject sealedEm1 = new SealedObject(nodes, cipher);
        return sealedEm1;
    }
}
