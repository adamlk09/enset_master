"""
Module de traitement et nettoyage des données générique.
Compatible avec vos tables Sales Orders, Customers, Regions et Products.
"""

import pandas as pd
import numpy as np
from typing import Tuple
import warnings

warnings.filterwarnings('ignore')


def load_data(filepath: str, sheet_name: str = None) -> pd.DataFrame:
    """
    Charge un fichier Excel ou une feuille spécifique.
    """
    try:
        df = pd.read_excel(filepath, sheet_name=sheet_name, engine='openpyxl')
        if df.empty:
            raise ValueError(f"Le fichier Excel {filepath} est vide")
        print(f"✅ Données chargées: {len(df)} lignes, {len(df.columns)} colonnes")
        return df
    except FileNotFoundError:
        raise FileNotFoundError(f"Fichier non trouvé: {filepath}")
    except Exception as e:
        raise ValueError(f"Erreur lors du chargement: {str(e)}")


def clean_data(df: pd.DataFrame) -> pd.DataFrame:
    """
    Nettoie les données génériques.
    - Supprime les doublons
    - Remplit les valeurs manquantes
    - Convertit les dates
    - Assure que les valeurs numériques sont positives
    """
    initial_count = len(df)
    df = df.drop_duplicates()
    duplicates_removed = initial_count - len(df)

    # Valeurs numériques
    numeric_cols = df.select_dtypes(include=[np.number]).columns
    df[numeric_cols] = df[numeric_cols].fillna(df[numeric_cols].median())

    # Colonnes texte
    categorical_cols = df.select_dtypes(include=['object']).columns
    df[categorical_cols] = df[categorical_cols].fillna('Unknown')

    # Convertir les colonnes de date
    date_cols = [col for col in df.columns if 'date' in col.lower()]
    for col in date_cols:
        df[col] = pd.to_datetime(df[col], errors='coerce')

    # S'assurer que les valeurs numériques sont positives
    for col in ['Order Quantity', 'Unit Selling Price', 'Unit Cost']:
        if col in df.columns:
            df[col] = df[col].abs()

    print(f"🧹 Nettoyage terminé: Doublons supprimés: {duplicates_removed}, Lignes finales: {len(df)}")
    return df


def create_dimension_tables(sales_df: pd.DataFrame,
                            customers_df: pd.DataFrame = None,
                            regions_df: pd.DataFrame = None,
                            products_df: pd.DataFrame = None
                            ) -> Tuple[pd.DataFrame, pd.DataFrame, pd.DataFrame, pd.DataFrame]:
    """
    Crée des tables dimensionnelles et une table de faits à partir des données fournies.
    Compatible avec vos tables réelles.
    """
    # ----- Customers -----
    if customers_df is not None:
        customer_cols = [c for c in ['Customer Index', 'Customer Names', 'Size', 'Capital'] if c in customers_df.columns]
        customer_data = customers_df[customer_cols].drop_duplicates().reset_index(drop=True)
        customer_data['Customer_ID'] = customer_data['Customer Index'].astype(str)
    elif 'Customer Name' in sales_df.columns or 'Customer Index' in sales_df.columns:
        customer_cols = [c for c in ['Customer Index', 'Customer Name'] if c in sales_df.columns]
        customer_data = sales_df[customer_cols].drop_duplicates().reset_index(drop=True)
        customer_data['Customer_ID'] = customer_data.iloc[:, 0].astype(str)
    else:
        customer_data = pd.DataFrame()

    # ----- Products -----
    if products_df is not None:
        product_cols = [c for c in ['Index', 'Product Name', 'Customer Index', 'Customer Names', 'Size', 'Capital'] if c in products_df.columns]
        products_data = products_df[product_cols].drop_duplicates().reset_index(drop=True)
        products_data['Product_ID'] = products_data['Index'].astype(str)
    elif 'Product Description' in sales_df.columns:
        product_cols = [c for c in ['Product Description'] if c in sales_df.columns]
        products_data = sales_df[product_cols].drop_duplicates().reset_index(drop=True)
        products_data['Product_ID'] = range(1, len(products_data)+1)
    else:
        products_data = pd.DataFrame()

    # ----- Regions -----
    if regions_df is not None:
        region_cols = [c for c in ['Index', 'Suburb', 'City', 'postcode', 'Longitude', 'Latitude', 'Full Address'] if c in regions_df.columns]
        regions_table = regions_df[region_cols].drop_duplicates().reset_index(drop=True)
        regions_table['Region_ID'] = regions_table['Index'].astype(str)
    elif 'Delivery Region Index' in sales_df.columns or 'City' in sales_df.columns:
        region_cols = [c for c in ['Delivery Region Index', 'City'] if c in sales_df.columns]
        regions_table = sales_df[region_cols].drop_duplicates().reset_index(drop=True)
        regions_table['Region_ID'] = range(1, len(regions_table)+1)
    else:
        regions_table = pd.DataFrame()

    # ----- Sales Fact Table -----
    fact_cols = [c for c in sales_df.columns if c in [
        'OrderNumber', 'OrderDate', 'Ship Date', 'Customer Name', 'Index',
        'Channel', 'Currency Code', 'Warehouse Code', 'Delivery Region Index',
        'Product Description', 'Order Quantity', 'Unit Selling Price', 'Unit Cost'
    ]]
    sales_data = sales_df[fact_cols].copy()
    
    if 'Order Quantity' in sales_data.columns and 'Unit Selling Price' in sales_data.columns:
        sales_data['Sales'] = sales_data['Order Quantity'] * sales_data['Unit Selling Price']
    if 'Unit Cost' in sales_data.columns and 'Order Quantity' in sales_data.columns:
        sales_data['Total_Cost'] = sales_data['Order Quantity'] * sales_data['Unit Cost']
    if 'Sales' in sales_data.columns and 'Total_Cost' in sales_data.columns:
        sales_data['Profit'] = sales_data['Sales'] - sales_data['Total_Cost']

    print(f"📊 Tables créées: Customers={len(customer_data)}, Products={len(products_data)}, Regions={len(regions_table)}, Sales={len(sales_data)}")
    return customer_data, products_data, regions_table, sales_data
