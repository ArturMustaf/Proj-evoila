import {Injectable} from '@angular/core';
import {GenericForm} from '../../models/questions/GenericForm';
import {TextBoxQuestion} from '../../models/questions/TextBoxQuestion';
import {PopUpQuestion} from '../../models/database-form-container/PopUpQuestion';
import {DatabaseListContainer} from '../../models/database-list-container/DatabaseListContainer';
import {DatafieldLoader} from './DatafieldLoader';
import {DropdownQuestion} from '../../models/questions/DropdownQuestion';
import {SearchableGridForm} from '../../models/database-form-container/SearchableGridForm';
import {DateTimePicker} from '../../models/questions/DateTimePicker';

@Injectable()
/**
 * This class has all definitions of all dynamic grids
 */
export class FormLoader {

  forms = new Map<string, GenericForm<string>[]>();
  public urlMap = new Map<string, URLInterface>();

  constructor(private datafieldLoader: DatafieldLoader) {
    this.forms.set('customers', FormLoader.setUpCustomerForm());
    this.forms.set('contactInformations', FormLoader.setUpContactInformation());
    this.forms.set('projects', FormLoader.setUpProject());
    this.forms.set('resourcetypes', FormLoader.setUpResourceType());

    this.urlMap.set('customers', {
      searchUrl: '/search/customers',
      commitUrl: '/create/customers/'
    });
    this.urlMap.set('contactInformations', {
      searchUrl: '/search/contact_information',
      commitUrl: '/create/contactInformations/'
    });
    this.urlMap.set('projects', {
      commitUrl: '/create/projects/',
      searchUrl: '/search/projects'
    });
    this.urlMap.set('resourcetypes', {
      commitUrl: '/create/resourceType/',
      searchUrl: '/search/resourcetypes'
    });
  }


  static setUpAddress(): Array<GenericForm<string>> {
    const arr = [];
    arr.push(new TextBoxQuestion({key: 'address', label: 'Straße'}));
    arr.push(new TextBoxQuestion({key: 'plz', label: 'Postleitzahl'}));
    arr.push(new TextBoxQuestion({key: 'city', label: 'Ort'}));
    arr.push(new DropdownQuestion({
      key: 'addressType', label: 'Adresstyp', options: [{value: 'Kontaktadresse', key: 'CONTACT_ADDRESS'},
        {key: 'BILLING_ADDRESS', value: 'Rechnungsadresse'}]
    }));
    // TODO add Country
    arr.push(new SearchableGridForm({gridName: 'projects'}));

    return arr;
  }

  static setUpPhoneNumber(): Array<GenericForm<string>> {
    const arr = [];
    arr.push(new TextBoxQuestion({label: 'Nummer', key: 'number', required: true}));
    arr.push(new DropdownQuestion({
      key: 'type', label: 'Art der Telefonnummer', required: true, options: [
        {key: 'MOBILE', value: 'Mobil'},
        {key: 'PHONE', value: 'Festnetz'}]
    }));
    return arr;
  }

  static setUpContactInformationType(): Array<GenericForm<string>> {
    const arr = [];
    arr.push(new DropdownQuestion({
      label: 'Kontakttyp', key: 'type', required: true,
      options: [
        {key: 'MAIN_CONTACT', value: 'Hauptkontakt'}
        , {key: 'EMERGENCY_CONTACT', value: 'Notfallkontakt'}]
    }));
    return arr;
  }

  static setUpContactInformation(): Array<GenericForm<string>> {
    const arr = [];
    const phoneNumbers = new DatabaseListContainer({label: 'Telefonnummern', key: 'numbers'});
    phoneNumbers.shortenFunction = (dataElement, itemNumber) => {
      const dropdown = dataElement[1].options.find((elm => elm.key === dataElement[1].value))?.value;
      if (!dataElement[0].value) {
        return 'Neues Element';
      }
      return dropdown + ': ' + dataElement[0].value;
    };
    phoneNumbers.template = this.setUpPhoneNumber;
    arr.push(new TextBoxQuestion(
      {label: 'Emailadresse', key: 'email', required: true}));
    arr.push(...this.setUpContactInformationType());
    arr.push(new PopUpQuestion({label: 'Adresse', key: 'address', children: this.setUpAddress()}));

    arr.push(phoneNumbers);
    return arr;

  }

