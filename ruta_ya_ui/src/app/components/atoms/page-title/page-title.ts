import { Component, input } from '@angular/core';

@Component({
  selector: 'app-page-title',
  standalone: true,
  templateUrl: './page-title.html',
})
export class PageTitleComponent {
  title = input.required<string>();
}
