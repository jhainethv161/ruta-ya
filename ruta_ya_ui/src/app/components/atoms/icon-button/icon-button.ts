import { Component, input } from '@angular/core';

@Component({
  selector: 'app-icon-button',
  standalone: true,
  templateUrl: './icon-button.html',
})
/**
 * @description Icon button atom that renders a borderless button wrapping any icon via ng-content
 * @export
 * @class IconButtonComponent
 */
export class IconButtonComponent {
  disabled = input<boolean>(false);
}