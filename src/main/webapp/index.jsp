<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Calculatrice Simple - BenSira</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .calculatrice { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; color: #333; margin-bottom: 25px; }
        .champs { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; font-weight: 500; }
        input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; font-size: 16px; }
        .boutons { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-top: 20px; }
        button { padding: 12px; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; font-size: 18px; transition: transform 0.1s, background 0.3s; }
        button:active { transform: scale(0.95); }
        .btn-somme { background-color: #4CAF50; color: white; }
        .btn-sous { background-color: #2196F3; color: white; }
        .btn-mult { background-color: #FF9800; color: white; }
        .btn-div { background-color: #9C27B0; color: white; }
        .resultat-box { margin-top: 25px; padding: 15px; border-radius: 6px; text-align: center; background-color: #e8f5e9; border: 1px solid #c8e6c9; }
        .resultat-valeur { font-size: 24px; font-weight: bold; color: #2e7d32; }
        .erreur { background-color: #ffebee; border: 1px solid #ffcdd2; color: #c62828; }
    </style>
</head>
<body>

<div class="calculatrice">
    <h2>Ma Calculatrice</h2>
    
    <form method="post">
        <div class="champs">
            <label for="n1">Valeur 1 :</label>
            <input type="number" step="any" name="nombre1" id="n1" required value='<%= request.getParameter("nombre1") != null ? request.getParameter("nombre1") : "" %>'>
        </div>
        <div class="champs">
            <label for="n2">Valeur 2 :</label>
            <input type="number" step="any" name="nombre2" id="n2" required value='<%= request.getParameter("nombre2") != null ? request.getParameter("nombre2") : "" %>'>
        </div>
        
        <div class="boutons">
            <button type="submit" name="operation" value="somme" class="btn-somme" title="Addition">+</button>
            <button type="submit" name="operation" value="soustraction" class="btn-sous" title="Soustraction">-</button>
            <button type="submit" name="operation" value="multiplication" class="btn-mult" title="Multiplication">×</button>
            <button type="submit" name="operation" value="division" class="btn-div" title="Division">÷</button>
        <a href="gestionEmployes.jsp" style="display: block; text-align: center; margin-top: 20px; color: #666; text-decoration: none; font-size: 14px;">Gestion des Employés →</a>
    </form>

    <%
        String op = request.getParameter("operation");
        if (op != null) {
            try {
                double n1 = Double.parseDouble(request.getParameter("nombre1"));
                double n2 = Double.parseDouble(request.getParameter("nombre2"));
                double resultat = 0;
                String message = "";
                boolean erreur = false;

                switch (op) {
                    case "somme":
                        resultat = n1 + n2;
                        message = "Somme : " + resultat;
                        break;
                    case "soustraction":
                        resultat = n1 - n2;
                        message = "Reste : " + resultat;
                        break;
                    case "multiplication":
                        resultat = n1 * n2;
                        message = "Produit : " + resultat;
                        break;
                    case "division":
                        if (n2 != 0) {
                            resultat = n1 / n2;
                            message = "Quotient : " + resultat;
                        } else {
                            message = "Division par zéro impossible !";
                            erreur = true;
                        }
                        break;
                }
    %>
                <div class="resultat-box <%= erreur ? "erreur" : "" %>">
                    <strong><%= message %></strong>
                </div>
    <%
            } catch (Exception e) {
    %>
                <div class="resultat-box erreur">
                    <strong>Erreur de saisie !</strong>
                </div>
    <%
            }
        }
    %>
</div>

</body>
</html>