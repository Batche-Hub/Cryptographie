/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.dao;



import fr.doranco.connexion.SecuriteDataSource;
import fr.doranco.entity.dojo.CarteCreditDOJO;
import fr.doranco.entity.dojo.UtilisateurDOJO;
import fr.doranco.entity.CarteCredit;
import fr.doranco.entity.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.text.Caret;


/**
 *
 * @author Boule
 */
public class UtilisateurDAO implements IUtilisateurDAO {

    //Constructeur
    public UtilisateurDAO() {
    }
    

    @Override
    public UtilisateurDOJO getUtilisateurById(Integer id) throws Exception{
        
        UtilisateurDOJO utilisateurDOJO = null;
        CarteCreditDOJO carteCredit = null;
        Connection connexion = SecuriteDataSource.getInstance().getConnection();
   
        PreparedStatement ps = null;
        try {
            utilisateurDOJO = new UtilisateurDOJO();
            
            String requete = "SELECT * FROM utilisateur WHERE id = ?";
            ps = connexion.prepareStatement(requete);
            ps.setInt(1, id);
                        
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) { 
                utilisateurDOJO.setId(rs.getInt("id"));
                utilisateurDOJO.setNom(rs.getString("nom"));
                utilisateurDOJO.setPrenom(rs.getString("prenom"));
                utilisateurDOJO.setEmail(rs.getString("email"));
                utilisateurDOJO.setLogin(rs.getString("login"));
                utilisateurDOJO.setPassword(rs.getBytes("password"));
                utilisateurDOJO.setCle(rs.getBytes("cle"));
                utilisateurDOJO.setCarteCreditByte(carteCredit);
            }
        } catch (Exception e) {
            System.out.println("Une erreur est survenue : " + e);
            return null;
        }       
//        try {
//            String requete = "SELECT * FROM carte_credit WHERE utilisateur_id = ?";
//            ps = connexion.prepareStatement(requete);
//            ps.setInt(1, id);
//            
//            ResultSet rs = ps.executeQuery();
// 
//        if (rs.next()) { 
//                utilisateurDOJO.getCarteCreditByte().setId(rs.getInt("id"));
//                utilisateurDOJO.getCarteCreditByte().setNumeroCarte(rs.getBytes("numero"));
//                utilisateurDOJO.getCarteCreditByte().setDateExpiration(rs.getString("date_expiration"));
//                utilisateurDOJO.getCarteCreditByte().setCryptogramme(rs.getBytes("cryptogramme"));
//            }
//        } catch (Exception e) {
//            System.out.println("Une erreur est survenue : " + e);
//            return null;
//        }
        finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ex) {
                    System.err.println("Une erreur Sql est survenue : " + ex);                   
                }
            }
        }
        return utilisateurDOJO;
    }

//    public UtilisateurDOJO getUtilisateurByLogin(String login) throws Exception{
//        
//        UtilisateurDOJO utilisateurDOJO = null;
//        CarteCreditDOJO carteCredit = null;
//        Connection connexion = SecuriteDataSource.getInstance().getConnection();
//   
//        PreparedStatement ps = null;
//        try {
//            utilisateurDOJO = new UtilisateurDOJO();
//            carteCredit = new CarteCreditDOJO();
//            String requete = "SELECT * FROM utilisateur WHERE id = ?";
//            ps = connexion.prepareStatement(requete);
//            ps.setInt(1, id);
//                        
//            ResultSet rs = ps.executeQuery();
//            
//            if (rs.next()) { 
//                utilisateurDOJO.setId(rs.getInt("id"));
//                utilisateurDOJO.setNom(rs.getString("nom"));
//                utilisateurDOJO.setPrenom(rs.getString("prenom"));
//                utilisateurDOJO.setEmail(rs.getString("email"));
//                utilisateurDOJO.setLogin(rs.getString("login"));
//                utilisateurDOJO.setPassword(rs.getBytes("password"));
//                utilisateurDOJO.setCle(rs.getBytes("cle"));
//            }
//        } catch (Exception e) {
//            System.out.println("Une erreur est survenue : " + e);
//            return null;
//        }finally {
//            if (connexion != null) {
//                try {
//                    connexion.close();
//                } catch (SQLException ex) {
//                    System.err.println("Une erreur Sql est survenue : " + ex);                   
//                }
//            }
//        }
//        return utilisateurDOJO;
//    }
    
    
    
    
    @Override
    public UtilisateurDOJO addUtilisateur(UtilisateurDOJO utilisateurDOJO) throws Exception{
        UtilisateurDOJO utilisateurAjoute = null;
        Connection connexion = SecuriteDataSource.getInstance().getConnection();
        
        PreparedStatement ps = null;        
        try {
            String requete = "INSERT INTO utilisateur(nom, prenom, email, login, password, cle) VALUE(?,?,?,?,?,?)";
            ps = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, utilisateurDOJO.getNom());
            ps.setString(2, utilisateurDOJO.getPrenom());
            ps.setString(3, utilisateurDOJO.getEmail());
            ps.setString(4, utilisateurDOJO.getLogin());
            ps.setBytes(5, utilisateurDOJO.getPassword());
            ps.setBytes(6, utilisateurDOJO.getCle());
            ps.executeUpdate();
            utilisateurAjoute = utilisateurDOJO;
            ResultSet resultSet = ps.getGeneratedKeys();

            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                utilisateurAjoute.setId(id);
            }

        } catch (Exception e) {
            System.err.println("Une erreur de connexion est survenue dans la requête concernant l'utilisateur." + e);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ex) {
                   System.err.println("Une erreur SQL est survenue : "+ex);
                }
            }
        }
        return utilisateurAjoute;
    }

