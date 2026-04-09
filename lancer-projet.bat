@echo off
REM ==============================================================================
REM Script de lancement pour Windows du projet Java J2EE (BenSira99)
REM Ce fichier automatise la compilation et le déploiement sur Tomcat.
REM ==============================================================================

setlocal
set "commandeMaven=.\mvnw.cmd"

echo.
echo 🚀 Preparation du lancement du projet...
echo.

REM Verification de la presence du wrapper Maven
if not exist "%commandeMaven%" (
    echo ❌ Erreur : Le fichier %commandeMaven% est introuvable.
    echo Assurez-vous d'etre a la racine du projet.
    pause
    exit /b 1
)

echo 🛠️  Nettoyage et compilation en cours...
call %commandeMaven% clean package cargo:run

REM Verification du resultat de la commande
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ⚠️  Une erreur est survenue lors du lancement du projet.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ✅ Projet arrete normalement.
pause
