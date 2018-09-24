class SensorHub {
  id: number;
  name: string;
  macAddress: string;
  lastMeasurement: string;
  isConnected: boolean;

  hubStatus: HubStatus;
  hubConfiguration: HubConfiguration;
}

class HubStatus {
  heaterActive: boolean;
  sprinklerActive: boolean;
}

class HubConfiguration {

  updateIntervalSeconds: number;
  autoControl: boolean;
  minDailyCelsius: number;
  targetDailyCelsius: number;
  minNightlyCelsius: number;
  targetNightlyCelsius: number;
  regularWateringCycleSeconds: number;
  regularWateringDurationSeconds: number;
  minMoistureThreshold: number;
  triggeredWateringDurationSeconds: number;

}
