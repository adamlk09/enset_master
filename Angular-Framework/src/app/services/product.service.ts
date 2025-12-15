import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url = 'http://localhost:8181/products';
  constructor(private http : HttpClient) {

   }

   fetchAllProducts():Observable<any>{
    return this.http.get(this.url);
   }

   deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
   }
}
