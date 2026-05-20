import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: []
})
export class LoginComponent {
  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
}