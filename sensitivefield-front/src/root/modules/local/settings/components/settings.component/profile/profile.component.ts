import {Component, OnInit} from '@angular/core';
@Component({
  selector: 'profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css'],
  // providers: [HTTPService]
})
export class ProfileComponent implements OnInit{
  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }
  hide_1 = true;
  hide_2 = true;
  hide_3 = true;
  odlpass: string;
  pass_1: string;
  pass_2: string;

  // constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private http: HTTPService) { }
  // ngOnInit(): void {
  //     if (this.tokenStorage.getToken()) {
  //       // this.authority = this.tokenStorage.getAuthorities()[0];
  //     }
  // }
  onSubmitChangePassword(): void {
  //   if (this.pass_2 !== this.pass_1) return;
  //   this.http.get('http://localhost:8080/api/users/login/'+this.tokenStorage.getUsername()).subscribe(
  //     id => {
  //       this.http.post('http://localhost:8080/api/users/'+id+'/password/'+this.pass_1+'/oldpassword/'+this.odlpass,'').subscribe(
  //         res=>{
  //           //TODO: here need to оповестить user про result
  //       })
  //     },
  //     error => {
  //     }
  //   );
    ///{id}/name/{name}
    ////api/users/{id}/password/{password}
  }
  onSubmitChangeName(): void {
  //   this.http.get('http://localhost:8080/api/users/login/'+this.tokenStorage.getUsername()).subscribe(
  //     id => {
  //       this.http.put('http://localhost:8080/api/users/'+id+'/password/'+this.pass_1+'/oldpassword/'+this.odlpass,'').subscribe(
  //         res=>{
  //           //TODO: here need to оповестить user про result
  //       })
  //     },
  //     error => {
  //     }
  //   );
    ///{id}/name/{name}
    ////api/users/{id}/password/{password}
  }
}

