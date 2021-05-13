import {GenericForm} from './GenericForm';

/**
 * Basic Input text question
 */
export class TextBoxQuestion extends GenericForm<string> {
  controlType = 'textbox';
}
