import {Injectable, OnInit} from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { TokenStorageService } from '../auth/token.storage.service';

@Injectable()
export class LocalStorage implements OnInit {
  private t = new TokenStorageService();
  private user: User;
  constructor() {
    this.ngOnInit();
   }
  ngOnInit(): void {
    const username = this.t.getUsername()?this.t.getUsername():'default';
    this.user = JSON.parse(localStorage.getItem(username)) as User;
    if (this.user == null){
      let _default = JSON.parse(localStorage.getItem('default')) as User;
      if (_default != null) this.user = _default;
      else{
        this.user = this.initDefault();
      }
    }
  }
  public getUser(): User{
    return this.user;
  }
  public save(): void{
    return localStorage.setItem(this.user.user_name,JSON.stringify(this.user));
  }
  public setUser(user: User){
    this.user = user;
    return this.save();
  }
  public toDefault(){
    this.user = {
      user_name: this.user.user_name,
      left_navbar: false,
      sound_all: false
    } as User;
    this.save();
  }
  public initDefault(){
    let _default = {
      user_name: 'default',
      left_navbar: false,
      sound_all: false,
      back: '#E2E5E9',
      s_back: '#2d2d2d',
      text: '#2d2d2d',
      s_text: '#fff'
    } as User;
    localStorage.setItem('default',JSON.stringify(_default));
    return _default;
  }
}
