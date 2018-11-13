import { Injectable } from '@angular/core';
import {Http, Headers } from '@angular/http';
import {Observable} from 'rxjs';
import {AppConst} from '../../constants/app-const';
import {User} from '../../models/user';
import {Sale} from '../../models/sale';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:Http) { }

  sendCredential(username: string, password: string) {
  let url = AppConst.userService + "/login/token";
  let encodedCredentials = btoa(username+":"+password);
  let basicHeader = "Basic "+encodedCredentials;
  let headers = new Headers ({
    'Content-Type' : 'application/json',
    'Authorization' : basicHeader
  });
  console.log(basicHeader);
  return this.http.get(url, {headers: headers});
}

  createUser(user:User){
    let url = AppConst.userService + "/users";
    let headers = new Headers ({
      'Content-Type' : 'application/json',
    });
    return this.http.post(url,user, {headers: headers});
  }

  logout(){
  let url = AppConst.userService + "/login/logout";
  let headers = new Headers({
    'x-auth-token' : localStorage.getItem('xAuthToken')
  });
    return this.http.post(url,'', {headers:headers});
}

  findUserById(id:string) {
    let url = AppConst.userService + "/users/" + id;
    let headers = new Headers ({
      'Content-Type' : 'application/json',
    });
    return this.http.get(url, {headers: headers});
  }

  processSale(sale:Sale) {

    let url = AppConst.userService + "/users/sale";

    let headers = new Headers({
      'Content-Type' : 'application/json'
    });

      return this.http.put(url, sale, {headers:headers});
  }

  updateUserData(user:User) {
    let url = AppConst.userService + "/users/update";

    let headers = new Headers({
      'Content-Type' : 'application/json'
    });

      return this.http.put(url, user, {headers:headers});
  }
}
