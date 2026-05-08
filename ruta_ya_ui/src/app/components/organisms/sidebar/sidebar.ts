import { Component } from '@angular/core';
import { NavSectionLabelComponent } from '../../atoms/nav-section-label/nav-section-label';
import { NavItemComponent } from '../../molecules/nav-item/nav-item';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [NavSectionLabelComponent, NavItemComponent],
  templateUrl: './sidebar.html',
})
export class SidebarComponent {}
