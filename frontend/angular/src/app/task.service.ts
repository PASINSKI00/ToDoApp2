import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  address = 'http://localhost:8080';

  constructor(
    private http: HttpClient
  ) { }

  getTasksByCategory(id: number): Observable<any> {
    const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
    return this.http.get(this.address + '/api/v1/task', {headers, params: {categoryId: id.toString()}});
  }

  getPlannedTasks(): Observable<any> {
    const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
    return this.http.get(this.address + '/api/v1/task/planned', {headers});
  }

  addTask(task: any): Observable<any> {
    const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
    return this.http.post(this.address + '/api/v1/task', task, {headers});
  }

  updateTask(task: any): Observable<any> {
    const headers = new HttpHeaders({Authorization: localStorage.getItem('authHeader') ?? ''});
    return this.http.put(this.address + '/api/v1/task', task, {headers});
  }
}
