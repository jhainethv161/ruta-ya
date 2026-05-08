import { Component, input } from '@angular/core';

@Component({
  selector: 'app-nav-section-label',
  standalone: true,
  templateUrl: './nav-section-label.html',
})
export class NavSectionLabelComponent {
  label = input.required<string>();
}
