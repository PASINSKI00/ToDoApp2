import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  address = 'http://localhost:8080';

  constructor(
    private http: HttpClient
  ) { }

  getCategories(): Observable<any> {
      const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
      return this.http.get(this.address + '/api/v1/category', {headers});
  }

  addCategory(name: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
    return this.http.post(this.address + '/api/v1/category',name, {headers});
  }
}
