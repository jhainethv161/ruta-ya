import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './components/molecules/navbar/navbar';
import { SidebarComponent } from './components/organisms/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Navbar, SidebarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}
