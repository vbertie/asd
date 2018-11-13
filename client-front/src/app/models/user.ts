import {Wallet} from './wallet';
import {UserData} from './userData';

export class User {
  public id:number;
  public username:string;
  public password:string;
  public userData:UserData;
  public wallet:Wallet;
}
