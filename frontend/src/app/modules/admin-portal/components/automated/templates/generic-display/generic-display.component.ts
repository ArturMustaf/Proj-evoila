import {Component, Input, OnInit} from '@angular/core';
import {DatafieldLoader} from '../../../../service/form-loader/DatafieldLoader';

import {FormGroup} from '@angular/forms';
import {GenericForm} from '../../../../models/questions/GenericForm';
import {RequestServiceService} from '../../../../service/shared/request-service.service';
import {URLInterface} from '../../../../service/form-loader/FormLoader';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-generic-display',
  templateUrl: './generic-display.component.html',
  styleUrls: ['./generic-display.component.scss'],
  providers: [DatafieldLoader]
})
/**
 * This is the main component to display the embedded form. Here are the informations retrieved from the embedded dynamically created form
 */
export class GenericDisplayComponent implements OnInit {
  @Input() editMode: boolean;
  @Input() questions: GenericForm<string>[] = [];
  @Input()
  urlInterface: URLInterface;
  @Input()
  id: string;
  form: FormGroup;
  payLoad = '';

  constructor(private qcs: DatafieldLoader, private requestService: RequestServiceService, private activeRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.qcs.toFormGroup(this.questions);
  }

  async onSubmit(): Promise<void> {
    if (this.id !== undefined) {
      const savedData = await this.requestService.loadId(this.urlInterface.commitUrl, this.id);
      const linked = this.qcs.toLinkedJson({}, this.questions);
      const data: any = {};
      this.mergeObjects(data, savedData);
      this.mergeObjects(data, linked);
      const raw = this.form.getRawValue();
      this.mergeObjects(data, raw);
      data.id = this.id;
      this.payLoad = JSON.stringify(data);
      const response: any = await this.requestService.putData(this.urlInterface.commitUrl, data.id, data);
      await this.router.navigate([], {relativeTo: this.activeRoute});
    } else {
      const data: any = {};
      const linked = this.qcs.toLinkedJson({}, this.questions);
      this.mergeObjects(data, linked);
      this.mergeObjects(data, this.form.getRawValue());

      this.payLoad = JSON.stringify(data);
      const response: any = await this.requestService.postData(this.urlInterface.commitUrl, data);
      await this.router.navigate([response.id], {relativeTo: this.activeRoute});

    }

  }

  mergeObjects(target: object, obj: object): any {
    if (!target) {
      return obj;
    }
    if (!obj) {
      return target;
    }
    for (const key of Object.keys(obj)) {
      // remove hatos _links for json
      if (key === '_links') {
        continue;
      }
      // check if object
      if (typeof obj[key] === 'object' && !Array.isArray(obj[key]) && obj[key] !== null) {
        // merge two objects with same keys
        if (target.hasOwnProperty(key)) {
          this.mergeObjects(target[key], obj[key]);
        } else {
          target[key] = obj[key];
        }
      } else if (Array.isArray(obj[key])) {
        if (target.hasOwnProperty(key) && Array.isArray(target[key])) {
          const objArray: Array<any> = obj[key];
          const targetArray: Array<any> = target[key];
          for (let i = 0; i < obj[key].length && i < targetArray.length; i++) {
            this.mergeObjects(target[key][i], obj[key][i]);
          }
          if (targetArray.length > objArray.length) {
            for (let i = objArray.length; i < targetArray.length; i++) {
              targetArray.pop();
            }
          } else if (targetArray.length < objArray.length) {
            for (let i = targetArray.length; i < objArray.length; i++) {
              targetArray.push(this.mergeObjects({}, objArray[i]));
            }
          }
        } else {
          const copy = [];
          for (const val of obj[key]) {
            copy.push(this.mergeObjects({}, val));
          }
          target[key] = copy;
        }
      } else {
        target[key] = obj[key];
      }
    }

    return target;
  }


}
