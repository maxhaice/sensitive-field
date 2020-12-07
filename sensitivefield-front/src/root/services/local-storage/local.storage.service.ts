import { Injectable, OnInit } from '@angular/core';
import { User } from 'src/root/interfaces/user.interface';
import { TokenStorageService } from './token.storage.service';

@Injectable()
export class LocalStorage {
  private t = new TokenStorageService();
  private user: User;
  constructor() {
    this.onInit();
   }
  public onInit(): void {
    const username = this.t.getUsername() ? this.t.getUsername() : 'default';
    this.user = JSON.parse(localStorage.getItem(username)) as User;
    if (this.user == null){
      const defaultUser = JSON.parse(localStorage.getItem('default')) as User;
      if (defaultUser != null) {
        this.user = defaultUser;
      }
      else{
        this.user = this.initDefault();
      }
    }
  }
  public save(): void{
    return localStorage.setItem(this.user.name, JSON.stringify(this.user));
  }
  public getUser(): User{
    return this.user;
  }
  public setUser(user: User): void{
    this.user = user;
    return this.save();
  }
  public toDefault(): void{
    this.user = {
      user_name: this.user.name,
      left_navbar: false,
      sound_all: false
    } as unknown as User;
    this.save();
  }
  public initDefault(): User{
    const defaultUser = {
      user_name: 'default',
      left_navbar: false,
      sound_all: false,
      back: '#E2E5E9',
      s_back: '#2d2d2d',
      text: '#2d2d2d',
      s_text: '#fff'
    } as unknown as User;
    localStorage.setItem('default', JSON.stringify(defaultUser));
    return defaultUser;
  }
}
