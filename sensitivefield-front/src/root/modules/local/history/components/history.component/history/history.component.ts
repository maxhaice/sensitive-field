import { OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, Observable, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {HTTPService} from '../../../services/http.service';
@Component({
  selector: 'history',
  templateUrl: 'history.component.html',
  styleUrls: ['history.component.css'],
  providers: [HTTPService]
})
export class HistoryRout{
//   events: Event[];
//   authority: string;
//   date = new Date();
//   dateEnd: string;
//   dateStart: string;
//   constructor(private tokenStorage: TokenStorageService, private router: Router, private http: HTTPService) {
//   }
//  ngOnInit(): void {
//    if (this.tokenStorage.getToken()) {
//      this.authority = this.tokenStorage.getAuthorities()[0];
//      const day = (this.date.getDate() - (this.authority === 'user' ? 2 : 0));
//      this.dateEnd = this.date.getFullYear() + '-' + (this.date.getMonth() + 1 ) + '-' + day;
//      // this.dateStart = this.date.toISOString().split('T')[0];
//    } else {
//      this.router.navigate(['']);
//    }
//  }

//   getHistoryByDate(): void {
//     const date = this.dateEnd.split('-')[0] + '-' + this.dateEnd.split('-')[1] + '-' + this.dateEnd.split('-')[2].replace('0', '');
//     this.http.get<Event[]>('http://localhost:8080/api/history?date=' + date).subscribe(done => {
//       this.events = done;
//     }, er => {
//       FireSound.execute();
//     });
//   }
// }
displayedColumns: string[] = ['created', 'state', 'number', 'title'];
exampleDatabase: ExampleHttpDatabase | null;
data: GithubIssue[] = [];

resultsLength = 0;
isLoadingResults = true;
isRateLimitReached = false;

@ViewChild(MatPaginator) paginator: MatPaginator;
@ViewChild(MatSort) sort: MatSort;

constructor(private _httpClient: HttpClient) {}

ngAfterViewInit() {
  this.exampleDatabase = new ExampleHttpDatabase(this._httpClient);

  // If the user changes the sort order, reset back to the first page.
  this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

  merge(this.sort.sortChange, this.paginator.page)
    .pipe(
      startWith({}),
      switchMap(() => {
        this.isLoadingResults = true;
        return this.exampleDatabase!.getRepoIssues(
          this.sort.active, this.sort.direction, this.paginator.pageIndex);
      }),
      map(data => {
        // Flip flag to show that loading has finished.
        this.isLoadingResults = false;
        this.isRateLimitReached = false;
        this.resultsLength = data.total_count;

        return data.items;
      }),
      catchError(() => {
        this.isLoadingResults = false;
        // Catch if the GitHub API has reached its rate limit. Return empty data.
        this.isRateLimitReached = true;
        return observableOf([]);
      })
    ).subscribe(data => this.data = data);
}
}

export interface GithubApi {
items: GithubIssue[];
total_count: number;
}

export interface GithubIssue {
created_at: string;
number: string;
state: string;
title: string;
}

/** An example database that the data source uses to retrieve data for the table. */
export class ExampleHttpDatabase {
constructor(private _httpClient: HttpClient) {}

getRepoIssues(sort: string, order: string, page: number): Observable<GithubApi> {
  const href = 'https://api.github.com/search/issues';
  const requestUrl =
      `${href}?q=repo:angular/components&sort=${sort}&order=${order}&page=${page + 1}`;

  return this._httpClient.get<GithubApi>(requestUrl);
}
}
