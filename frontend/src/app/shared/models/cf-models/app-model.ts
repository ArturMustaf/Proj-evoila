export interface AppModel {
  id: string,
  arangoID: string;
  diskInMb: number;
  dt: number;
  guid: string;
  instances: number;
  memoryInMb: number;
  name: string;
  org: string;
  space: string;
}
