import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {GenericDatabaseForm} from '../../../../models/database-form-container/GenericDatabaseForm';
import {FormGroup} from '@angular/forms';
import {SearchableGridComponent} from '../../linking-table/searchable-grid/searchable-grid.component';

@Component({
  selector: 'app-popup-grid',
  templateUrl: './popup-grid.component.html',
  styleUrls: ['./popup-grid.component.css']
})
/**
 * This component is used for linkable fields like: Country => not every user should have his own distinct country object in the database
 * this needs the implementation of a grid for the element to link
 */
export class PopupGridComponent implements OnInit {
  @Input()
  question: GenericDatabaseForm<string>;
  @Input() form: FormGroup;
  @Output()
  emitter = new EventEmitter<any>();
  gridComponent: SearchableGridComponent;

  constructor() {
  }

  @ViewChild(SearchableGridComponent)
  set searchableComponent(component: SearchableGridComponent) {
    this.gridComponent = component;
  }

  ngOnInit(): void {
  }

  onClose(): void {
    this.emitter.emit(null);
  }

  onOk(): void {
    console.log(this.gridComponent.selectedData);
    this.emitter.emit(this.gridComponent.selectedData);
  }


}
