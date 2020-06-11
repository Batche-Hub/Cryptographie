/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.entity.dojo;

import fr.doranco.entity.CarteCredit;

/**
 *
 * @author Boule
 */
public class UtilisateurDOJO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private byte[] password;
    private CarteCreditDOJO carteCreditByte;
    private byte[] cle;

    public UtilisateurDOJO() {
    }

    public UtilisateurDOJO(String nom, String prenom, String email, String login, byte[] password, CarteCreditDOJO carteCreditByte, byte[] cle) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
        this.password = password;
        this.carteCreditByte = carteCreditByte;
        this.cle = cle;
    }

    public byte[] getCle() {
        return cle;
    }

    public void setCle(byte[] cle) {
        this.cle = cle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public CarteCreditDOJO getCarteCreditByte() {
        return carteCreditByte;
    }

    public void setCarteCreditByte(CarteCreditDOJO carteCreditByte) {
        this.carteCreditByte = carteCreditByte;
    }

    @Override
    public String toString() {
        return "UtilisateurByte{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", login=" + login + ", password=" + password + ", carteCreditByte=" + carteCreditByte + ", cle=" + cle + '}';
    }
    
}
