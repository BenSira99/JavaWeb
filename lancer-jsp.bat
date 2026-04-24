@echo off
REM ==============================================================================
REM Script de lancement RAPIDE (JSP) pour Windows - BenSira99
REM Ce script saute le 'clean' pour gagner du temps lors des modifs JSP.
REM ==============================================================================

setlocal
set "commandeMaven=.\mvnw.cmd"

echo.
echo ⚡ Lancement rapide du serveur (Mode JSP)...
echo.

REM Verification de la presence du wrapper Maven
if not exist "%commandeMaven%" (
    echo ❌ Erreur : Le fichier %commandeMaven% est introuvable.
    echo Assurez-vous d'etre a la racine du projet.
    pause
    exit /b 1
)

echo 🛠️ Compilation et deploiement rapide...
REM Ouverture automatique du lien dans le navigateur
start "" "http://localhost:8080/JavaJ2ee/gestionEmployes.jsp"

REM On lance le package et cargo:run sans clean pour la rapidite
call %commandeMaven% package cargo:run -DskipTests

REM Verification du resultat de la commande
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ⚠️ Echec du mode rapide. Tentative avec un nettoyage complet...
    call %commandeMaven% clean package cargo:run -DskipTests
)

echo.
echo ✅ Session terminee.
pause
