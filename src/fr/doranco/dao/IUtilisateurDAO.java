/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.dao;
import fr.doranco.entity.dojo.UtilisateurDOJO;
import fr.doranco.entity.Utilisateur;
import java.util.List;

/**
 *
 * @author Boule
 */
public interface IUtilisateurDAO {    
    public UtilisateurDOJO getUtilisateurById(Integer id) throws Exception;
    public UtilisateurDOJO addUtilisateur(UtilisateurDOJO utilisateurByte) throws Exception;
//    public void updateUtilisateur(Utilisateur utlisateur) throws Exception;
//    public void removeUtilisateur(Integer id) throws Exception;
//    public List<Utilisateur> getListeUtilisateur() throws Exception;
}
