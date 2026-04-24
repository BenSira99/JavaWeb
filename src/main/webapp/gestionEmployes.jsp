<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, com.bensira.javaj2ee.employe.modeles.Employe, com.bensira.javaj2ee.employe.controllers.BaseDonneesEmploye" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Employés - BenSira</title>
    <style>
        body { font-family: 'Inter', system-ui, -apple-system, sans-serif; background-color: #f3f4f6; margin: 0; padding: 40px; color: #1f2937; }
        .container { max-width: 800px; margin: 0 auto; }
        .card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); margin-bottom: 25px; }
        h2 { margin-top: 0; color: #111827; font-size: 1.5rem; border-bottom: 2px solid #e5e7eb; padding-bottom: 10px; margin-bottom: 20px; }
        
        .form-group { margin-bottom: 15px; }
        label { display: block; font-size: 0.875rem; font-weight: 600; margin-bottom: 5px; color: #374151; }
        input { width: 100%; padding: 10px; border: 1px solid #d1d5db; border-radius: 6px; box-sizing: border-box; font-size: 1rem; transition: border-color 0.2s; }
        input:focus { outline: none; border-color: #3b82f6; ring: 2px solid #bfdbfe; }
        
        .actions { display: flex; gap: 12px; margin-top: 25px; }
        .btn { flex: 1; padding: 12px; border: none; border-radius: 6px; font-weight: 600; cursor: pointer; transition: all 0.2s; font-size: 1rem; }
        .btn-ajout { background-color: #2563eb; color: white; }
        .btn-ajout:hover { background-color: #1d4ed8; }
        .btn-sauvegarder { background-color: #f59e0b; color: white; }
        .btn-sauvegarder:hover { background-color: #d97706; }
        
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; border-radius: 8px; overflow: hidden; }
        th { background-color: #f9fafb; color: #4b5563; font-weight: 600; text-align: left; padding: 12px 15px; border-bottom: 1px solid #e5e7eb; }
        td { padding: 12px 15px; border-bottom: 1px solid #f3f4f6; color: #374151; }
        tr:last-child td { border-bottom: none; }
        tr:hover { background-color: #f9fafb; }
        
        .empty-state { text-align: center; padding: 20px; color: #6b7280; font-style: italic; }
        .badge-success { background: #dcfce7; color: #166534; padding: 8px 12px; border-radius: 6px; margin-bottom: 15px; display: inline-block; font-size: 0.875rem; }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <h2>Saisie Employé</h2>
        
        <%
            // Initialisation de la liste en session si elle n'existe pas
            List<Employe> listeEmployes = (List<Employe>) session.getAttribute("liste_employes");
            if (listeEmployes == null) {
                listeEmployes = new ArrayList<Employe>();
                session.setAttribute("liste_employes", listeEmployes);
            }

            String message = null;
            String action = request.getParameter("action");
            boolean afficherListe = "liste".equals(action) || session.getAttribute("vue_liste") != null;

            // Traitement de l'ajout
            if ("ajout".equals(action)) {
                String mat = request.getParameter("mat");
                String nom = request.getParameter("nom");
                String salaireStr = request.getParameter("salaire");
                
                if (mat != null && nom != null && salaireStr != null && !mat.isEmpty() && !nom.isEmpty()) {
                    try {
                        double salaire = Double.parseDouble(salaireStr);
                        listeEmployes.add(new Employe(mat, nom, salaire));
                        message = "Employé " + nom + " ajouté avec succès !";
                    } catch (NumberFormatException e) {
                        message = "Erreur : Le salaire doit être un nombre.";
                    }
                }
            }
            
            // Gestion de l'affichage
            if ("liste".equals(action)) {
                session.setAttribute("vue_liste", true);
                afficherListe = true;
            }

            // Traitement de la sauvegarde
            if ("sauvegarder".equals(action)) {
                if (!listeEmployes.isEmpty()) {
                    BaseDonneesEmploye.sauvegarderEmployes(listeEmployes);
                    message = "Liste des employés sauvegardée dans la base de données avec succès !";
                } else {
                    message = "Aucun employé à sauvegarder.";
                }
            }
        %>

        <% if (message != null) { %>
            <div class="badge-success"><%= message %></div>
        <% } %>

        <form method="post">
            <div class="form-group">
                <label>Matricule :</label>
                <input type="text" name="mat" required>
            </div>
            <div class="form-group">
                <label>Nom Complet :</label>
                <input type="text" name="nom" required>
            </div>
            <div class="form-group">
                <label>Salaire :</label>
                <input type="number" step="0.01" name="salaire" required>
            </div>
            
            <div class="actions">
                <button type="submit" name="action" value="ajout" class="btn btn-ajout">Ajouter l'Employé</button>
                <button type="submit" name="action" value="liste" class="btn btn-liste">Afficher la Liste</button>
                <button type="submit" name="action" value="sauvegarder" class="btn btn-sauvegarder">Sauvegarder</button>
            </div>
        </form>
    </div>

    <% if (afficherListe) { %>
    <div class="card">
        <h2>Liste des Employés</h2>
        <table>
            <thead>
                <tr>
                    <th>Matricule</th>
                    <th>Nom Complet</th>
                    <th>Salaire</th>
                </tr>
            </thead>
            <tbody>
                <% if (listeEmployes.isEmpty()) { %>
                    <tr>
                        <td colspan="3" class="empty-state">Aucun employé enregistré pour le moment.</td>
                    </tr>
                <% } else { 
                    for (Employe e : listeEmployes) { %>
                    <tr>
                        <td><strong><%= e.obtenirMatricule() %></strong></td>
                        <td><%= e.obtenirNom() %></td>
                        <td><%= String.format("%.2f €", e.obtenirSalaire()) %></td>
                    </tr>
                <%   } 
                } %>
            </tbody>
        </table>
    </div>
    <% } %>
</div>

</body>
</html>
