#!/bin/bash

echo "Entrez le chemin du fichier à sauvegarder :"
read fichier

if [ ! -f "$fichier" ]; then
    echo "Erreur : le fichier n'existe pas."
    exit 1
fi

echo "Entrez le dossier de destination :"
read dossier

if [ ! -d "$dossier" ]; then
    mkdir -p "$dossier"
fi

cp "$fichier" "$dossier"
echo "Fichier sauvegardé avec succès dans $dossier."