//    @Override
//    public List<Employe> getListeEmploye() throws Exception{
//        
//        
//        List<Employe> listeEmployes = new ArrayList<>();
//       
//        Connection connexion = SecuriteDataSource.getInstance().getConnection();
//       
//        String requete = "SELECT * FROM employe";
//        PreparedStatement ps = null;
//        try {
//            ps = connexion.prepareStatement(requete);
//            ResultSet rs = ps.executeQuery();
//            
//           
//            while (rs.next()) {
//               
//                Employe employe = new Employe();
//               
//                employe.setId(rs.getInt("id"));
//                employe.setNom(rs.getString("nom"));
//                employe.setPrenom(rs.getString("prenom"));
//                employe.setPosteOccupe(rs.getString("poste_occupe"));
//                listeEmployes.add(employe);
//                
//            }
//           
//        } catch (SQLException e) {
//            System.err.println("Une erreur SQL est survenue : "+e);
//        } finally {
//            if (connexion != null) {
//                try {
//                    connexion.close();
//                } catch (SQLException ex) {
//                    System.err.println("Erreur de connexion SQL : " + ex);
//                }
//            }
//        }
//        
//        
//        return listeEmployes;
//    }
//
//    @Override
//    public void updateEmploye(Employe employe) throws Exception {
//       Connection connexion = SecuriteDataSource.getInstance().getConnection();
//        String requete = "UPDATE employe SET nom = ?, prenom=?, poste_occupe=? WHERE id=?";
//        PreparedStatement ps = null;
//        try {
//            ps = connexion.prepareStatement(requete);
//            //Je spécifie à quoi correspond dans Java les points d'interrogation de ma requête.
//            ps.setString(1, employe.getNom());
//            ps.setString(2, employe.getPrenom());
//            ps.setString(3, employe.getPosteOccupe());
//            ps.setInt(4, employe.getId());
//            // Je mets à jour
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.err.println("Une erreur de connexion est survenue." + e);
//        } finally {
//            if (connexion != null) {
//                try {
//                    connexion.close();
//                } catch (SQLException ex) {
//                System.err.println("Une erreur SQL est survenue : "+ex);
//                }
//            }
//        }
//        
//    }
//
//    @Override
//    public void removeEmploye(Integer id) throws Exception {
//        
//        Connection connexion = SecuriteDataSource.getInstance().getConnection();
//
//        String requete = "DELETE FROM employe WHERE id = " + id;
//        
//            try {
//                PreparedStatement ps = connexion.prepareStatement(requete);
//                ps.executeUpdate();
//            } catch (Exception e) {
//                System.err.println("Une erreur de connexion est survenue." + e);
//            } finally {
//                if (connexion != null) {
//                    try {
//                        connexion.close();
//                    } catch (SQLException ex) {
//                        System.err.println("Erreur de connexion SQL : " + ex);
//                    }
//                }
//            }
//    }

    

}
