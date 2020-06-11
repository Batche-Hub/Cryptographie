/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.impl;

import fr.doranco.dao.CarteCreditDAO;
import fr.doranco.dao.UtilisateurDAO;
import fr.doranco.entity.dojo.CarteCreditDOJO;
import fr.doranco.entity.dojo.UtilisateurDOJO;
import fr.doranco.entity.CarteCredit;
import fr.doranco.entity.Utilisateur;
import fr.doranco.cryptage.des.CryptageDES;
import fr.doranco.utils.Utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Boule
 */
public class UtilisateurImpl {

    public UtilisateurImpl() {
    }

    
    public UtilisateurDOJO addUtilisateur(Utilisateur utilisateur, String password) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, Exception{
        SecretKey cle = CryptageDES.generateKey();
        UtilisateurDAO dao = new UtilisateurDAO();
        UtilisateurDOJO utilisateurDOJO = new UtilisateurDOJO(); 
        utilisateurDOJO.setNom(utilisateur.getNom());
        utilisateurDOJO.setPrenom(utilisateur.getPrenom());
        utilisateurDOJO.setEmail(utilisateur.getEmail());
        utilisateurDOJO.setLogin(utilisateur.getLogin());
        utilisateurDOJO.setPassword(CryptageDES.encrypter(password, cle));
        utilisateurDOJO.setCle(Utils.convertToBytes(cle));        
        UtilisateurDOJO utilisateurAjoute = dao.addUtilisateur(utilisateurDOJO);        
        return utilisateurAjoute;
    }
    
    public void addCarteCredit(CarteCredit carteCredit, Integer idUtilisateur) throws Exception{
        UtilisateurDAO utDAO = new UtilisateurDAO();
        UtilisateurDOJO utilisateurDojo = utDAO.getUtilisateurById(idUtilisateur);
        SecretKey cle = (SecretKey) Utils.convertFromBytes(utilisateurDojo.getCle());
        CarteCreditDAO carteCreditDAO = new CarteCreditDAO();
        CarteCreditDOJO carteDOJO = new CarteCreditDOJO();
        carteDOJO.setNumeroCarte(CryptageDES.encrypter(carteCredit.getNumeroCarte(), cle));
        carteDOJO.setDateExpiration(carteCredit.getDateExpiration());
        carteDOJO.setCryptogramme(CryptageDES.encrypter(carteCredit.getCryptogramme(), cle));
        carteCreditDAO.addCarteToUtilisateur(carteDOJO, idUtilisateur);
    }

    public Utilisateur connexion(String login, String password){
    return null;
    }

//    public static final Utilisateur convertUtilisateurByteToUtilisateurDecrypted(UtilisateurDOJO utilisateurByte) throws Exception {
//
//        SecretKey cle = (SecretKey) Utils.convertFromBytes(utilisateurByte.getCle());
//        CarteCredit cartecredit = new CarteCredit();
//        Utilisateur utilisateur = new Utilisateur();
//        utilisateur.setId(utilisateurByte.getId());
//        utilisateur.setNom(utilisateurByte.getNom());
//        utilisateur.setPrenom(utilisateurByte.getPrenom());
//        utilisateur.setLogin(utilisateurByte.getLogin());
//        utilisateur.setEmail(utilisateurByte.getEmail());
//        utilisateur.setPassword(CryptageDES.decrypter(utilisateurByte.getPassword(), cle));
//        utilisateur.setCarteCredit(cartecredit);
//        Long numeroCarte = Long.parseLong(CryptageDES.decrypter(utilisateurByte.getCarteCreditByte().getNumeroCarte(), cle));
//        utilisateur.getCarteCredit().setId(utilisateurByte.getCarteCreditByte().getId());
//        utilisateur.getCarteCredit().setNumeroCarte(numeroCarte);
//        Integer crypto = Integer.parseInt(CryptageDES.decrypter(utilisateurByte.getCarteCreditByte().getCryptogramme(), cle));
//        utilisateur.getCarteCredit().setCryptogramme(crypto);
//        utilisateur.getCarteCredit().setDateExpiration(utilisateurByte.getCarteCreditByte().getDateExpiration());
//
//        return utilisateur;
//    }
}
