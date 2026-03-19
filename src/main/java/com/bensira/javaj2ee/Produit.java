package com.bensira.javaj2ee;

/**
 * Classe représentant un Produit dans le système.
 * Tous les identifiants sont en Français conformément aux règles du projet.
 */
public class Produit {
    private String code;
    private String designation;
    private int quantite;
    private double prix;

    public Produit() {}

    public Produit(String code, String designation, int quantite, double prix) {
        this.code = code;
        this.designation = designation;
        this.quantite = quantite;
        this.prix = prix;
    }

    public String obtenirCode() {
        return code;
    }

    public void definirCode(String code) {
        this.code = code;
    }

    public String obtenirDesignation() {
        return designation;
    }

    public void definirDesignation(String designation) {
        this.designation = designation;
    }

    public int obtenirQuantite() {
        return quantite;
    }

    public void definirQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double obtenirPrix() {
        return prix;
    }

    public void definirPrix(double prix) {
        this.prix = prix;
    }
}
