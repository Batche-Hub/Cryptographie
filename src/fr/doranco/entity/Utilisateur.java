/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.entity;


/**
 *
 * @author Boule
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private boolean connected;
//    private String password;
//    private CarteCredit carteCredit;
//    private byte[] cle;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String login) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", login=" + login + '}';
    }

}
