import { Injectable } from '@angular/core';
import {Http, Headers } from '@angular/http';
import {Observable} from 'rxjs';
import {AppConst} from '../../constants/app-const';
import {Purchase} from '../../models/purchase';
import {Sale} from '../../models/sale';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http:Http) { }

    getStock() {

    let url = AppConst.stockService + "/stocks/future";

    let headers = new Headers({
      'Content-Type' : 'application/json'
    });

      return this.http.get(url, {headers:headers});
  }

  processPurchase(purchase:Purchase) {

    let url = AppConst.stockService + "/stocks/purchase";

    let headers = new Headers({
      'Content-Type' : 'application/json'
    });

      return this.http.put(url, purchase, {headers:headers});
  }
}
