/**
 * This is the defention which is needed to create a searchable grid these grids are used in the admin portal to search through all entries
 *
 */
export class SearchAbleGrid {
  /**
   * These are the string names which should be shown in the ui
   */
  fields: string[];
  /**
   * these are the field keys which are given through the search api. The field keys can be nested through "." so address.city could be a viable identifier to
   * access the city of a customer object
   */
  fieldKeys: Array<string | CalculationAfterField>;
  /**
   * this is the backend url where the elastic search query is performed
   */
  searchUrl: string;
  /**
   * this is the api url where the backend is. This url is used to load complete objects out of the database
   */
  apiUrl: string;
  /**
   * Name of the search gird
   */
  formName: string;
  /**
   *
   */
  formKeyName: string;
  /**
   * on element selected => navigate to this sub path
   */
  redirectUrl: string[];

  callbackUrl: string;


  constructor(partital: Partial<SearchAbleGrid>) {
    Object.assign(this, partital);
  }


}

export interface CalculationAfterField {
  key: string;
  calculationFunction: (value: any) => string;
}

