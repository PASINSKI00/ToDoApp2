import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  address = "http://localhost:8080";

  constructor(
    private http: HttpClient
  ) { }

  login(form: FormGroup) : Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Basic ' + btoa(form.value.username + ':' + form.value.password)});
    return this.http.get(this.address + '/api/v1/login', {observe: 'response' ,headers, responseType: 'text' as 'json'});
  }

  register(form: FormGroup) :Observable<any> {
    console.log(form.value);
    return this.http.post(this.address + '/api/v1/register', form.value, {observe: 'response' ,responseType: 'text' as 'json'}); 
  }
}
