package com.bensira.javaj2ee.produit.vue;

import java.io.*;

import com.bensira.javaj2ee.produit.controleur.BaseDonnees;
import com.bensira.javaj2ee.produit.modeles.Produit;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet de saisie d'un nouveau produit avec validation des doublons.
 */
@WebServlet(name = "formulaireServlet", value = "/formulaire")
public class FormulaireServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        reponse.setContentType("text/html;charset=UTF-8");
        PrintWriter sortie = reponse.getWriter();

        // Récupération éventuelle d'un message d'erreur et des valeurs précédentes
        String messageErreur = (String) requete.getAttribute("erreur");
        String codeSaisi = requete.getParameter("code") != null ? requete.getParameter("code") : "";
        String designationSaisie = requete.getParameter("designation") != null ? requete.getParameter("designation") : "";
        String quantiteSaisie = requete.getParameter("quantite") != null ? requete.getParameter("quantite") : "";
        String prixSaisi = requete.getParameter("prix") != null ? requete.getParameter("prix") : "";

        sortie.println("<!DOCTYPE html>");
        sortie.println("<html>");
        sortie.println("<head><title>Nouveau Produit</title>");
        sortie.println("<style>");
        sortie.println("body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f8f9fa; margin: 40px; }");
        sortie.println(".form-container { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); max-width: 400px; margin: auto; }");
        sortie.println("h2 { margin-top: 0; color: #444; }");
        sortie.println(".alerte { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; border: 1px solid #f5c6cb; margin-bottom: 15px; font-size: 14px; }");
        sortie.println("label { display: block; margin: 10px 0 5px; font-weight: bold; }");
        sortie.println("input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }");
        sortie.println(".btn { background-color: #5c67f2; color: white; border: none; padding: 12px; width: 100%; border-radius: 4px; cursor: pointer; margin-top: 20px; font-weight: bold; }");
        sortie.println(".btn-retour { display: block; text-align: center; margin-top: 15px; color: #666; text-decoration: none; }");
        sortie.println("</style>");
        sortie.println("</head>");
        sortie.println("<body>");
        sortie.println("<div class='form-container'>");
        sortie.println("<h2>Nouveau Produit</h2>");

        // Affichage du message d'erreur s'il existe
        if (messageErreur != null) {
            sortie.println("<div class='alerte'>⚠️ " + messageErreur + "</div>");
        }

        sortie.println("<form action='formulaire' method='POST'>");
        sortie.println("<label>Code :</label><input type='text' name='code' value='" + codeSaisi + "' required>");
        sortie.println("<label>Désignation :</label><input type='text' name='designation' value='" + designationSaisie + "' required>");
        sortie.println("<label>Quantité :</label><input type='number' min='1' name='quantite' value='" + quantiteSaisie + "' required>");
        sortie.println("<label>Prix :</label><input type='number' min='1' step='0.01' name='prix' value='" + prixSaisi + "' required>");
        sortie.println("<button type='submit' class='btn'>Enregistrer</button>");
        sortie.println("</form>");
        sortie.println("<a href='dashboard' class='btn-retour'>Annuler et retourner au tableau de bord</a>");
        sortie.println("</div>");
        sortie.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest requete, HttpServletResponse reponse) throws IOException, jakarta.servlet.ServletException {
        String code = requete.getParameter("code");
        String designation = requete.getParameter("designation");
        String quantiteStr = requete.getParameter("quantite");
        String prixStr = requete.getParameter("prix");

        // 1. Vérification des doublons
        if (BaseDonnees.existeDejaParCode(code)) {
            requete.setAttribute("erreur", "Le code <b>" + code + "</b> est déjà utilisé. Veuillez en choisir un autre.");
            this.doGet(requete, reponse);
            return;
        }

        if (BaseDonnees.existeDejaParDesignation(designation)) {
            requete.setAttribute("erreur", "La désignation <b>" + designation + "</b> existe déjà. Veuillez la modifier.");
            this.doGet(requete, reponse);
            return;
        }

        // 2. Si pas de doublon, insertion
        try {
            int quantite = Integer.parseInt(quantiteStr);
            double prix = Double.parseDouble(prixStr);

            if (quantite < 1 || prix < 1) {
                requete.setAttribute("erreur", "La quantité et le prix doivent être au minimum de 1.");
                this.doGet(requete, reponse);
                return;
            }

            BaseDonnees.insererProduit(new Produit(code, designation, quantite, prix));
            reponse.sendRedirect("dashboard");
        } catch (NumberFormatException e) {
            requete.setAttribute("erreur", "Données numériques invalides.");
            this.doGet(requete, reponse);
        }
    }
}
