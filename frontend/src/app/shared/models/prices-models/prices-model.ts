/*
 * vCloud Director Model for the response data when calling the corresponding spring rest controller
 */
export  interface PricesModel {
  id: number;
  startDate: number;
  endDate: number;
  resourceID: number;
  resourceSize: string;
  priceInCent: number;
  vdcID: string;
  orgID: string;
}
