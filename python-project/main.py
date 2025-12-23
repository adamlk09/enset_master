#!/usr/bin/env python3
"""
Script principal pour l'analyse des données de ventes.

Ce script orchestre le chargement, le nettoyage, l'analyse et la 
visualisation des données de ventes.

Usage:
    python main.py [--data-path PATH] [--output-dir DIR] [--use-sample]
    
Auteur: Adam Lakhmiri
Date: 2024
"""

import argparse
import os
import sys
from pathlib import Path

# Ajouter le répertoire src au path
sys.path.insert(0, str(Path(__file__).parent))

import pandas as pd
import matplotlib.pyplot as plt

from src.data_processing import (
    load_sales_data,
    generate_sample_data,
    clean_data,
    create_dimension_tables
)
from src.date_table import create_date_table_from_data
from src.measures import calculate_all_measures, print_measures_summary
from src.visualizations import create_sales_dashboard


def parse_arguments():
    """Parse les arguments de la ligne de commande."""
    parser = argparse.ArgumentParser(
        description='Analyse des données de ventes avec visualisations.'
    )
    parser.add_argument(
        '--data-path', 
        type=str, 
        default='data/Sales.xlsx',
        help='Chemin vers le fichier de données (défaut: data/Sales.xlsx)'
    )
    parser.add_argument(
        '--output-dir', 
        type=str, 
        default='outputs',
        help='Répertoire de sortie (défaut: outputs)'
    )
    parser.add_argument(
        '--use-sample', 
        action='store_true',
        help='Utiliser des données d\'exemple au lieu du fichier'
    )
    parser.add_argument(
        '--sample-size', 
        type=int, 
        default=5000,
        help='Nombre d\'enregistrements pour les données d\'exemple (défaut: 5000)'
    )
    
    return parser.parse_args()


def main():
    """Fonction principale d'exécution."""
    
    # Parser les arguments
    args = parse_arguments()
    
    print("\n" + "=" * 60)
    print("🚀 DÉMARRAGE DE L'ANALYSE DES VENTES")
    print("=" * 60)
    
    # Créer le répertoire de sortie
    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # ===== ÉTAPE 1: CHARGEMENT DES DONNÉES =====
    print("\n📁 ÉTAPE 1: Chargement des données...")
    
    if args.use_sample or not Path(args.data_path).exists():
        if not args.use_sample:
            print(f"⚠️  Fichier non trouvé: {args.data_path}")
            print("   Génération de données d'exemple...")
        sales_raw = generate_sample_data(n_records=args.sample_size)
    else:
        sales_raw = load_sales_data(args.data_path)
    
    # ===== ÉTAPE 2: NETTOYAGE DES DONNÉES =====
    print("\n🧹 ÉTAPE 2: Nettoyage des données...")
    sales_clean = clean_data(sales_raw)
    
    # ===== ÉTAPE 3: CRÉATION DES TABLES DIMENSIONNELLES =====
    print("\n📊 ÉTAPE 3: Création des tables dimensionnelles...")
    customer_data, products_data, regions_table, sales_data = create_dimension_tables(sales_clean)
    
    # ===== ÉTAPE 4: CRÉATION DE LA TABLE DE DATES =====
    print("\n📅 ÉTAPE 4: Création de la table de dates...")
    date_table = create_date_table_from_data(sales_data, 'Order_Date')
    
    # ===== ÉTAPE 5: CALCUL DES MESURES =====
    print("\n📈 ÉTAPE 5: Calcul des mesures...")
    measures = calculate_all_measures(sales_data, 'Order_Date')
    print_measures_summary(measures)
    
    # ===== ÉTAPE 6: CRÉATION DU DASHBOARD =====
    print("\n🎨 ÉTAPE 6: Création du dashboard...")
    dashboard_path = output_dir / 'dashboard.png'
    
    fig = create_sales_dashboard(
        sales_data=sales_data,
        measures=measures,
        date_column='Order_Date',
        output_path=str(dashboard_path)
    )
    
    # Afficher le dashboard
    plt.show()
    
    # ===== RÉSUMÉ FINAL =====
    print("\n" + "=" * 60)
    print("✅ ANALYSE TERMINÉE AVEC SUCCÈS!")
    print("=" * 60)
    print(f"\n📁 Fichiers générés dans '{output_dir}':")
    print(f"   - dashboard.png")
    
    # Sauvegarder les données traitées (optionnel)
    sales_data.to_csv(output_dir / 'sales_data_processed.csv', index=False)
    date_table.to_csv(output_dir / 'date_table.csv', index=False)
    print(f"   - sales_data_processed.csv")
    print(f"   - date_table.csv")
    
    print("\n📊 Résumé des données:")
    print(f"   - Période: {sales_data['Order_Date'].min().date()} à {sales_data['Order_Date'].max().date()}")
    print(f"   - Transactions: {len(sales_data):,}")
    print(f"   - Clients uniques: {sales_data['Customer_Name'].nunique():,}")
    print(f"   - Produits: {sales_data['Product'].nunique():,}")
    
    return 0


if __name__ == '__main__':
    sys.exit(main())
