import { AudioEvent } from 'src/root/interfaces/audio-event.interface';

export interface EventSystem{
    events: AudioEvent[],
    eventOnTouch: Event[]
}