package com.bensira.javaj2ee.employe.modeles;

/**
 * Représente un employé dans le système.
 */
public class Employe {
    private String matricule;
    private String nom;
    private double salaire;

    public Employe() {}

    public Employe(String matricule, String nom, double salaire) {
        this.matricule = matricule;
        this.nom = nom;
        this.salaire = salaire;
    }

    // Getters et Setters en Français
    public String obtenirMatricule() { return matricule; }
    public void definirMatricule(String matricule) { this.matricule = matricule; }

    public String obtenirNom() { return nom; }
    public void definirNom(String nom) { this.nom = nom; }

    public double obtenirSalaire() { return salaire; }
    public void definirSalaire(double salaire) { this.salaire = salaire; }
}
