"""
Module de calcul des mesures (KPIs) pour l'analyse de ventes.
Compatible avec vos tables réelles (Sales Orders).
"""

import pandas as pd
import numpy as np
from typing import Optional, Tuple, List
from dataclasses import dataclass


@dataclass
class SalesMeasures:
    total_sales: float = 0.0
    total_sales_py: float = 0.0
    total_sales_py_var: float = 0.0
    total_sales_py_var_pct: float = 0.0
    total_profit: float = 0.0
    total_profit_py: float = 0.0
    total_profit_py_var: float = 0.0
    total_profit_py_var_pct: float = 0.0
    profit_margin_pct: float = 0.0
    total_cost: float = 0.0
    total_order_quantity: int = 0
    total_order_quantity_py: int = 0
    total_order_quantity_py_var: int = 0
    total_order_quantity_py_var_pct: float = 0.0


def prepare_sales_columns(df: pd.DataFrame) -> pd.DataFrame:
    """
    Assure que les colonnes Sales, Total_Cost et Profit existent.
    """
    if 'Sales' not in df.columns:
        df['Sales'] = df['Order Quantity'] * df['Unit Selling Price']
    if 'Total_Cost' not in df.columns:
        df['Total_Cost'] = df['Order Quantity'] * df['Unit Cost']
    if 'Profit' not in df.columns:
        df['Profit'] = df['Sales'] - df['Total_Cost']
    return df


def calculate_yoy(df: pd.DataFrame, date_column: str, value_column: str,
                  current_year: Optional[int] = None) -> Tuple[float, float, float, float]:
    """
    Calcule YoY (Current Year vs Previous Year)
    """
    if current_year is None:
        current_year = df[date_column].dt.year.max()
    previous_year = current_year - 1
    cy_total = df[df[date_column].dt.year == current_year][value_column].sum()
    py_total = df[df[date_column].dt.year == previous_year][value_column].sum()
    var = cy_total - py_total
    var_pct = (var / cy_total * 100) if cy_total != 0 else 0.0
    return cy_total, py_total, var, var_pct


def calculate_profit_margin(total_profit: float, total_sales: float) -> float:
    return (total_profit / total_sales * 100) if total_sales != 0 else 0.0


def calculate_all_measures(df: pd.DataFrame, date_column: str = 'OrderDate',
                           current_year: Optional[int] = None) -> SalesMeasures:
    """
    Calcule toutes les mesures pour les KPIs
    """
    df = prepare_sales_columns(df)
    measures = SalesMeasures()

    # Sales YoY
    (measures.total_sales, measures.total_sales_py,
     measures.total_sales_py_var, measures.total_sales_py_var_pct) = calculate_yoy(df, date_column, 'Sales', current_year)

    # Profit YoY
    (measures.total_profit, measures.total_profit_py,
     measures.total_profit_py_var, measures.total_profit_py_var_pct) = calculate_yoy(df, date_column, 'Profit', current_year)

    # Profit Margin
    measures.profit_margin_pct = calculate_profit_margin(measures.total_profit, measures.total_sales)

    # Total Cost
    measures.total_cost = df['Total_Cost'].sum()

    # Order Quantity YoY
    (qty_cy, qty_py, qty_var, qty_var_pct) = calculate_yoy(df, date_column, 'Order Quantity', current_year)
    measures.total_order_quantity = int(qty_cy)
    measures.total_order_quantity_py = int(qty_py)
    measures.total_order_quantity_py_var = int(qty_var)
    measures.total_order_quantity_py_var_pct = qty_var_pct

    return measures


def calculate_measures_by_dimension(df: pd.DataFrame, dimension: str,
                                    date_column: str = 'OrderDate',
                                    current_year: Optional[int] = None) -> pd.DataFrame:
    """
    Calcule les mesures par dimension (Product, Customer, City, Channel, etc.)
    """
    df = prepare_sales_columns(df)
    if current_year is None:
        current_year = df[date_column].dt.year.max()
    prev_year = current_year - 1

    cy = df[df[date_column].dt.year == current_year].groupby(dimension).agg({
        'Sales': 'sum', 'Profit': 'sum', 'Order Quantity': 'sum', 'Total_Cost': 'sum'
    }).reset_index().rename(columns={
        'Sales': 'Sales_CY', 'Profit': 'Profit_CY', 'Order Quantity': 'Qty_CY', 'Total_Cost': 'Cost_CY'
    })

    py = df[df[date_column].dt.year == prev_year].groupby(dimension).agg({
        'Sales': 'sum', 'Profit': 'sum', 'Order Quantity': 'sum'
    }).reset_index().rename(columns={
        'Sales': 'Sales_PY', 'Profit': 'Profit_PY', 'Order Quantity': 'Qty_PY'
    })

    result = pd.merge(cy, py, on=dimension, how='outer').fillna(0)
    result['Sales_Var'] = result['Sales_CY'] - result['Sales_PY']
    result['Sales_Var_Pct'] = np.where(result['Sales_CY'] != 0,
                                       result['Sales_Var'] / result['Sales_CY'] * 100, 0)
    result['Profit_Margin_Pct'] = np.where(result['Sales_CY'] != 0,
                                           result['Profit_CY'] / result['Sales_CY'] * 100, 0)
    return result


def print_measures_summary(measures: SalesMeasures) -> None:
    """
    Affiche un résumé des KPIs
    """
    print("\n" + "=" * 60)
    print("📊 RÉSUMÉ DES MESURES (KPIs)")
    print("=" * 60)
    print(f"\n💰 VENTES: Total Sales (CY): €{measures.total_sales:,.2f} | PY: €{measures.total_sales_py:,.2f} | Δ€{measures.total_sales_py_var:,.2f} ({measures.total_sales_py_var_pct:+.1f}%)")
    print(f"📈 PROFIT: Total Profit (CY): €{measures.total_profit:,.2f} | PY: €{measures.total_profit_py:,.2f} | Δ€{measures.total_profit_py_var:,.2f} ({measures.total_profit_py_var_pct:+.1f}%) | Marge: {measures.profit_margin_pct:.1f}%")
    print(f"📦 COMMANDES: Qty (CY): {measures.total_order_quantity:,} | PY: {measures.total_order_quantity_py:,} | Δ{measures.total_order_quantity_py_var:,} ({measures.total_order_quantity_py_var_pct:+.1f}%)")
    print(f"💵 COÛTS: Total Cost: €{measures.total_cost:,.2f}")
    print("=" * 60)
