import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.css']
})
export class Viewer {
  bgClass = 'default';
  private currentUrl = -1;
  private urlTree = [
    '/',
    '/home/(news)',
    '/home/(schedule)',
    '/home/(users)',
    '/home/(birthday)',
    '/home/(logger)',
    '/home/(system)',
  ];
  constructor(
    private router: Router,
  ) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = this.urlTree.indexOf(event.url);
        const eventUrl = /(?<=\/).+/.exec(event.urlAfterRedirects);
        const current = (eventUrl || []).join('');
        this.bgClass = this.bgClass ==  "pagePop" ? "pagePop1": "pagePop";
      }
    });

  }
  public toPrevious(){
    this.router.navigateByUrl(this.urlTree[this.currentUrl-1]!=undefined?this.urlTree[this.currentUrl-1]:"/");
  }
  public toNext(){
    this.router.navigateByUrl(this.urlTree[this.currentUrl+1]!=undefined?this.urlTree[this.currentUrl+1]:"/");
  }
}
