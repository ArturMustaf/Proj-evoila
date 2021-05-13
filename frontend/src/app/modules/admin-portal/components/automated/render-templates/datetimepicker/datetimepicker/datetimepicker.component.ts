import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {DateTimePicker} from '../../../../../models/questions/DateTimePicker';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-datetimepicker',
  templateUrl: './datetimepicker.component.html',
  styleUrls: ['./datetimepicker.component.css']
})
export class DatetimepickerComponent implements OnInit, AfterViewInit {

  @Input()
  datetimepicker: DateTimePicker;
  @Input()
  editMode: boolean;
  @Input()
  form: FormGroup;

  constructor() {
  }

  ngAfterViewInit(): void {
    if (this.datetimepicker.value) {
      this.form.get(this.datetimepicker.key).setValue(this.datetimepicker.value);
    }
  }

  ngOnInit(): void {
  }

}
