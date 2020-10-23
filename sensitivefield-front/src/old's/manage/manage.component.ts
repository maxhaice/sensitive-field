import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'manage',
  templateUrl: 'manage.component.html',
  styleUrls: ['manage.component.css']
})
export class Manage {
  bgClass = 'default';
  constructor(
    private router: Router,
  ) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const eventUrl = /(?<=\/).+/.exec(event.urlAfterRedirects);
        const current = (eventUrl || []).join('');
        this.bgClass = this.bgClass ==  "pagePop" ? "pagePop1": "pagePop";
      }
    });

  }
}
