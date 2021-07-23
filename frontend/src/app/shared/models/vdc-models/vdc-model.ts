/*
 * vCloud Director Model for the response data when calling the corresponding spring rest controller
 */

export interface VdcModel {
  id: string;
  name: string;
  href: string;
  tasks: string;
  allocationModel: string;
  orgID: string;
}
