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
public class CarteCreditDAO implements ICarteCreditDAO {

    //Constructeur
    public CarteCreditDAO() {
    }
    
    //Méthodes de l'interface à redéfinir
    @Override
    public UtilisateurDOJO getUtilisateurById(int id) throws Exception{
        
        UtilisateurDOJO utilisateur = null;
        CarteCreditDOJO carteCredit = null;
        Connection connexion = SecuriteDataSource.getInstance().getConnection();
   
        PreparedStatement ps = null;
        try {
            utilisateur = new UtilisateurDOJO();
            carteCredit = new CarteCreditDOJO();
            String requete = "SELECT * FROM utilisateur WHERE id = ?";
            ps = connexion.prepareStatement(requete);
            ps.setInt(1, id);
            
            
            ResultSet rs = ps.executeQuery();
            

            if (rs.next()) { 
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getBytes("password"));
                utilisateur.setCle(rs.getBytes("cle"));
                carteCredit.setId(rs.getInt("carte_credit_id"));
            }
        } catch (Exception e) {
            System.out.println("Une erreur est survenue : " + e);
            return null;
        }
            
            try{
            
            String requete2 = "SELECT * FROM carte_credit WHERE id = ?";
            ps = connexion.prepareStatement(requete2);
            ps.setInt(1, carteCredit.getId());
            
            
            ResultSet rs2 = ps.executeQuery();
            
            if(rs2.next()){
                carteCredit.setNumeroCarte(rs2.getBytes("numero"));
                carteCredit.setDateExpiration(rs2.getString("date_validite"));
                carteCredit.setCryptogramme(rs2.getBytes("cryptogramme"));
                utilisateur.setCarteCreditByte(carteCredit);
            }
            
            
            
        } catch (Exception e) {
            System.out.println("Une erreur est survenue : " + e);
            return null;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ex) {
                    System.err.println("Une erreur Sql est survenue : " + ex);
                    
                }

            }

        }
        return utilisateur;
    }

    @Override
    public void addCarteToUtilisateur(CarteCreditDOJO carteCredit, Integer id) throws Exception{
        UtilisateurDOJO utilisateurDojo = null;
        CarteCreditDOJO carteCreditAjoutee = null;
        Connection connexion = SecuriteDataSource.getInstance().getConnection();
        
        PreparedStatement ps = null;
        
        try{
            String requete = "INSERT INTO carte_credit(numero, date_validite, cryptogramme, utilisateur_id) VALUE(?,?,?,?)";
            ps = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            ps.setBytes(1, carteCredit.getNumeroCarte());
            ps.setString(2, carteCredit.getDateExpiration());
            ps.setBytes(3, carteCredit.getCryptogramme());
            ps.setInt(4, id);
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            carteCreditAjoutee = carteCredit;
            if (resultSet.next()) {
                utilisateurDojo = new UtilisateurDOJO();
                Integer idCarte = resultSet.getInt(1);
                carteCreditAjoutee.setId(idCarte);
                utilisateurDojo.setCarteCreditByte(carteCreditAjoutee);
                
            }
        }catch(Exception ex){
            System.err.println("Une erreur est survenue lors de la requête concenrnant la carte de crédit : "+ex);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ex) {
                   System.err.println("Une erreur SQL est survenue : "+ex);
                }
            }

        }
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
