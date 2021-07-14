export interface VdcStorageProfile {
  id: string;
  href: string;
  name: string;
  vdcID: string;
  date: number;
  enabled: string;
  units: string;
  maxLimit: string;
  storageUsedMB: string;
  iopsAllocated: string;
}
