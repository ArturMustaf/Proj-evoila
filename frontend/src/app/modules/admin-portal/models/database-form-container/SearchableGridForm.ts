import {GenericForm} from '../questions/GenericForm';

export class SearchableGridForm extends GenericForm<string> {
  gridName: string;
  showSearchField: boolean;
  controlType = 'searchgrid';

  constructor(props: Partial<SearchableGridForm>) {
    super();
    Object.assign(this, props);
  }

}
