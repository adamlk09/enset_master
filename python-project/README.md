# Sales Data Analysis Project

This project provides a comprehensive framework for analyzing sales data using Python. It includes automated data loading, cleaning, processing, key performance indicator (KPI) calculations, and interactive visualizations to gain insights into sales performance, trends, and patterns.

## Features

- **Data Loading & Cleaning**: Automated import and preprocessing of sales data from Excel files
- **Date Table Creation**: Generation of calendar dimensions for time-based analysis
- **Measures Calculation**: Computation of key sales metrics and KPIs
- **Interactive Visualizations**: Creation of charts, dashboards, and reports for data insights

## Project Structure

```
project/
│
├── data/
│   └── Sales.xlsx                    # Raw sales data file
│
├── notebooks/
│   ├── 01_data_loading_cleaning.ipynb    # Data import and preprocessing
│   ├── 02_date_table_creation.ipynb      # Calendar dimension creation
│   ├── 03_measures_calculation.ipynb      # KPI and metrics calculation
│   └── 04_visualizations.ipynb            # Charts and dashboard creation
│
├── src/
│   ├── data_processing.py             # Data loading and cleaning functions
│   ├── measures.py                    # KPI calculation functions
│   └── visualizations.py              # Plotting and visualization functions
│
├── outputs/
│   ├── dashboard.png                  # Generated dashboard image
│   └── report.pdf                     # Analysis report
│
├── requirements.txt                   # Python dependencies
└── README.md                         # Project documentation
```

## Prerequisites

- Python 3.8 or higher
- pip package manager
- Jupyter Notebook or JupyterLab for running notebooks

## Installation

1. **Clone or download the project** to your local machine

2. **Navigate to the project directory**:

   ```bash
   cd python-project
   ```

3. **Create a virtual environment** (recommended):

   ```bash
   python -m venv venv
   # On Windows:
   venv\Scripts\activate
   # On macOS/Linux:
   source venv/bin/activate
   ```

4. **Install dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

## Data Preparation

1. Place your sales data in `data/Sales.xlsx`
2. Ensure the Excel file contains columns for:
   - Date (transaction dates)
   - Product/Service information
   - Sales amounts
   - Customer information (optional)
   - Geographic data (optional)

## Usage

### Running the Analysis

Execute the Jupyter notebooks in the following order:

1. **01_data_loading_cleaning.ipynb**

   - Load sales data from Excel
   - Handle missing values and data types
   - Basic data validation and cleaning

2. **02_date_table_creation.ipynb**

   - Create date dimension table
   - Extract date components (year, month, quarter, day of week)
   - Generate calendar hierarchies

3. **03_measures_calculation.ipynb**

   - Calculate key sales metrics:
     - Total sales
     - Average transaction value
     - Sales by period
     - Growth rates
     - Customer metrics

4. **04_visualizations.ipynb**
   - Create interactive charts and graphs
   - Generate dashboard visualizations
   - Export reports and images

### Running Individual Notebooks

```bash
jupyter notebook notebooks/01_data_loading_cleaning.ipynb
```

Or use JupyterLab:

```bash
jupyter lab
```

## Dependencies

The project uses the following main libraries:

- **pandas**: Data manipulation and analysis
- **numpy**: Numerical computing
- **matplotlib**: Static plotting
- **seaborn**: Statistical data visualization
- **openpyxl**: Excel file handling

## Outputs

- **dashboard.png**: Visual dashboard with key metrics
- **report.pdf**: Comprehensive analysis report
- Interactive charts and visualizations within notebooks

## Customization

- Modify the data processing logic in `src/data_processing.py`
- Add new KPIs in `src/measures.py`
- Customize visualizations in `src/visualizations.py`
- Extend notebooks for additional analysis steps

## Troubleshooting

### Common Issues

1. **Import Errors**: Ensure all dependencies are installed with `pip install -r requirements.txt`

2. **File Not Found**: Verify that `Sales.xlsx` is placed in the `data/` directory

3. **Jupyter Not Starting**: Install Jupyter with `pip install jupyter` if not included

4. **Excel Reading Issues**: Ensure openpyxl is installed for .xlsx file support

### Data Format Requirements

- Excel file must be in .xlsx format
- Date columns should be in recognizable date format
- Numeric columns should not contain non-numeric characters

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source. Feel free to use and modify as needed.

## Support

For questions or issues, please check the troubleshooting section or create an issue in the repository.
