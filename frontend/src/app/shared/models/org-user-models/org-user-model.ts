/*
 * vCloud Director Model for the response data when calling the corresponding spring rest controller
 */
export interface OrgUserModel {
  id: string;
  href: string;
  fullName: string;
  isEnabled: string;
  isLocked: string;
  name: string;
  emailAddress: string;
  telephone: string;
  isExternal: string;
  isGroupRole: string;
  orgRoleID: string;
  orgID: string;
  userID: string;
}
