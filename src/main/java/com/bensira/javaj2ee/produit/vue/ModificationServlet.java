package com.bensira.javaj2ee.produit.vue;

import java.io.*;

import com.bensira.javaj2ee.produit.controleur.BaseDonnees;
import com.bensira.javaj2ee.produit.modeles.Produit;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet gérant la modification d'un produit existant.
 */
@WebServlet(name = "modificationServlet", value = "/modifier")
public class ModificationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        String code = requete.getParameter("code");
        Produit p = BaseDonnees.rechercherParCode(code);

        reponse.setContentType("text/html;charset=UTF-8");
        PrintWriter sortie = reponse.getWriter();

        if (p == null) {
            reponse.sendRedirect("dashboard");
            return;
        }

        sortie.println("<html><head><title>Modifier Produit</title>");
        sortie.println("<style>");
        sortie.println("body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 40px; }");
        sortie.println(".form-container { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); max-width: 400px; margin: auto; }");
        sortie.println("h2 { margin-top: 0; color: #444; border-bottom: 2px solid #ffc107; padding-bottom: 5px; }");
        sortie.println("label { display: block; margin: 10px 0 5px; font-weight: bold; }");
        sortie.println("input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; background: #f9f9f9; }");
        sortie.println("input[readonly] { background: #eee; cursor: not-allowed; }");
        sortie.println(".btn { background-color: #ffc107; color: #333; border: none; padding: 12px; width: 100%; border-radius: 4px; cursor: pointer; margin-top: 20px; font-weight: bold; }");
        sortie.println(".btn-retour { display: block; text-align: center; margin-top: 15px; color: #666; text-decoration: none; }");
        sortie.println("</style></head><body>");
        
        sortie.println("<div class='form-container'>");
        sortie.println("<h2>Modifier le Produit</h2>");
        sortie.println("<form action='modifier' method='POST'>");
        sortie.println("<label>Code (non modifiable) :</label><input type='text' name='code' value='" + p.obtenirCode() + "' readonly>");
        sortie.println("<label>Désignation :</label><input type='text' name='designation' value='" + p.obtenirDesignation() + "' required>");
        sortie.println("<label>Quantité :</label><input type='number' name='quantite' value='" + p.obtenirQuantite() + "' required>");
        sortie.println("<label>Prix :</label><input type='number' step='0.01' name='prix' value='" + p.obtenirPrix() + "' required>");
        sortie.println("<button type='submit' class='btn'>Mettre à jour</button>");
        sortie.println("</form>");
        sortie.println("<a href='dashboard' class='btn-retour'>Annuler</a>");
        sortie.println("</div>");
        sortie.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        String code = requete.getParameter("code");
        String designation = requete.getParameter("designation");
        int quantite = Integer.parseInt(requete.getParameter("quantite"));
        double prix = Double.parseDouble(requete.getParameter("prix"));

        BaseDonnees.modifierProduit(new Produit(code, designation, quantite, prix));
        reponse.sendRedirect("dashboard");
    }
}
