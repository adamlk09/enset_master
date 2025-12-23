"""
Module de visualisation pour le dashboard de ventes.
Compatible avec vos tables réelles (Sales Orders, Customers, Products, Regions).
Crée des graphiques professionnels avec un thème sombre.
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
from matplotlib.gridspec import GridSpec
import warnings
from typing import Optional, Tuple

from src.measures import SalesMeasures, calculate_measures_by_dimension, prepare_sales_columns

warnings.filterwarnings('ignore')

# ==== Style et couleurs ====
plt.style.use('dark_background')
COLORS = {
    'primary': '#FF6B6B',
    'secondary': '#4ECDC4',
    'accent': '#FFE66D',
    'background': '#1a1a2e',
    'surface': '#16213e',
    'text': '#EAEAEA',
    'text_muted': '#888888',
    'grid': '#333355',
    'positive': '#00D26A',
    'negative': '#FF4757',
}

def setup_style() -> None:
    plt.rcParams.update({
        'figure.facecolor': COLORS['background'],
        'axes.facecolor': COLORS['surface'],
        'axes.edgecolor': COLORS['grid'],
        'axes.labelcolor': COLORS['text'],
        'axes.titlecolor': COLORS['text'],
        'xtick.color': COLORS['text_muted'],
        'ytick.color': COLORS['text_muted'],
        'grid.color': COLORS['grid'],
        'grid.linestyle': ':',
        'grid.alpha': 0.5,
        'text.color': COLORS['text'],
        'font.family': 'sans-serif',
        'font.size': 10,
        'axes.titlesize': 12,
        'axes.labelsize': 10,
        'legend.fontsize': 9,
        'legend.facecolor': COLORS['surface'],
        'legend.edgecolor': COLORS['grid'],
    })

def format_currency(value: float) -> str:
    if abs(value) >= 1_000_000:
        return f"€{value/1_000_000:.1f}M"
    elif abs(value) >= 1_000:
        return f"€{value/1_000:.0f}K"
    else:
        return f"€{value:.0f}"

def format_percentage(value: float) -> str:
    return f"{value:+.1f}%" if value != 0 else "0%"

# ==== KPI Cards ====
def create_kpi_cards(measures: SalesMeasures, ax: plt.Axes) -> None:
    ax.set_xlim(0, 4)
    ax.set_ylim(0, 1)
    ax.axis('off')

    kpis = [
        {'title': 'Total Sales', 'value': format_currency(measures.total_sales),
         'change': measures.total_sales_py_var_pct, 'icon': '💰'},
        {'title': 'Total Profit', 'value': format_currency(measures.total_profit),
         'change': measures.total_profit_py_var_pct, 'icon': '📈'},
        {'title': 'Profit Margin', 'value': f"{measures.profit_margin_pct:.1f}%",
         'change': None, 'icon': '📊'},
        {'title': 'Order Quantity', 'value': f"{measures.total_order_quantity:,}",
         'change': measures.total_order_quantity_py_var_pct, 'icon': '📦'}
    ]

    for i, kpi in enumerate(kpis):
        x = i + 0.5
        card = mpatches.FancyBboxPatch(
            (x - 0.45, 0.1), 0.9, 0.8,
            boxstyle=mpatches.BoxStyle("Round", pad=0.02, rounding_size=0.1),
            facecolor=COLORS['surface'],
            edgecolor=COLORS['grid'],
            linewidth=1
        )
        ax.add_patch(card)
        ax.text(x, 0.75, f"{kpi['icon']} {kpi['title']}", ha='center', va='center',
                fontsize=9, color=COLORS['text_muted'])
        ax.text(x, 0.45, kpi['value'], ha='center', va='center', fontsize=18,
                fontweight='bold', color=COLORS['text'])
        if kpi['change'] is not None:
            change_color = COLORS['positive'] if kpi['change'] >= 0 else COLORS['negative']
            arrow = '▲' if kpi['change'] >= 0 else '▼'
            ax.text(x, 0.2, f"{arrow} {abs(kpi['change']):.1f}% vs PY", ha='center',
                    va='center', fontsize=9, color=change_color)

# ==== Bar chart CY vs PY ====
def create_bar_chart_cy_vs_py(data: pd.DataFrame, dimension: str,
                               ax: plt.Axes, title: str, top_n: Optional[int] = None) -> None:
    plot_data = data.copy()
    if top_n:
        plot_data = plot_data.nlargest(top_n, 'Sales_CY')
    x = np.arange(len(plot_data))
    width = 0.35
    ax.bar(x - width/2, plot_data['Sales_CY'], width, label='Current Year',
           color=COLORS['primary'], alpha=0.9)
    ax.bar(x + width/2, plot_data['Sales_PY'], width, label='Previous Year',
           color=COLORS['secondary'], alpha=0.7)
    ax.plot(x, plot_data['Sales_PY'], 'o-', color=COLORS['secondary'], linewidth=2, markersize=5, alpha=0.8)
    ax.set_xticks(x)
    ax.set_xticklabels(plot_data[dimension], rotation=45, ha='right', fontsize=8)
    ax.set_ylabel('Sales (€)', fontsize=9)
    ax.set_title(title, fontsize=11, fontweight='bold', pad=10)
    ax.yaxis.set_major_formatter(plt.FuncFormatter(lambda v, p: format_currency(v)))
    ax.grid(True, axis='y', linestyle=':', alpha=0.3)
    ax.set_axisbelow(True)
    ax.legend(loc='upper right', framealpha=0.8)

# ==== Horizontal bar chart ====
def create_horizontal_bar_chart(data: pd.DataFrame, dimension: str, ax: plt.Axes,
                                title: str, top_n: int = 5, ascending: bool = False) -> None:
    plot_data = data.nsmallest(top_n, 'Sales_CY').iloc[::-1] if ascending else data.nlargest(top_n, 'Sales_CY').iloc[::-1]
    y = np.arange(len(plot_data))
    height = 0.35
    ax.barh(y - height/2, plot_data['Sales_CY'], height, label='Current Year', color=COLORS['primary'], alpha=0.9)
    ax.barh(y + height/2, plot_data['Sales_PY'], height, label='Previous Year', color=COLORS['secondary'], alpha=0.7)
    ax.set_yticks(y)
    ax.set_yticklabels(plot_data[dimension], fontsize=9)
    ax.set_xlabel('Sales (€)', fontsize=9)
    ax.set_title(title, fontsize=11, fontweight='bold', pad=10)
    ax.xaxis.set_major_formatter(plt.FuncFormatter(lambda v, p: format_currency(v)))
    ax.grid(True, axis='x', linestyle=':', alpha=0.3)
    ax.set_axisbelow(True)
    ax.legend(loc='lower right', framealpha=0.8)

# ==== Donut chart ====
def create_donut_chart(data: pd.DataFrame, dimension: str, value_column: str, ax: plt.Axes,
                       title: str, top_n: int = 5) -> None:
    plot_data = data.nlargest(top_n, value_column).copy()
    colors = plt.cm.RdYlGn(np.linspace(0.2, 0.8, len(plot_data)))
    wedges, texts, autotexts = ax.pie(
        plot_data[value_column],
        labels=plot_data[dimension],
        autopct='%1.1f%%',
        pctdistance=0.75,
        colors=colors,
        wedgeprops=dict(width=0.5, edgecolor=COLORS['background']),
        textprops={'fontsize': 8, 'color': COLORS['text']}
    )
    for autotext in autotexts:
        autotext.set_fontsize(8)
        autotext.set_fontweight('bold')
    ax.set_title(title, fontsize=11, fontweight='bold', pad=10)
    total = plot_data[value_column].sum()
    ax.text(0, 0, format_currency(total), ha='center', va='center', fontsize=14, fontweight='bold', color=COLORS['text'])

# ==== Area chart (Profit & Margin) ====
def create_area_chart(data: pd.DataFrame, dimension: str, ax: plt.Axes, title: str) -> None:
    x = np.arange(len(data))
    ax.fill_between(x, data['Profit_CY'], alpha=0.6, color=COLORS['primary'], label='Profit CY')
    ax.fill_between(x, data['Profit_PY'], alpha=0.4, color=COLORS['secondary'], label='Profit PY')
    ax.plot(x, data['Profit_CY'], color=COLORS['primary'], linewidth=2)
    ax.plot(x, data['Profit_PY'], color=COLORS['secondary'], linewidth=2, linestyle='--')
    ax.set_xticks(x)
    ax.set_xticklabels(data[dimension], rotation=45, ha='right', fontsize=8)
    ax.set_ylabel('Profit (€)', fontsize=9, color=COLORS['text'])
    ax.set_title(title, fontsize=11, fontweight='bold', pad=10)
    ax.yaxis.set_major_formatter(plt.FuncFormatter(lambda v, p: format_currency(v)))
    ax2 = ax.twinx()
    ax2.plot(x, data['Profit_Margin_Pct'], 'o-', color=COLORS['accent'], linewidth=2, markersize=6, label='Margin %')
    ax2.set_ylabel('Margin %', fontsize=9, color=COLORS['accent'])
    ax2.tick_params(axis='y', colors=COLORS['accent'])
    ax.grid(True, axis='y', linestyle=':', alpha=0.3)
    ax.set_axisbelow(True)
    lines1, labels1 = ax.get_legend_handles_labels()
    lines2, labels2 = ax2.get_legend_handles_labels()
    ax.legend(lines1 + lines2, labels1 + labels2, loc='upper left', framealpha=0.8)

# ==== Full Dashboard ====
def create_sales_dashboard(sales_data: pd.DataFrame,
                           measures: SalesMeasures,
                           date_column: str = 'OrderDate',
                           output_path: Optional[str] = None,
                           figsize: Tuple[int, int] = (16, 12)) -> plt.Figure:
    setup_style()
    sales_data = prepare_sales_columns(sales_data)
    fig = plt.figure(figsize=figsize, facecolor=COLORS['background'])
    gs = GridSpec(3, 3, figure=fig, hspace=0.35, wspace=0.3, height_ratios=[0.8, 1.2, 1.2])
    ax_kpi = fig.add_subplot(gs[0, :])
    create_kpi_cards(measures, ax_kpi)
    current_year = sales_data[date_column].dt.year.max()

    product_data = calculate_measures_by_dimension(sales_data, 'Product Description', date_column, current_year)
    month_data = sales_data.copy()
    month_data['Month'] = month_data[date_column].dt.month_name()
    month_data['Month_No'] = month_data[date_column].dt.month
    month_data = calculate_measures_by_dimension(month_data, 'Month', date_column, current_year)
    month_order = ['January', 'February', 'March', 'April', 'May', 'June',
                   'July', 'August', 'September', 'October', 'November', 'December']
    month_data['Month_Order'] = month_data['Month'].map({m: i for i, m in enumerate(month_order)})
    month_data = month_data.sort_values('Month_Order')

    city_data = calculate_measures_by_dimension(sales_data, 'City', date_column, current_year)
    channel_data = calculate_measures_by_dimension(sales_data, 'Channel', date_column, current_year)
    customer_data = calculate_measures_by_dimension(sales_data, 'Customer Name', date_column, current_year)

    # ROW 2
    ax1 = fig.add_subplot(gs[1, 0])
    create_bar_chart_cy_vs_py(product_data, 'Product Description', ax1, 'Sales by Product: CY vs PY', top_n=8)

    ax2 = fig.add_subplot(gs[1, 1])
    create_bar_chart_cy_vs_py(month_data, 'Month', ax2, 'Sales by Month: CY vs PY')

    ax3 = fig.add_subplot(gs[1, 2])
    create_donut_chart(city_data, 'City', 'Sales_CY', ax3, 'Sales by City (Top 5)', top_n=5)

    # ROW 3
    ax4 = fig.add_subplot(gs[2, 0])
    create_area_chart(channel_data, 'Channel', ax4, 'Profit & Margin by Channel')

    ax5 = fig.add_subplot(gs[2, 1])
    create_horizontal_bar_chart(customer_data, 'Customer Name', ax5, 'Top 5 Customers', top_n=5, ascending=False)

    ax6 = fig.add_subplot(gs[2, 2])
    create_horizontal_bar_chart(customer_data, 'Customer Name', ax6, 'Bottom 5 Customers', top_n=5, ascending=True)

    fig.suptitle('📊 SALES PERFORMANCE DASHBOARD',
                 fontsize=16, fontweight='bold', color=COLORS['text'], y=0.98)

    if output_path:
        fig.savefig(output_path, dpi=150, bbox_inches='tight',
                    facecolor=COLORS['background'], edgecolor='none')
        print(f"✅ Dashboard sauvegardé: {output_path}")

    return fig
