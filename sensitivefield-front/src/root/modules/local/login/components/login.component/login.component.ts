
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';

import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
import { LoginInfo } from '../../interfaces/logininfo.interface';
import { LoginService } from '../../services/login.service';


@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None

})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  //TODO: write verifi fields
  roles: string[] = [];

  private loginInfo: LoginInfo;
  public authority: string;

  constructor(private authService: LoginService, private tokenStorage: TokenStorageService, private router: Router) { }
  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      console.log(this.authority);
      if ( this.authority ){
        this.router.navigate(['situation']);
      }
  }
}
  onSubmit(): void {
    this.loginInfo = {
        username: this.username,
        password: this.password
     } as LoginInfo;
    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.roles = this.tokenStorage.getAuthorities();
        window.location.reload();
      },
      error => {
        console.log(error);
        //TODO: write with fire alert
      }
    );
  }
}

