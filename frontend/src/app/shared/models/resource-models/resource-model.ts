/*
 * vCloud Director Model for the response data when calling the corresponding spring rest controller
 */
export interface ResourceModel {
  id: number;
  vdcID: string;
  orgID: string;
  resourceTypeID: number;
  startDate: number;
  endDate: number;
}
