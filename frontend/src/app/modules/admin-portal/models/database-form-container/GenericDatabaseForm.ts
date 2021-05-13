import {GenericForm} from '../questions/GenericForm';

/**
 * Base class for all database forms
 */
export abstract class GenericDatabaseForm<T> extends GenericForm<T> {
  genericLinkTable: string;
  linkedData: any;
}
