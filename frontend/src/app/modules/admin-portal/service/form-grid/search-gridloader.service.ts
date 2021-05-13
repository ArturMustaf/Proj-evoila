import {Injectable} from '@angular/core';
import {CalculationAfterField, SearchAbleGrid} from '../../components/automated/linking-table/models/SearchAbleGrid';

@Injectable({
  providedIn: 'root'
})
// Defines the SearchGridFields through a string identifier
/**
 * Here are all searchgrids defined and how they look like
 */
export class SearchGridloaderService {
  private map: Map<string, SearchAbleGrid> = new Map<string, SearchAbleGrid>();

  private priceTypes = {
    MONTHLY_PRICE: 'Monat',
    WEEKLY_PRICE: 'Woche',
    DAILY_PRICE: 'Tag',
    HOURLY_PRICE: 'Stunde',
    MINUTE_PRICE: 'Minute'

  };

  constructor() {
    this.map.set('customers', this.customerGrid());
    this.map.set('contactInformations', this.contactInformation());
    this.map.set('projects', this.projects());
    this.map.set('resourceTypes', this.resourceTypes());
    this.map.set('logs', this.resourceLogs());
  }

  public loadSearchGrid(key: string): SearchAbleGrid {
    return this.map.get(key);
  }

  /**
   * @param obj to read key from
   * @param key key can be nested and seperated by . so address.city is a valid identifier for example
   * chain can also contain integers to access specific array fields / -1 is for last element
   */
  public getField(obj: any, key: string | CalculationAfterField): any {
    let keyValue: string;
    if (typeof (key) === 'object') {
      keyValue = key.key;
    } else {
      keyValue = key;
    }
    const splits = keyValue.split('.');
    let result = obj;
    for (const split of splits) {
      if (this.isNumeric(split)) {
        const index = parseInt(split, 10);
        if (index === -1) {
          result = result[result.length - 1];
        } else {
          result = result[index];
        }
      } else {
        if (result && result.hasOwnProperty(split)) {
          result = result[split];
        }
      }
    }
    if (typeof (key) === 'object') {
      return key.calculationFunction(result);
    }

    return result;
  }

  /**
   * Customer grid definition
   * @private
   */
  private customerGrid(): SearchAbleGrid {
    const grid: SearchAbleGrid = new SearchAbleGrid({
      fieldKeys: ['surname', 'name', 'contactInformation.email',
        'contactInformation.address.address', 'contactInformation.address.plz',
        'contactInformation.address.city', 'contactInformation.address.country.countryName'],
      formName: 'customers',
      fields: ['Nachname', 'Vorname', 'Email', 'Adresse', 'PLZ', 'Ort', 'Land'],
      searchUrl: '/search/customers',
      apiUrl: '/customers/',
      formKeyName: 'customers',
      redirectUrl: ['forms', 'customers'],
    });

    return grid;
  }

  /**
   * Contact Information definition
   * @private
   */
  private contactInformation(): SearchAbleGrid {
    const grid: SearchAbleGrid = new SearchAbleGrid({
      fieldKeys: ['email',
        'address.address', 'address.plz',
        'address.city', 'address.country.countryName'],
      formName: 'contactInformations',
      fields: ['Mail', 'Adresse', 'Postleitzahl', 'Stadt', 'Land'],
      searchUrl: '/search/contact_information',
      apiUrl: '/contactInformations/',
      formKeyName: 'contactInformations',
      redirectUrl: ['forms', 'contactInformations'],
    });

    return grid;
  }

  private resourceLogs(): SearchAbleGrid {
    const grid = new SearchAbleGrid({
      fieldKeys: ['id', 'event.eventType', 'type.type'],
      formName: 'logs',
      fields: ['Resource Id', 'Event Type', 'Resource Type'],

    });
    return grid;
  }

  /**
   * Project definition
   * @private
   */
  private projects(): SearchAbleGrid {
    const grid: SearchAbleGrid = new SearchAbleGrid({
      fieldKeys: ['name', 'description'],
      formName: 'projects',
      fields: ['Name', 'Beschreibung'],
      apiUrl: 'projects',
      searchUrl: '/search/projects',
      formKeyName: 'projects',
      redirectUrl: ['forms', 'projects']
    });
    return grid;
  }

  /**
   * resource types definition
   * @private
   */
  private resourceTypes(): SearchAbleGrid {
    const grid = new SearchAbleGrid({
      fieldKeys: ['type', 'shortDescription',
        {
          key: 'price.-1',
          calculationFunction: value => {
            console.dir(value);
            return Number.parseInt(value.price, 10) / 100 + '€ pro ' + this.priceTypes[value.priceType];
          }
        },
        {
          key: 'price.-1.created', calculationFunction: value => {
            const date = new Date(value);
            return date.toLocaleDateString();
          }
        }, {
          key: 'price.-1.expireDate', calculationFunction: value => {
            if (value == null) {
              return '';
            }
            const date = new Date(value);
            return date.toLocaleDateString();
          }
        }],
      formName: 'resourceTypes',
      fields: ['Instanztyp', 'Beschreibung', 'Preis', 'Gültig ab', 'Gültig bis'],
      apiUrl: 'resourceTypes',
      searchUrl: '/search/resourcetypes',
      formKeyName: 'resourceTypes',
      redirectUrl: ['forms', 'resourcetypes']
    });
    return grid;
  }


  /**
   *
   * @param value checks if it is numeric
   * @private
   */
  private isNumeric(value: string): boolean {
    return /^-?\d+$/.test(value);
  }
}
