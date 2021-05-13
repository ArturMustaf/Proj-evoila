import {ValidatorFn} from '@angular/forms';

/**
 * This is the basic form definition to allow the dynamic creation
 */
export abstract class GenericForm<T> {
  value: T;
  key: string;
  label: string;
  required: boolean;
  validators: ValidatorFn[];
  order: number;
  controlType: string;
  type: string;
  options: { key: string, value: string }[];
  selectAllowed: boolean;
  createAllowed: boolean;
  editAllowed: boolean;
  children: GenericForm<string>[];

  constructor(options: Partial<GenericForm<T>> = {}) {
    this.value = options.value;
    this.key = options.key || '';
    this.label = options.label || '';
    this.required = !!options.required;
    this.order = options.order === undefined ? 1 : options.order;
    this.controlType = options.controlType || '';
    this.type = options.type || '';
    this.validators = options.validators || [];
    this.options = options.options || [];
    this.children = options.children || [];
    this.selectAllowed = options.selectAllowed || false;
    this.createAllowed = options.createAllowed || true;
    this.editAllowed = options.editAllowed || true;
  }
}

