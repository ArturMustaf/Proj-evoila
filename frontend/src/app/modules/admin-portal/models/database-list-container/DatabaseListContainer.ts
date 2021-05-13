import {GenericForm} from '../questions/GenericForm';

/**
 * This is the definition of the embed form list containers
 */
export class DatabaseListContainer<T> extends GenericForm<T> {
  list: GenericForm<T>[][] = [];
  template: () => GenericForm<T>[];
  controlType = 'list-container';
  shortenFunction: (dataElement: GenericForm<string>[], itemNumber: number) => string = ((dataElement, itemNumber) => {
    return itemNumber + '';
  });

  get datalist(): GenericForm<T>[][] {
    return this.list;
  }

}
