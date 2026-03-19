package com.bensira.javaj2ee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les interactions avec la base de données SQLite.
 */
public class BaseDonnees {
    private static final String URL_SQLITE = "jdbc:sqlite:produits.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initialiserBaseDeDonnees();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initialiserBaseDeDonnees() {
        String requeteCreation = "CREATE TABLE IF NOT EXISTS produits (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT UNIQUE NOT NULL," +
                "designation TEXT NOT NULL," +
                "quantite INTEGER NOT NULL," +
                "prix REAL NOT NULL" +
                ");";

        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             Statement instruction = connexion.createStatement()) {
            instruction.execute(requeteCreation);
            
            // Grainage de données (Seeding) si la table est vide
            ResultSet rs = instruction.executeQuery("SELECT COUNT(*) FROM produits");
            if (rs.next() && rs.getInt(1) == 0) {
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P001', 'Ordinateur Portable', 10, 850.00)");
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P002', 'Souris sans fil', 50, 25.50)");
                instruction.execute("INSERT INTO produits(code, designation, quantite, prix) VALUES('P003', 'Clavier Azerty', 30, 45.00)");
            }
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de l'initialisation.");
        }
    }

    public static void insererProduit(Produit produit) {
        String requeteInsertion = "INSERT INTO produits(code, designation, quantite, prix) VALUES(?,?,?,?)";
        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             PreparedStatement instruction = connexion.prepareStatement(requeteInsertion)) {
            instruction.setString(1, produit.obtenirCode());
            instruction.setString(2, produit.obtenirDesignation());
            instruction.setInt(3, produit.obtenirQuantite());
            instruction.setDouble(4, produit.obtenirPrix());
            instruction.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de l'insertion.");
        }
    }

    public static List<Produit> listerTousLesProduits() {
        List<Produit> liste = new ArrayList<>();
        String requeteSelection = "SELECT code, designation, quantite, prix FROM produits";
        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             Statement instruction = connexion.createStatement();
             ResultSet resultats = instruction.executeQuery(requeteSelection)) {
            while (resultats.next()) {
                liste.add(new Produit(
                    resultats.getString("code"),
                    resultats.getString("designation"),
                    resultats.getInt("quantite"),
                    resultats.getDouble("prix")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de la liste.");
        }
        return liste;
    }

    public static Produit rechercherParCode(String code) {
        String requete = "SELECT code, designation, quantite, prix FROM produits WHERE code = ?";
        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             PreparedStatement ps = connexion.prepareStatement(requete)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produit(
                    rs.getString("code"),
                    rs.getString("designation"),
                    rs.getInt("quantite"),
                    rs.getDouble("prix")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de la recherche.");
        }
        return null;
    }

    public static void modifierProduit(Produit p) {
        String requete = "UPDATE produits SET designation = ?, quantite = ?, prix = ? WHERE code = ?";
        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             PreparedStatement ps = connexion.prepareStatement(requete)) {
            ps.setString(1, p.obtenirDesignation());
            ps.setInt(2, p.obtenirQuantite());
            ps.setDouble(3, p.obtenirPrix());
            ps.setString(4, p.obtenirCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de la modification.");
        }
    }

    public static void supprimerProduitParCode(String code) {
        String requete = "DELETE FROM produits WHERE code = ?";
        try (Connection connexion = DriverManager.getConnection(URL_SQLITE);
             PreparedStatement ps = connexion.prepareStatement(requete)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur technique lors de la suppression.");
        }
    }
}
