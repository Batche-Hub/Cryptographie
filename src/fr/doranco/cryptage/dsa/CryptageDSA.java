/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.cryptage.dsa;

import fr.doranco.utils.Utils;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/**
 *
 * @author Boule
 */
public abstract class CryptageDSA {
    final static Integer KEY_SIZE = 1024;
    private static final boolean CHEAT_TEXT = false;
    private static final boolean CHEAT_SIGNATURE = false;
    private static KeyPair key_pair;
    private static byte[] documentBytes;
    private static byte[] signatureBytes;
    private static Signature signature;

    private CryptageDSA() {
    }
    
    
    /**
     * Cette méthode permet de générer une paire de clés. Une publique et une autre privée.
     * @throws NoSuchAlgorithmException 
     */
    public static void generateKey() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        key_pair = keyPairGen.generateKeyPair();
    }
    /**
     * 
     * @param stringAsigner : Il s'agit de n'importe quelle string qu'on peut signer.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException 
     */
    public static void signerDocument(String stringAsigner) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        documentBytes = Utils.convertToBytes(stringAsigner);
        signature = Signature.getInstance("DSA");
        signature.initSign(key_pair.getPrivate());
        signature.update(documentBytes);
        signatureBytes = signature.sign();
        
        if(CHEAT_TEXT){
            documentBytes[0]++;
        }
        if(CHEAT_SIGNATURE){
            signatureBytes[4]++;
        }
    }
    /**
     * Cette méthode permet de vérifier si une signature vient véritablement de l'entité supposée avoir signé un document.
     * @return
     * @throws InvalidKeyException
     * @throws SignatureException 
     */
    public static boolean verifierSignatureDocument() throws InvalidKeyException, SignatureException{
        signature.initVerify(key_pair.getPublic());
        signature.update(documentBytes);
        return signature.verify(signatureBytes);
    }   
}
