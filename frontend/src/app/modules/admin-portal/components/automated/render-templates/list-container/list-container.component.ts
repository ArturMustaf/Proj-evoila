import {Component, Input, OnInit} from '@angular/core';
import {FormArray} from '@angular/forms';
import {DatabaseListContainer} from '../../../../models/database-list-container/DatabaseListContainer';
import {DatafieldLoader} from '../../../../service/form-loader/DatafieldLoader';

@Component({
  selector: 'app-list-container',
  templateUrl: './list-container.component.html',
  styleUrls: ['./list-container.component.scss'],
  providers: [DatafieldLoader]
})
/**
 * This is the class to render child forms in form of lists
 * for example a address object can have multiple phone-numbers
 * this component renders a list and manages embedded forms
 */
export class ListContainerComponent implements OnInit {
  @Input()
  question: DatabaseListContainer<string>;
  @Input() form: FormArray;
  @Input()
  editMode: boolean;

  constructor(private datafieldLoader: DatafieldLoader) {
  }

  ngOnInit(): void {
  }

  addElement(): void {
    const template = this.question.template();

    this.question.list.push(template);
    this.form.push(this.datafieldLoader.toFormGroup(template));
  }

  remove(id: number): void {
    this.question.list.splice(id, 1);
    this.form.removeAt(id);
  }

}
