import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  selector: 'root-app',
  templateUrl: 'root.component.html',
  styleUrls: ['root.component.css']
})

export class RootComponent{
  public authority: string;
  public hide: boolean = false;
  constructor(private tokenStorage: TokenStorageService, private router: Router) {
    this.router.events.pipe(filter(event => event instanceof NavigationStart)).subscribe(value=>{
      this.hide = (value as NavigationStart).url == '/situation' 
                  || (value as NavigationStart).url == '/statistic'
                  || (value as NavigationStart).url == '/history'
                  || (value as NavigationStart).url == '/sensors'
                  || (value as NavigationStart).url == '/settings';
    });
  }

  async ngAfterViewInit(): Promise<void> {
    if (this.tokenStorage.getToken()) {
      this.authority = await this.tokenStorage.getAuthorities()[0];
      if (!this.authority) {
        if (this.authority === 'ROLE_USER') {
          // TODO: if user have USER_ROLE
        }
      }
    }
    else {
      // this.router.navigate(['']);
    }
    
  }
}
