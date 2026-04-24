package com.bensira.javaj2ee.produit.vue;

import java.io.*;

import com.bensira.javaj2ee.produit.controleur.BaseDonnees;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet gérant la suppression d'un produit.
 */
@WebServlet(name = "suppressionServlet", value = "/supprimer")
public class SuppressionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws IOException {
        String code = requete.getParameter("code");
        
        if (code != null) {
            BaseDonnees.supprimerProduitParCode(code);
        }

        reponse.setContentType("text/html;charset=UTF-8");
        PrintWriter sortie = reponse.getWriter();

        sortie.println("<html><head><title>Suppression Effectuée</title>");
        sortie.println("<style>");
        sortie.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f8f9fa; }");
        sortie.println(".message-container { text-align: center; border: 1px solid #ddd; padding: 40px; border-radius: 8px; background-color: #fff; box-shadow: 0 4px 6px rgba(0,0,0,0.05); }");
        sortie.println("h2 { color: #dc3545; }");
        sortie.println("a { text-decoration: none; color: #5c67f2; font-weight: bold; }");
        sortie.println("</style></head><body>");
        
        sortie.println("<div class='message-container'>");
        sortie.println("<h2>Le produit a bien été supprimé</h2>");
        sortie.println("<p>Les modifications ont été enregistrées dans la base de données.</p>");
        sortie.println("<a href='dashboard'>← Retourner au tableau de bord</a>");
        sortie.println("</div>");
        
        sortie.println("</body></html>");
    }
}
