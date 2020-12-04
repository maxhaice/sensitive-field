export interface AudioEvent {
    id: number,
    sensor_id: number,
    latitude: number,
    longitude: number,
    dateServer: string,
    dateReal: string,
    typeSource1: string,
    persistenceSource1: number,
    typeSource2: string,
    persistenceSource2: number,
    persistenceSource3: number,
    typeSource3: string,
    isDeleted: boolean,
    kindEvent: string,
    longitudeSensor: number,
    latitudeSensor: number
  }
