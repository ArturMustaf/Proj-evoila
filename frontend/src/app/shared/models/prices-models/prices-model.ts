//ToDo id und resourceID in long ändern
export  interface PricesModel {
  id: string;
  startDate: number;
  endDate: number;
  resourceID: string;
  resourceSize: string;
  priceInCent: number;
}
