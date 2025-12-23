# Measures Module

def calculate_total_sales(df):
    """
    Calculate total sales.
    """
    # Add calculation logic here
    return df['Sales'].sum() if 'Sales' in df.columns else 0

def calculate_average_sales(df):
    """
    Calculate average sales.
    """
    # Add calculation logic here
    return df['Sales'].mean() if 'Sales' in df.columns else 0