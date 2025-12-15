import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Material } from '../../material';
import { BaseChartDirective } from 'ng2-charts'; // <-- L'IMPORT CORRECT
import { ChartConfiguration } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    Material,
    BaseChartDirective // <-- AJOUTER
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})


export class DashboardComponent {
  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: ['Semaine 1', 'Semaine 2', 'Semaine 3', 'Semaine 4', 'Semaine 5'],
    datasets: [
      {
        data: [65000, 59000, 80000, 81000, 56000],
        label: 'Tendance du Solde Total',
        fill: true,
        tension: 0.4,
        borderColor: 'rgba(63, 81, 181, 1)',
        backgroundColor: 'rgba(63, 81, 181, 0.2)'
      }
    ]
  };
  public lineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true
  };

  constructor() { }
}
