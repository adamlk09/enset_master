import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Material } from '../../material';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Customer } from '../../core/models/customer.model';
import { CustomerService } from './customer';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReactiveFormsModule, FormBuilder, FormGroup  } from '@angular/forms';
import { debounceTime } from 'rxjs';

@Component({
  selector: 'app-customers',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    ReactiveFormsModule,
    Material
  ],
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})

export class CustomersComponent implements AfterViewInit {
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'actions'];
  dataSource: MatTableDataSource<Customer> = new MatTableDataSource();
  searchForm: FormGroup;
  /*searchForm = this.fb.group({
    keyword: ['']
  });*/

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private customerService: CustomerService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder
  ) {
    this.searchForm = this.fb.group({
      keyword: ['']
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadCustomers();

    // Recherche dynamique
    this.searchForm.get('keyword')?.valueChanges.pipe(debounceTime(300)).subscribe(() => {
      this.loadCustomers();
    });
  }

  loadCustomers() {
    // Pour l'exemple, nous allons utiliser getCustomers.
    // Adaptez pour utiliser searchCustomers si votre backend le supporte.
    this.customerService.getCustomers().subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (err) => {
        console.error("Failed to load customers", err);
      }
    });
  }

  handleDeleteCustomer(customer: Customer) {
    if (confirm(`Are you sure you want to delete ${customer.firstName} ${customer.lastName}?`)) {
      this.customerService.deleteCustomer(customer.id).subscribe({
        next: () => {
          this.loadCustomers(); // Recharger la liste
          this.snackBar.open('Customer deleted successfully!', 'Close', { duration: 3000 });
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Error deleting customer.', 'Close', { duration: 3000 });
        }
      });
    }
  }
}
