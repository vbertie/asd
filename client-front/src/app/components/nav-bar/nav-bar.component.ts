import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/UserService/user.service'
import { StockService } from '../../services/StockService/stock.service'
import { User } from '../../models/user';
import { UserData } from '../../models/userData';
import { Wallet } from '../../models/wallet';
import { StockItem } from '../../models/stockItem';
import { Stock } from '../../models/stock';
import { Http, Headers } from '@angular/http';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(private userService: UserService,
    private http: Http, private router: Router,
    private route: ActivatedRoute) { }

    private user: User = new User();
    private userData: UserData = new UserData();

  ngOnInit() {


    this.userService.findUserById(localStorage.getItem("xAuthToken")).subscribe(
      res => {
        this.user = res.json()
        this.userData = this.user.userData;
      },
      err => {
        console.log(err);
      }
    )
  }

  logout() {
    this.userService.logout().subscribe(
      res => {
        localStorage.clear();
        this.router.navigate(["/"]);
        location.reload();
      },
      err => {
        console.log(err);
      }
    );
  }

  editProfile() {

    let navigationExtras: NavigationExtras = {
      queryParams: {
        "user": JSON.stringify(this.user)
      }
    };

    this.router.navigate(['/home/edit'], navigationExtras);
  }
}