  static setUpCustomerForm(): Array<GenericForm<string>> {
    const arr = [];
    arr.push(new TextBoxQuestion({label: 'Vorname', key: 'name', required: true}));
    arr.push(new TextBoxQuestion({label: 'Nachname', key: 'surname', required: true}));
    arr.push(new TextBoxQuestion({label: 'Keycloak-Id', key: 'userId', editAllowed: false, createAllowed: false}));
    const popup = new PopUpQuestion({
      label: 'Kontaktinformationen', key: 'contactInformation', children: this.setUpContactInformation()
    });
    popup.genericLinkTable = 'contactInformations';
    arr.push(popup);

    // const projects = new DatabaseListContainer({label: 'Projekte', key: 'projects'});
    // projects.template = this.setUpProject;
    //

    return arr;


  }


  static setUpProject(): Array<GenericForm<string>> {
    const arr = [];
    arr.push(new TextBoxQuestion({key: 'name', label: 'Projektname', required: true}));
    arr.push(new TextBoxQuestion({key: 'description', label: 'Beschreibung', required: true}));
    const logList = new DatabaseListContainer({key: 'logs', label: 'Ressourcenlogs'});

    logList.template = FormLoader.setUpResourceLog;
    arr.push(logList);

    return arr;
  }


  static setUpResourceLog(): GenericForm<string>[] {
    const arr = [];
    arr.push(new TextBoxQuestion({key: 'id', label: 'ID der Ressource', editAllowed: false}));
    const popupQuestion = new PopUpQuestion({
      key: 'type',
      label: 'Ressourcentyp',
      selectAllowed: true,
      children: FormLoader.setUpResourceType()
    });
    popupQuestion.genericLinkTable = 'resourceTypes';
    arr.push(popupQuestion);
    arr.push(new TextBoxQuestion({key: 'quantity', label: 'Menge', editAllowed: true}));


    return arr;
  }

  static setUpResourceType(): GenericForm<string>[] {
    const arr = [];
    arr.push(new TextBoxQuestion({key: 'type', label: 'Typ', editAllowed: false, required: true}));
    arr.push(new TextBoxQuestion({key: 'shortDescription', editAllowed: false, label: 'Kurzbeschreibung'}));
    arr.push(new TextBoxQuestion({key: 'fullDescription', editAllowed: false, label: 'Beschreibung'}));
    const databaseList = new DatabaseListContainer({key: 'price', label: 'Preisverlauf'});
    databaseList.template = FormLoader.setUpPrice;
    arr.push(databaseList);
    return arr;
  }

  static setupEvent(): GenericForm<string>[] {
    const arr = [];

    return arr;
  }

  static setUpPrice(): GenericForm<string>[] {
    const arr = [];
    arr.push(new TextBoxQuestion({key: 'price', label: 'Preis (in Cent)'}));
    arr.push(new DropdownQuestion({
      label: 'Abrechnungszeiteinheit', key: 'priceType', options:
        [{value: 'pro Minute', key: 'MINUTE_PRICE'}, {value: 'pro Stunde', key: 'HOURLY_PRICE'}, {value: 'pro Tag', key: 'DAILY_PRICE'},
          {value: 'pro Woche', key: 'WEEKLY_PRICE'}, {value: 'pro Monat', key: 'MONTHLY_PRICE'}]
    }));
    arr.push(new DateTimePicker({key: 'created', label: 'Gültig von'}));
    arr.push(new DateTimePicker({key: 'expireDate', label: 'Gültig bis'}));
    return arr;
  }




}

export interface URLInterface {
  searchUrl: string;
  commitUrl: string;
}
