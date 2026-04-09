package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe générale pour la gestion de la connexion à la base de données.
 * Mise à jour pour utiliser SQLite (produits.db) selon la demande de l'utilisateur.
 * Toute l'implémentation est en Français.
 */
public class connexion {

    // Paramètres de connexion pour SQLite
    private static final String piloteSqlite = "org.sqlite.JDBC";
    private static final String urlBaseDonnees = "jdbc:sqlite:produits.db";

    // Instance unique pour la connexion
    private static Connection laConnexionInstance = null;

    /**
     * Charge le pilote SQLite au démarrage de la classe.
     */
    static {
        try {
            Class.forName(piloteSqlite);
            System.out.println("✅ Pilote SQLite JDBC chargé avec succès.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Erreur : Pilote SQLite introuvable.");
            e.printStackTrace();
        }
    }

    /**
     * Permet d'obtenir une connexion active à la base produits.db.
     * @return Connection La connexion JDBC à SQLite.
     */
    public static Connection obtenirConnexion() throws SQLException {
        if (laConnexionInstance == null || laConnexionInstance.isClosed()) {
            try {
                laConnexionInstance = DriverManager.getConnection(urlBaseDonnees);
                System.out.println("✅ Connexion à SQLite (produits.db) établie.");
            } catch (SQLException e) {
                System.err.println("❌ Échec de la connexion à la base de données SQLite.");
                throw e;
            }
        }
        return laConnexionInstance;
    }

    /**
     * Ferme proprement la connexion si elle est ouverte.
     */
    public static void fermerConnexion() {
        if (laConnexionInstance != null) {
            try {
                laConnexionInstance.close();
                System.out.println("✅ Connexion SQLite fermée.");
            } catch (SQLException e) {
                System.err.println("❌ Erreur lors de la fermeture de la connexion.");
                e.printStackTrace();
            }
        }
    }
}
