package com.bensira.javaj2ee.produit.vue;

import java.io.*;
import java.util.List;

import com.bensira.javaj2ee.produit.controleur.BaseDonnees;
import com.bensira.javaj2ee.produit.modeles.Produit;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet principal faisant office de Tableau de Bord.
 */
@WebServlet(name = "tableauBordServlet", value = "/dashboard")
public class TableauBordServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        reponse.setContentType("text/html;charset=UTF-8");
        PrintWriter sortie = reponse.getWriter();

        List<Produit> produits = BaseDonnees.listerTousLesProduits();

        sortie.println("<!DOCTYPE html>");
        sortie.println("<html>");
        sortie.println("<head><title>Tableau de Bord - Produits</title>");
        sortie.println("<style>");
        sortie.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 40px; background-color: #f8f9fa; color: #333; }");
        sortie.println(".container { max-width: 900px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }");
        sortie.println("h1 { color: #444; border-bottom: 2px solid #5c67f2; padding-bottom: 10px; }");
        sortie.println(".btn { display: inline-block; padding: 8px 15px; border-radius: 4px; text-decoration: none; font-weight: bold; font-size: 14px; cursor: pointer; border: none; }");
        sortie.println(".btn-primaire { background-color: #5c67f2; color: white; margin-bottom: 20px; }");
        sortie.println(".btn-modif { background-color: #ffc107; color: #333; margin-right: 5px; }");
        sortie.println(".btn-suppr { background-color: #dc3545; color: white; }");
        sortie.println("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        sortie.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #eee; }");
        sortie.println("th { background-color: #f1f3f5; font-weight: 600; }");
        sortie.println("tr:hover { background-color: #fcfcfc; }");
        sortie.println("</style>");
        sortie.println("</head>");
        sortie.println("<body>");
        sortie.println("<div class='container'>");
        sortie.println("<h1>Gestion des Produits</h1>");
        
        sortie.println("<a href='formulaire' class='btn btn-primaire'>+ Nouveau Produit</a>");

        if (produits.isEmpty()) {
            sortie.println("<p>Aucun produit enregistré.</p>");
        } else {
            sortie.println("<table>");
            sortie.println("<tr><th>Code</th><th>Désignation</th><th>Qté</th><th>Prix</th><th>Actions</th></tr>");
            for (Produit p : produits) {
                sortie.println("<tr>");
                sortie.println("<td>" + p.obtenirCode() + "</td>");
                sortie.println("<td>" + p.obtenirDesignation() + "</td>");
                sortie.println("<td>" + p.obtenirQuantite() + "</td>");
                sortie.println("<td>" + p.obtenirPrix() + " DH</td>");
                sortie.println("<td>");
                sortie.println("<a href='modifier?code=" + p.obtenirCode() + "' class='btn btn-modif'>Modifier</a>");
                sortie.println("<a href='supprimer?code=" + p.obtenirCode() + "' class='btn btn-suppr' onclick='return confirm(\"Confirmer la suppression ?\")'>Supprimer</a>");
                sortie.println("</td>");
                sortie.println("</tr>");
            }
            sortie.println("</table>");
        }
        sortie.println("</div>");
        sortie.println("</body></html>");
    }
}
