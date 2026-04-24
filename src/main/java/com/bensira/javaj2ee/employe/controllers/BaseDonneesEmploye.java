package com.bensira.javaj2ee.employe.controllers;

import java.sql.*;
import java.util.List;

import com.bensira.javaj2ee.produit.connexion.connexion;
import com.bensira.javaj2ee.employe.modeles.Employe;

/**
 * Gère les interactions avec la base de données pour les employés.
 */
public class BaseDonneesEmploye {

    static {
        initialiserTableEmployes();
    }

    /**
     * Crée la table employes si elle n'existe pas.
     */
    public static void initialiserTableEmployes() {
        String requeteCreation = "CREATE TABLE IF NOT EXISTS employes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricule TEXT UNIQUE NOT NULL," +
                "nom TEXT NOT NULL," +
                "salaire REAL NOT NULL" +
                ");";

        try (Connection con = connexion.obtenirConnexion();
             Statement instruction = con.createStatement()) {

            instruction.execute(requeteCreation);
            System.out.println("✅ Table employes créée ou déjà existante.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la création de la table employes : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde une liste d'employés dans la base de données.
     */
    public static void sauvegarderEmployes(List<Employe> employes) {
        String requeteInsertion = "INSERT OR REPLACE INTO employes(matricule, nom, salaire) VALUES(?,?,?)";

        try (Connection con = connexion.obtenirConnexion();
             PreparedStatement ps = con.prepareStatement(requeteInsertion)) {

            for (Employe emp : employes) {
                ps.setString(1, emp.obtenirMatricule());
                ps.setString(2, emp.obtenirNom());
                ps.setDouble(3, emp.obtenirSalaire());
                ps.executeUpdate();
            }
            System.out.println("✅ " + employes.size() + " employé(s) sauvegardé(s) dans la base de données.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la sauvegarde des employés : " + e.getMessage());
        }
    }
}