import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  page = "login";

  constructor(
    private formBuilder: FormBuilder,
  ) { }

  loginForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  registerForm = this.formBuilder.group({
    name: '',
    email: '',
    password: '',
    repassword: ''
  });

  ngOnInit(): void {
  }

  getLoginForm(): void {
    this.page = "login";
  }

  getRegisterForm(): void {
    this.page = "register";
  }

  login(): void {
  }

  register(): void{
  }
}
