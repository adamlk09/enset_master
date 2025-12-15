#!/bin/bash
echo "Entrez un nombre :"
read nombre

for i in {1..10}; do
    echo "$nombre x $i = $((nombre * i))"
done
