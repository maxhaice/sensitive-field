import {AfterViewInit, Component} from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'root-app',
  templateUrl: 'root.component.html',
  styleUrls: ['root.component.css']
})

export class RootComponent implements  AfterViewInit{
  public authority: string;
  public hide = false;
  constructor(private tokenStorage: TokenStorageService, private router: Router) {
    this.router.events.pipe(filter(event => event instanceof NavigationStart)).subscribe(value => {
      this.hide = (value as NavigationStart).url === '/situation'
                  || (value as NavigationStart).url === '/statistic'
                  || (value as NavigationStart).url.split(';')[0] === '/situation'
                  || (value as NavigationStart).url === '/history'
                  || (value as NavigationStart).url === '/sensors'
                  || (value as NavigationStart).url === '/settings';
    });
  }

  async ngAfterViewInit(): Promise<void> {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
    }
    else {
      await this.router.navigate(['']);
    }
  }
}
