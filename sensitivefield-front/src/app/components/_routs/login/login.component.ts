
import { Component, OnInit } from '@angular/core';
import { LoginInfo } from 'src/app/interfaces/login';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
import {Router} from '@angular/router';


@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginRoute implements OnInit {
  username: string;
  password: string;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: LoginInfo;
  public authority: string;
  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }
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
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        window.location.reload();
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
}













// username: string;
// password: string;

// constructor(username: string, password: string) {
//     this.username = username;
//     this.password = password;
// }
// export class SignUpInfo {
//     name: string;
//     username: string;
//     email: string;
//     role: string[];
//     password: string;

//     constructor(name: string, username: string, email: string, password: string) {
//         this.name = name;
//         this.username = username;
//         this.email = email;
//         this.password = password;
//         this.role = ['user'];
//     }
// }
