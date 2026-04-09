package com.bensira.javaj2ee;

import connexion.connexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les interactions avec la base de données SQLite.
 */
public class BaseDonnees {

    static {
        initialiserBaseDeDonnees();
    }

    /**
     * Crée la table et insère les données de base si nécessaire.
     */
    public static void initialiserBaseDeDonnees() {
        // Ajout de UNIQUE sur designation selon la demande de validation des doublons
        String requeteCreation = "CREATE TABLE IF NOT EXISTS produits (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT UNIQUE NOT NULL," +
                "designation TEXT UNIQUE NOT NULL," +
                "quantite INTEGER NOT NULL," +
                "prix REAL NOT NULL" +
                ");";

        try (Connection con = connexion.obtenirConnexion();
             Statement instruction = con.createStatement()) {
            
            instruction.execute(requeteCreation);
            
            ResultSet rs = instruction.executeQuery("SELECT COUNT(*) FROM produits");
            if (rs.next() && rs.getInt(1) == 0) {
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P001', 'Ordinateur Portable', 10, 850.00)");
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P002', 'Souris sans fil', 50, 25.50)");
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P003', 'Clavier Azerty', 30, 45.00)");
                System.out.println("🌱 Données d'exemple insérées.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'initialisation : " + e.getMessage());
        }
    }

    /**
     * Vérifie si un code produit existe déjà dans la base.
     */
    public static boolean existeDejaParCode(String code) {
        String requete = "SELECT COUNT(*) FROM produits WHERE code = ?";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification du code : " + e.getMessage());
        }
        return false;
    }

    /**
     * Vérifie si une désignation produit existe déjà dans la base.
     */
    public static boolean existeDejaParDesignation(String designation) {
        String requete = "SELECT COUNT(*) FROM produits WHERE designation = ?";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setString(1, designation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de la désignation : " + e.getMessage());
        }
        return false;
    }

    public static void insererProduit(Produit produit) {
        String requeteInsertion = "INSERT INTO produits(code, designation, quantite, prix) VALUES(?,?,?,?)";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requeteInsertion)) {
            ps.setString(1, produit.obtenirCode());
            ps.setString(2, produit.obtenirDesignation());
            ps.setInt(3, produit.obtenirQuantite());
            ps.setDouble(4, produit.obtenirPrix());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'insertion : " + e.getMessage());
        }
    }

    // ... Reste des méthodes inchangé (lister, rechercher, modifier, supprimer)
    
    public static List<Produit> listerTousLesProduits() {
        List<Produit> liste = new ArrayList<>();
        String requeteSelection = "SELECT code, designation, quantite, prix FROM produits";
        try (Connection con = connexion.obtenirConnexion();
             Statement instruction = con.createStatement();
             ResultSet rs = instruction.executeQuery(requeteSelection)) {
            while (rs.next()) {
                liste.add(new Produit(rs.getString("code"), rs.getString("designation"), rs.getInt("quantite"), rs.getDouble("prix")));
            }
        } catch (SQLException e) { System.err.println("❌ Erreur lors de la liste."); }
        return liste;
    }

    public static Produit rechercherParCode(String code) {
        String requete = "SELECT code, designation, quantite, prix FROM produits WHERE code = ?";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produit(rs.getString("code"), rs.getString("designation"), rs.getInt("quantite"), rs.getDouble("prix"));
            }
        } catch (SQLException e) { System.err.println("❌ Erreur lors de la recherche."); }
        return null;
    }

    public static void modifierProduit(Produit p) {
        String requete = "UPDATE produits SET designation = ?, quantite = ?, prix = ? WHERE code = ?";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setString(1, p.obtenirDesignation());
            ps.setInt(2, p.obtenirQuantite());
            ps.setDouble(3, p.obtenirPrix());
            ps.setString(4, p.obtenirCode());
            ps.executeUpdate();
        } catch (SQLException e) { System.err.println("❌ Erreur lors de la modification."); }
    }

    public static void supprimerProduitParCode(String code) {
        String requete = "DELETE FROM produits WHERE code = ?";
        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requete)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) { System.err.println("❌ Erreur lors de la suppression."); }
    }
}
