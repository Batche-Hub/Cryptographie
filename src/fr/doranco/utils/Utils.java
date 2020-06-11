/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Boule
 */
public abstract class Utils {
    
   private Utils() {
    }
    
    
    public static final byte[] convertToBytes(Object object) throws IOException{
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos)){
            out.writeObject(object);
            return bos.toByteArray();
        }
    }
    
    public static final Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInput in = new ObjectInputStream(bis)){
            return in.readObject();
        }          
    }
    
    public static final char[] convertStringToCharArray(String chaineAConvertir){
        char[] charArray = chaineAConvertir.toCharArray();
        return charArray;
    }
    
}
