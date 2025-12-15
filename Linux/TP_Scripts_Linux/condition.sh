#!/bin/bash
echo "Entrez un nombre :"
read nombre

if [ $((nombre % 2)) -eq 0 ]; then
    echo "Le nombre est pair."
else
    echo "Le nombre est impair."
fi
