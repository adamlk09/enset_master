import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../../core/models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl = 'http://localhost:8084/api';

  constructor(private http: HttpClient) {
  }

  // Recherche paginée de clients
  searchCustomers(keyword: string, page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(`${this.baseUrl}/customers/search`, {params});
  }

  // Obtenir tous les clients
  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.baseUrl}/customers`);
  }

  // Supprimer un client
  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/customers/${id}`);
  }
}
