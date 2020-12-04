import {Injectable} from '@angular/core';

@Injectable()
export class DateAndTimeService{
  public formatLabel(value: number): string {
    return ((Math.floor(value / 60) < 10
        ? '0' + Math.floor(value / 60) : Math.floor(value / 60)) +
        ':' + (value % 60 < 10 ? '0' + (value % 60) : value % 60));
  }
  public getQueryDate(d: Date): string{
    return (d.getDate() < 10 ? '0' + d.getDate() : d.getDate()) + '.'
      + ((d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1))
      + '.' + d.getFullYear() + ' ' + d.toLocaleTimeString();
  }
  public today(): Date {
    return new Date();
  }
  public date(d: number, m: number): string {
    return ((d < 10 ? '0' + d : d) +
            '.' +
            (m < 10 ? '0' + m : m));
  }
}
