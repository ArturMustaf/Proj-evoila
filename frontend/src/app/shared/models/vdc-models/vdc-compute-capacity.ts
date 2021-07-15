export interface VdcComputeCapacity {
  id: number;
  cpuUnits: string;
  cpuLimit: number;
  cpuAllocated: number;
  cpuReserved: number;
  cpuUsed: number;
  memoryUnits: string;
  memoryLimit: number;
  memoryAllocated: number;
  memoryReserved: number;
  memoryUsed: number;
  date: number;
  vdcID: string;
}
