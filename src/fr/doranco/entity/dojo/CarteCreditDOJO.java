/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.entity.dojo;

/**
 *
 * @author Boule
 */
public class CarteCreditDOJO {
    private int id;
    private byte[] numeroCarte;
    private String dateExpiration;
    private byte[] cryptogramme;

    public CarteCreditDOJO() {
    }

    public CarteCreditDOJO(byte[] numeroCarte, String dateExpiration, byte[] cryptogramme) {
        this.numeroCarte = numeroCarte;
        this.dateExpiration = dateExpiration;
        this.cryptogramme = cryptogramme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public byte[] getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(byte[] numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public byte[] getCryptogramme() {
        return cryptogramme;
    }

    public void setCryptogramme(byte[] cryptogramme) {
        this.cryptogramme = cryptogramme;
    }

    @Override
    public String toString() {
        return "CarteCreditByte{" + "id=" + id + ", numeroCarte=" + numeroCarte + ", dateExpiration=" + dateExpiration + ", cryptogramme=" + cryptogramme + '}';
    }
   
}
