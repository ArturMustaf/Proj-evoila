import {Component, OnInit} from '@angular/core';
import {DatafieldLoader} from '../../service/form-loader/DatafieldLoader';
import {from, Observable} from 'rxjs';

import {NavService} from '../../../../core/services/nav/nav.service';
import {GenericForm} from '../../models/questions/GenericForm';
import {FormLoader, URLInterface} from '../../service/form-loader/FormLoader';
import {ActivatedRoute} from '@angular/router';
import {RequestServiceService} from '../../service/shared/request-service.service';

@Component({
  selector: 'app-form-database',
  templateUrl: './form-database.component.html',
  styleUrls: ['./form-database.component.scss'],
  providers: [DatafieldLoader, FormLoader]
})
/**
 * this component shows the dynamically generated form from the definitions. The form is decided from the url parameter
 * This component laos allows the creating / modifying of a database object
 * After creation of a new database object the user gets routed to the newly created object and is in edit-mode
 */
export class FormDatabaseComponent implements OnInit {
  questions$: Observable<Array<GenericForm<string>>>;
  urlInterface: URLInterface;
  editMode = false;
  id: string;
  loaded = false;

  constructor(service: DatafieldLoader,
              nav: NavService,
              private formLoader: FormLoader,
              private route: ActivatedRoute,
              private requestService: RequestServiceService) {
    route.params.subscribe(params => {
      const questions = this.formLoader.forms.get(params.path);
      const urlInterface = formLoader.urlMap.get(params.path);
      if (params.id) {
        this.id = params.id;
        requestService.loadId(urlInterface.commitUrl, params.id).then(res => {
          service.fromJSON(res, questions);
          this.questions$ = from([questions]);
          this.urlInterface = urlInterface;
          this.loaded = true;
        });

      } else {
        this.questions$ = from([questions]);
        this.urlInterface = urlInterface;
        this.loaded = true;
      }
    });
    nav.isAdmin = true;
  }

  editTrigger(): void {
    this.editMode = !this.editMode;
  }

  ngOnInit(): void {
  }

}
