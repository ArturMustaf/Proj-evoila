/*
 * vCloud Director Model for the response data when calling the corresponding spring rest controller
 */
//ToDo id in long ändern, resourceTypeID in long
export interface ResourceModel {
  id: string;
  vdcID: string;
  orgID: string;
  resourceTypeID: string;
  startDate: number;
  endDate: number;
}
