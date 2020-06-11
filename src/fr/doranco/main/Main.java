/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.main;

import fr.doranco.cryptage.blowfish.CryptageBLOWFISH;
import fr.doranco.cryptage.pbe.CryptagePBE;
import fr.doranco.dao.UtilisateurDAO;
import fr.doranco.entity.dojo.UtilisateurDOJO;
import fr.doranco.entity.CarteCredit;
import fr.doranco.entity.Utilisateur;
import fr.doranco.impl.UtilisateurImpl;
import fr.doranco.utils.Utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.SecretKey;


/**
 *
 * @author Boule
 */
public class Main {

    public static void main(String[] args) {

        try {
            CarteCredit carteCredit = new CarteCredit("2284552111255541", "2020", "622");
            Utilisateur utilisateur = new Utilisateur("Cherif", "BADAD", "gmail@gmail", "R0xx0r");
            UtilisateurImpl utImpl = new UtilisateurImpl();
            UtilisateurDAO utDAO = new UtilisateurDAO();
            
            
            UtilisateurDOJO utDOJO = utImpl.addUtilisateur(utilisateur, "123456");

            System.err.println(utDAO.getUtilisateurById(utDOJO.getId()));
            utImpl.addCarteCredit(carteCredit, 42);
            
            utDAO.getUtilisateurById(42);
            
            String message = "Hi people";
            
            SecretKey cle = CryptageBLOWFISH.generateKey();
            byte[] messageCrypte = CryptageBLOWFISH.encrypter(message, cle);
            System.err.println(messageCrypte);
            System.err.println(CryptageBLOWFISH.decrypter(messageCrypte, cle));
           
            SecretKey clePBE = CryptagePBE.generatePBEKey("coucou");
            String messageA = "Hi people";
            
            System.err.println("Original message : "+ messageA+" //Message in bytes array : "+Arrays.toString(Utils.convertToBytes(messageA)));
            
            byte[] messageCrypte2 = CryptagePBE.encrypter(messageA, clePBE);
            
            System.err.println("Crypted message : "+Arrays.toString(messageCrypte2));
            
            String messageDecrypte = CryptagePBE.decrypter(messageCrypte2, clePBE, "UTF8");
            
            System.err.println("Decrypted message : "+messageDecrypte);
            
//            SecretKey clePBE = CryptagePBE.generateKey("oulahal");
//            String messageA = ("coucou");
//            byte[] a = CryptagePBE.encrypter(messageA, clePBE);
//            System.err.println(a);
//            
//            
//            System.err.println(b);
          
        } catch (Exception ex) {
            System.err.println("Une erreur est survenue dans le main : " + ex);
        }
    }

    public Main() {
    }
}
