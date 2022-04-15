import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  page = "login";
  message: string = "";
  color: string = "red";

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) { }

  loginForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  registerForm = this.formBuilder.group({
    name: '',
    email: '',
    password: '',
    rePassword: ''
  });

  ngOnInit(): void {
  }

  getLoginForm(): void {
    this.page = "login";
    this.message = "";
  }

  getRegisterForm(): void {
    this.page = "register";
    this.message = "";
  }

  login(): void {
    this.loginService.login(this.loginForm).subscribe(
      response => {
        this.color = "green";
        this.message = "Login successful. You'll be redirected";
        localStorage.setItem('authHeader', 'Basic ' + btoa(this.loginForm.value.username + ':' + this.loginForm.value.password));
        localStorage.setItem('userId', response.body);
        setTimeout(() => {
          this.router.navigate(['/todo']);
        } , 3000);
      },
      error => {
        this.color = "red";
        this.message = "Wrong username or password";
      }
    );
  }

  register(): void{
    if(this.registerForm.value.name.length === 0){
      this.message = "Name must not be empty";
      return;
    }

    if(!this.registerForm.value.email.match(/^\S+@\S+\.\S+$/)){
      this.message = "Email must be valid";
      return;
    }
    
    if(!this.registerForm.value.password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)){
      this.message = "Invalid Password";
      return;
    }

    if(this.registerForm.value.password !== this.registerForm.value.rePassword){
      this.message = "Passwords must match";
      return;
    }
     

    this.loginService.register(this.registerForm).subscribe(
      response =>{
        this.color = "green";
        this.message = "Registration successful. Please Log In";
      }, 
      error => {
        this.color = "red";
        this.message = error.error;
        console.log(error);
      }
    );
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
}
}
