package com.bensira.javaj2ee;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet de saisie d'un nouveau produit.
 */
@WebServlet(name = "formulaireServlet", value = "/formulaire")
public class FormulaireServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        reponse.setContentType("text/html;charset=UTF-8");
        PrintWriter sortie = reponse.getWriter();

        sortie.println("<!DOCTYPE html>");
        sortie.println("<html>");
        sortie.println("<head><title>Nouveau Produit</title>");
        sortie.println("<style>");
        sortie.println("body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 40px; }");
        sortie.println(".form-container { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); max-width: 400px; margin: auto; }");
        sortie.println("h2 { margin-top: 0; color: #444; }");
        sortie.println("label { display: block; margin: 10px 0 5px; font-weight: bold; }");
        sortie.println("input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }");
        sortie.println(".btn { background-color: #5c67f2; color: white; border: none; padding: 12px; width: 100%; border-radius: 4px; cursor: pointer; margin-top: 20px; font-weight: bold; }");
        sortie.println(".btn-retour { display: block; text-align: center; margin-top: 15px; color: #666; text-decoration: none; }");
        sortie.println("</style>");
        sortie.println("</head>");
        sortie.println("<body>");
        sortie.println("<div class='form-container'>");
        sortie.println("<h2>Nouveau Produit</h2>");
        sortie.println("<form action='formulaire' method='POST'>");
        sortie.println("<label>Code :</label><input type='text' name='code' required>");
        sortie.println("<label>Désignation :</label><input type='text' name='designation' required>");
        sortie.println("<label>Quantité :</label><input type='number' name='quantite' required>");
        sortie.println("<label>Prix :</label><input type='number' step='0.1' name='prix' required>");
        sortie.println("<button type='submit' class='btn'>Enregistrer</button>");
        sortie.println("</form>");
        sortie.println("<a href='dashboard' class='btn-retour'>Annuler et retourner au tableau de bord</a>");
        sortie.println("</div>");
        sortie.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        String code = requete.getParameter("code");
        String designation = requete.getParameter("designation");
        int quantite = Integer.parseInt(requete.getParameter("quantite"));
        double prix = Double.parseDouble(requete.getParameter("prix"));

        BaseDonnees.insererProduit(new Produit(code, designation, quantite, prix));
        
        // Redirection directe vers le tableau de bord
        reponse.sendRedirect("dashboard");
    }
}
