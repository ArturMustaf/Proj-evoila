import {Component, Directive, Injectable, Pipe} from '@angular/core';

import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {GenericForm} from '../../models/questions/GenericForm';
import {GenericDatabaseForm} from '../../models/database-form-container/GenericDatabaseForm';
import {DatabaseListContainer} from '../../models/database-list-container/DatabaseListContainer';



@Injectable()
/**
 * This class is for generating form groups for angular to display out of the custom data structure
 * and vise versa
 * This class also merges jsons which are loaded prior from a old version and the changes into a new one which then can be used to update
 * the database
 * ToLinkedJson uses sub elements which are linked to keep the link to the old ids
 */

export class DatafieldLoader {

  toFormGroup(formElements: GenericForm<string>[]): FormGroup {
    const group: any = {};
    formElements.forEach(question => {
      if (question instanceof GenericDatabaseForm) {
        group[question.key] = this.toFormGroup(question.children);
        group[question.key].setValidators([...question.validators]);
      } else if (question instanceof DatabaseListContainer) {
        group[question.key] = new FormArray([], [...question.validators]);
        for (const elm of question.datalist) {
          group[question.key].push(this.toFormGroup([...elm]));
        }
      } else {
        if (question.required) {
          group[question.key] = new FormControl(question.value || '', [Validators.required, ...question.validators]);
        } else {
          group[question.key] = new FormControl(question.value || '', [...question.validators]);
        }
      }


    });
    return new FormGroup(group);
  }

  fromJSON(obj: object, formElements: GenericForm<string>[]): void {
    for (const formElement of formElements) {
      if (formElement instanceof DatabaseListContainer) {
        if (obj.hasOwnProperty(formElement.key)) {
          const length = obj[formElement.key].length;
          for (let i = 0; i < length; i++) {
            formElement.list.push(formElement.template());
            this.fromJSON(obj[formElement.key][i], formElement.list[i]);
          }
        }
      } else if (formElement instanceof GenericDatabaseForm) {
        if (obj.hasOwnProperty(formElement.key)) {
          this.fromJSON(obj[formElement.key], formElement.children);
        }
      } else if (obj && obj.hasOwnProperty(formElement.key)) {
        formElement.value = obj[formElement.key];
      }
    }
  }

  toLinkedJson(obj: object, formElements: GenericForm<string>[]): object {
    for (const formElement of formElements) {
      if (formElement instanceof DatabaseListContainer) {
        obj[formElement.key] = [];
        for (const template of formElement.list) {
          obj[formElement.key].push(this.toLinkedJson({}, template));
        }
      } else if (formElement instanceof GenericDatabaseForm) {
        if (formElement.linkedData) {
          obj[formElement.key] = formElement.linkedData;
        } else {
          obj[formElement.key] = this.toLinkedJson({}, formElement.children);
        }
      } else {
        obj[formElement.key] = {};
      }
    }

    return obj;
  }


}

