import {AfterViewInit, Component, Input} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {GenericForm} from '../../../../models/questions/GenericForm';
import {GenericDatabaseForm} from '../../../../models/database-form-container/GenericDatabaseForm';
import {DatabaseListContainer} from '../../../../models/database-list-container/DatabaseListContainer';


@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html'
})
/**
 * This is the component to render basic form / inputs of the embedded states
 * This component uses recursion of objects to display dynamic child components
 */
export class GenericFormComponent implements AfterViewInit {
  @Input() editMode: boolean;
  @Input() question: GenericForm<string>;
  @Input() form: FormGroup;
  dateValue: Date;


  constructor() {


  }

  onDateChange(event: Date): void {
    if (!this.form.contains(this.question.key)) {
      this.form.addControl(this.question.key, new FormControl(this.question.value, [...this.question.validators]));
    }
    console.dir(event);
    const obj = {};
    const field: string = this.question.key;
    obj[field] = event.toISOString();
    this.form.patchValue(obj);
  }

  get isValid(): boolean {
    if (this.question instanceof GenericDatabaseForm || this.question instanceof DatabaseListContainer) {
      return true;
    }
    return this.form.controls[this.question.key].valid;
  }

  dropDownToString(question: GenericForm<string>, key: string): string {
    return question.options.find(elm => elm.key === key)?.value;
  }

  ngAfterViewInit(): void {
    if (this.question.type === 'datetimepicker') {
      this.dateValue = new Date(this.question.value);
    }
    if (this.question.value) {
      this.form.get(this.question.key).setValue(this.question.value);
    }
  }

  onLink(): void {

  }
}
