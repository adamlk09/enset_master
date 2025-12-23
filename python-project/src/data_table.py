"""
Module de création de la table de dates (DateTable).
Génère une table calendrier complète pour l'analyse temporelle.
Compatible avec vos tables réelles.
"""

import pandas as pd
import numpy as np
from typing import Optional, List


def create_date_table(start_date: str, end_date: str, fiscal_start_month: int = 4) -> pd.DataFrame:
    """
    Crée une table de dates complète avec toutes les colonnes temporelles.
    
    Parameters
    ----------
    start_date : str
        Date de début au format 'YYYY-MM-DD'
    end_date : str
        Date de fin au format 'YYYY-MM-DD'
    fiscal_start_month : int
        Mois de début de l'année fiscale (défaut: avril)
        
    Returns
    -------
    pd.DataFrame
        Table de dates avec colonnes: Date, Year, Quarter, Month, 
        Month_Name, Month_No, Day, Day_Name, Week_No, Is_Weekend, etc.
    """
    # Générer la plage de dates
    dates = pd.date_range(start=start_date, end=end_date, freq='D')
    date_table = pd.DataFrame({'Date': dates})

    # Composants temporels
    date_table['Year'] = date_table['Date'].dt.year
    date_table['Quarter'] = date_table['Date'].dt.quarter
    date_table['Quarter_Name'] = 'Q' + date_table['Quarter'].astype(str)
    date_table['Month'] = date_table['Date'].dt.month
    date_table['Month_No'] = date_table['Month']
    date_table['Month_Name'] = date_table['Date'].dt.month_name()
    date_table['Month_Short'] = date_table['Date'].dt.strftime('%b')
    date_table['Day'] = date_table['Date'].dt.day
    date_table['Day_Name'] = date_table['Date'].dt.day_name()
    date_table['Day_Short'] = date_table['Date'].dt.strftime('%a')
    date_table['Week_No'] = date_table['Date'].dt.isocalendar().week.astype(int)
    date_table['Day_Of_Week'] = date_table['Date'].dt.dayofweek + 1  # Lundi=1
    date_table['Day_Of_Year'] = date_table['Date'].dt.dayofyear
    date_table['Is_Weekend'] = date_table['Day_Of_Week'].isin([6, 7])
    date_table['Is_Month_Start'] = date_table['Date'].dt.is_month_start
    date_table['Is_Month_End'] = date_table['Date'].dt.is_month_end
    date_table['Is_Quarter_Start'] = date_table['Date'].dt.is_quarter_start
    date_table['Is_Quarter_End'] = date_table['Date'].dt.is_quarter_end
    date_table['Is_Year_Start'] = date_table['Date'].dt.is_year_start
    date_table['Is_Year_End'] = date_table['Date'].dt.is_year_end
    date_table['Year_Month'] = date_table['Date'].dt.to_period('M').astype(str)
    date_table['Year_Quarter'] = date_table['Year'].astype(str) + '-Q' + date_table['Quarter'].astype(str)
    date_table['Date_PY'] = date_table['Date'] - pd.DateOffset(years=1)

    # Fiscal Year
    date_table['Fiscal_Year'] = np.where(
        date_table['Month'] >= fiscal_start_month,
        date_table['Year'],
        date_table['Year'] - 1
    )
    date_table['Fiscal_Quarter'] = ((date_table['Month'] - fiscal_start_month) % 12) // 3 + 1

    assert date_table['Date'].is_unique, "Les dates doivent être uniques"
    assert not date_table['Date'].isna().any(), "Aucune date ne doit être nulle"

    print(f"📅 Table de dates créée: {len(date_table)} jours, de {start_date} à {end_date}")
    return date_table


def create_date_table_from_sales(sales_df: pd.DataFrame, 
                                 date_columns: Optional[List[str]] = None,
                                 buffer_months: int = 1,
                                 fiscal_start_month: int = 4) -> pd.DataFrame:
    """
    Crée une table de dates basée sur les colonnes de date d'une table de ventes.
    
    Parameters
    ----------
    sales_df : pd.DataFrame
        Table de ventes
    date_columns : List[str], optional
        Liste des colonnes de date à considérer (ex: ['OrderDate', 'Ship Date'])
        Si None, tentera de détecter automatiquement les colonnes contenant 'date'
    buffer_months : int
        Mois supplémentaires avant/après pour couvrir toute la période
    fiscal_start_month : int
        Mois de début de l'année fiscale
    
    Returns
    -------
    pd.DataFrame
        Table de dates couvrant toutes les dates de vos ventes
    """
    if date_columns is None:
        date_columns = [col for col in sales_df.columns if 'date' in col.lower()]
    
    if not date_columns:
        raise ValueError("Aucune colonne de date trouvée dans les données de ventes")

    min_date = sales_df[date_columns].min().min()
    max_date = sales_df[date_columns].max().max()

    start_date = (min_date - pd.DateOffset(months=buffer_months)).replace(day=1)
    end_date = (max_date + pd.DateOffset(months=buffer_months))
    end_date = (end_date.replace(day=1) + pd.DateOffset(months=1) - pd.DateOffset(days=1))

    return create_date_table(
        start_date=start_date.strftime('%Y-%m-%d'),
        end_date=end_date.strftime('%Y-%m-%d'),
        fiscal_start_month=fiscal_start_month
    )
