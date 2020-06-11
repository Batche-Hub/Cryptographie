/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.cryptage.des;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Boule
 */
public abstract class CryptageDES {

    private static final Integer KEY_SIZE = 56;
    
    
    public CryptageDES() {
    }
     
        public static final byte[] encrypter(String message,SecretKey cle)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{

        
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        System.err.println("Cipher : "+cipher.getProvider().getInfo());
        
        cipher.init(Cipher.ENCRYPT_MODE, cle);        
        byte[] messageBytes = message.getBytes("UTF8");       
        byte[] cipherBytes = cipher.doFinal(messageBytes);        


        return cipherBytes;
        } 
    
        public static final String decrypter(byte[] messageCrypte,SecretKey cle) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
            
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, cle);
        return new String(cipher.doFinal(messageCrypte), "UTF-8");
}
        
        public static final SecretKey generateKey() throws NoSuchAlgorithmException{
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        keygen.init(KEY_SIZE);
        return keygen.generateKey();
        }
        
        public static final byte[] convertKeyToBytes(SecretKey key) throws IOException{
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos)){
            out.writeObject(key);
            return bos.toByteArray();
        }
    }
    
    public static final SecretKey convertKeyFromBytes(byte[] keyData) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream bis = new ByteArrayInputStream(keyData);
                ObjectInput in = new ObjectInputStream(bis)){
            return (SecretKey) in.readObject();
        }          
    }
}
