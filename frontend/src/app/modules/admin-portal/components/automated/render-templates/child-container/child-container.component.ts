import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {GenericDatabaseForm} from '../../../../models/database-form-container/GenericDatabaseForm';
import {FormGroup} from '@angular/forms';
import {PopupGridComponent} from '../popup-grid/popup-grid.component';
import {DatafieldLoader} from '../../../../service/form-loader/DatafieldLoader';


@Component({
  selector: 'app-child-container',
  templateUrl: './child-container.component.html',
  styleUrls: ['./child-container.component.css']
})
/**
 * Renders a container component in the forms. This is especially used for nested forms. For example Customer has a address attribute
 * the address attribute is in this case a app-child-container and renders like a own form embedded into the master view.
 */
export class ChildContainerComponent implements OnInit {
  @Input()
  question: GenericDatabaseForm<string>;
  @Input() form: FormGroup;
  @Input()
  editMode: boolean;
  selectTrigger = false;
  gridComponent: PopupGridComponent;

  constructor(private datafieldLoader: DatafieldLoader) {
  }

  @ViewChild(PopupGridComponent)
  set searchableComponent(component: PopupGridComponent) {
    this.gridComponent = component;
  }

  ngOnInit(): void {
  }

  onLink(): void {
    this.selectTrigger = !this.selectTrigger;
  }

  onSelect(event: any): void {
    if (event !== null) {
      this.question.linkedData = event;
      this.datafieldLoader.fromJSON(this.question.linkedData, this.question.children);
    }
    this.selectTrigger = false;
  }

  onUnlink(): void {
    this.question.linkedData = undefined;
  }
}
