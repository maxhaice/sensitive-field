import {Component, OnInit} from '@angular/core';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
import {FireSound} from '../../../services/fire/firesound.service';
import {SignUpInfo} from '../../../interfaces/signup.service';
import {AuthService} from '../../../services/auth/auth.service';
import {HTTPService} from "../../../services/http.service";
@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css'],
  providers: [HTTPService]
})
export class RegisterComponent implements OnInit{
  signupInfo = {
    name: '',
    username: '',
    email: '',
    password: ''
  } as SignUpInfo;
  role: string;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  public authority: string;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private http: HTTPService) { }
  ngOnInit(): void {
      if (this.tokenStorage.getToken()) {
        this.authority = this.tokenStorage.getAuthorities()[0];
      }
    //   if (!this.authority) {
    //     if (this.authority === 'ROLE_USER') {
    //       // TODO: if user have USER_ROLE
    //       this.router.navigate(['']);
    //     }
    //   }
    // }
    // else {
    //   this.router.navigate(['']);
    // } }
  }
  onSubmit(): void {
    this.http.post('http://localhost:8080/api/auth/addUser?role=' + this.role, this.signupInfo).subscribe(
      data => {
        this.isSignedUp = true;
        this.isSignUpFailed = false;
        alert('Nice work!');
      },
      error => {
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
        FireSound.execute();
        alert('Bad work!');

      }
    );
  }
}
