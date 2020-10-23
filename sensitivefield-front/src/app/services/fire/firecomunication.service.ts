import { OnInit } from '@angular/core';
import {Injectable} from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { LocalStorage } from '../storage/local.storage.service';

@Injectable()
export class FireCommunication {
  constructor() { }
  static async onInit(): Promise<void> {
    const userSet = new LocalStorage();
    await userSet.ngOnInit();
    FireCommunication.navbar.next(await userSet.getUser().left_navbar);
    FireCommunication.soundAll = await userSet.getUser().sound_all;
    document.documentElement.style.setProperty('--primary-color', await userSet.getUser().s_text);
    document.documentElement.style.setProperty('--text-color', await userSet.getUser().text);
    document.documentElement.style.setProperty('--secondary-color', await userSet.getUser().s_back);
    document.documentElement.style.setProperty('--background-color', await userSet.getUser().back);
  }
  public static navbar = new BehaviorSubject<boolean>(true);
  private static soundAll = false;
  static isHide(hide?: boolean){
    if (hide !== undefined){
      const userSet = new LocalStorage();
      const userNew = {
        user_name: userSet.getUser().user_name,
        left_navbar: hide,
        sound_all: userSet.getUser().sound_all,
        back: userSet.getUser().back,
        s_back: userSet.getUser().s_back,
        text: userSet.getUser().text,
        s_text: userSet.getUser().s_text
      } as User;
      userSet.setUser(userNew);
      this.navbar.next(hide);
    }
    return this.navbar;
  }
  static isSoundAll(on?: boolean) {
    if (on !== undefined){
      const userSet = new LocalStorage();
      const userNew = {
        user_name: userSet.getUser().user_name,
        left_navbar: userSet.getUser().left_navbar,
        sound_all: on,
        back: userSet.getUser().back,
        s_back: userSet.getUser().s_back,
        text: userSet.getUser().text,
        s_text: userSet.getUser().s_text
      } as User;
      userSet.setUser(userNew);
      this.soundAll = on;
    }
    return this.soundAll;
  }
  static styleSecondaryTextColor(s_textColor: string) {
    if (s_textColor !== undefined){
      const userSet = new LocalStorage();
      const userNew = {
        user_name: userSet.getUser().user_name,
        left_navbar: userSet.getUser().left_navbar,
        sound_all: userSet.getUser().sound_all,
        back: userSet.getUser().back,
        s_back: userSet.getUser().s_back,
        text: userSet.getUser().text,
        s_text: s_textColor
      } as User;
      userSet.setUser(userNew);
      document.documentElement.style.setProperty('--primary-color', s_textColor );
    }
    return s_textColor;
  }
  static styleTextColor(textColor: string) {
    if (textColor !== undefined){
      const userSet = new LocalStorage();
      const userNew = {
        user_name: userSet.getUser().user_name,
        left_navbar: userSet.getUser().left_navbar,
        sound_all: userSet.getUser().sound_all,
        back: userSet.getUser().back,
        s_back: userSet.getUser().s_back,
        text: textColor,
        s_text: userSet.getUser().s_text
      } as User;
      userSet.setUser(userNew);
      document.documentElement.style.setProperty('--text-color', textColor );
    }
    return textColor;
  }
  static styleSecondaryBackground(s_background: string) {
      if (s_background !== undefined){
        const userSet = new LocalStorage();
        const userNew = {
          user_name: userSet.getUser().user_name,
          left_navbar: userSet.getUser().left_navbar,
          sound_all: userSet.getUser().sound_all,
          back: userSet.getUser().back,
          s_back: s_background,
          text: userSet.getUser().text,
          s_text: userSet.getUser().s_text
        } as User;
        userSet.setUser(userNew);
        document.documentElement.style.setProperty('--secondary-color', s_background );
      }
      return s_background;
  }
  static styleBackground(background: string) {
    if (background !== undefined){
      const userSet = new LocalStorage();
      const userNew = {
        user_name: userSet.getUser().user_name,
        left_navbar: userSet.getUser().left_navbar,
        sound_all: userSet.getUser().sound_all,
        back: background,
        s_back: userSet.getUser().s_back,
        text: userSet.getUser().text,
        s_text: userSet.getUser().s_text
      } as User;
      userSet.setUser(userNew);
      document.documentElement.style.setProperty('--background-color', background );
    }
    return background;
  }
}
