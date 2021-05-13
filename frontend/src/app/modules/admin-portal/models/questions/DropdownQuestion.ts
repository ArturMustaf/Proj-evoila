import {GenericForm} from './GenericForm';

/**
 * This is the definition of a standard dropdown question for selection
 */
export class DropdownQuestion extends GenericForm<string> {
  controlType = 'dropdown';
}
