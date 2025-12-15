import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products',
  standalone: true,  // Add this if using standalone components
  imports: [CommonModule],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']  // Corrected property name
})
export class ProductsComponent implements OnInit {
  // Sample data (remove if you're only using data from the service)
  sampleProducts = [
    { id: 1, name: "Computer", price: 2300, selected: true },
    { id: 2, name: "Printer", price: 1200, selected: false },
    { id: 3, name: "Smart Phone", price: 1100, selected: true }
  ];

  public products: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.fetchAllProducts().subscribe({
      next: (data) => {
        console.log(data);
        this.products = data;
      },
      error: (error) => {
        console.error('Error fetching products:', error);
        // Fallback to sample data if API fails (optional)
        this.products = this.sampleProducts;
      }
    });
  }

  deleteProduct(id: number): void {
    const res = confirm('Are you sure you want to delete this product?');
    if (res) {
      this.productService.deleteProduct(id).subscribe({
        next: (response) => {
          console.log('Product deleted successfully:', response);
          this.products = this.products.filter(product => product.id !== id);
        },
        error: (error) => {
          console.error('Error deleting product:', error);
        }
      });
    }
  }
}
