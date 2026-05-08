import { Component, input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { LabelComponent } from '../../atoms/label/label';
import { InputComponent } from '../../atoms/input/input';

@Component({
  selector: 'app-form-field',
  standalone: true,
  imports: [LabelComponent, InputComponent],
  templateUrl: './form-field.html',
})
export class FormFieldComponent {
  id = input.required<string>();
  label = input.required<string>();
  type = input<string>('text');
  placeholder = input<string>('');
  control = input<FormControl>(new FormControl(''));
}
