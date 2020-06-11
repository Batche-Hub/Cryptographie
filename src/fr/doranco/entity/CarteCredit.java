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
public class CarteCredit {
    private Integer id;
    private String numeroCarte;
    private String dateExpiration;
    private String cryptogramme;

    public CarteCredit() {
    }

    public CarteCredit(String numeroCarte, String dateExpiration, String cryptogramme) {
        this.numeroCarte = numeroCarte;
        this.dateExpiration = dateExpiration;
        this.cryptogramme = cryptogramme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getCryptogramme() {
        return cryptogramme;
    }

    public void setCryptogramme(String cryptogramme) {
        this.cryptogramme = cryptogramme;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    
    
    
    
    
}
