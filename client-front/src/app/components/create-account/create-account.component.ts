import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/UserService/user.service'
import { StockService } from '../../services/StockService/stock.service'
import { UserData } from '../../models/userData';
import { User } from '../../models/user';
import { Wallet } from '../../models/wallet';
import { StockItem } from '../../models/stockItem';
import { Stock } from '../../models/stock';
import { Http, Headers } from '@angular/http';
import { Router } from '@angular/router';
import { PasswordNotMatchComponent } from '../../components/password-not-match/password-not-match.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {

  constructor(private userService: UserService, private stockService: StockService,
    private router: Router, private dialog: MatDialog) { }

  private user: User = new User();
  private wallet: Wallet = new Wallet();
  private stockItems: StockItem[];
  private stockItem: StockItem = new StockItem();
  private stock: Stock = new Stock();
  private userData: UserData = new UserData();

  private checked: boolean;
  private allChecked: boolean;

  private selectedTab: number;

  private confirmedPassword: string;
  private passwordsNotMatch: boolean;
  private usernameNotNull: boolean;
  private passwordNotNull: boolean;
  private firstNameNotNull: boolean;
  private firstNameSize: boolean;
  private lastNameNotNull: boolean;
  private lastNameSize: boolean;
  private emailNotNull: boolean;
  private emailSize: boolean;
  private phonePattern: boolean;
  private phoneNotNull: boolean;
  private emailSyntax: boolean;
  private walletSize: boolean;
  private walletPattern: boolean;
  private uniqueEmail: boolean;
  private uniqueUsername: boolean;

  private userStockItems: StockItem[] = new Array();

  ngOnInit() {
    this.stockService.getStock().subscribe(
      res => {
        this.stock = res.json();
        this.stockItems = this.stock.stockItems;
      },
      err => {
        console.log(err);
      }
    );
  }

  addStockItemToUserList(checked: boolean, stockItem: StockItem) {
    if (checked) {
      this.userStockItems.push(stockItem);
    } else {
      this.userStockItems.splice(this.userStockItems.indexOf(stockItem), 1);
    }
  }

  goToPersonal() {
    this.selectedTab = 1;
  }

  goToWallet() {
    this.selectedTab = 2;
  }

  selectedChange(val: number) {
    this.selectedTab = val;
  }


  onSubmit() {

    this.wallet.stockItems = this.userStockItems;
    this.user.wallet = this.wallet;
    this.user.userData = this.userData;

    if (this.confirmedPassword == this.user.password) {

      this.userService.createUser(this.user).subscribe(
        res => {
          this.router.navigate(['/user/login'])
          console.log(res)
        },
        err => {

          this.usernameNotNull = false;
          this.passwordNotNull = false;
          this.firstNameNotNull = false;
          this.firstNameSize = false;
          this.lastNameNotNull = false;
          this.lastNameSize = false;
          this.emailSize = false;
          this.emailNotNull = false;
          this.phonePattern = false;
          this.phoneNotNull = false;
          this.emailSyntax = false;
          this.walletSize = false;
          this.walletPattern = false;
          this.uniqueEmail = false;
          this.uniqueUsername = false;

          for (let i of err.json()) {

            //first tab
            if (i.field == "username" || i.field == "password") {

              if (i.field == "username") {

                if (i.code == "NotNull") {
                  this.usernameNotNull = true;
                }

                if (i.code == "UniqueUsername") {
                  this.uniqueUsername = true;
                }
              }

              if (i.field == "password") {

                if (i.code == "NotNull") {
                  this.passwordNotNull = true;
                }
              }
            }

            //second tab
            if (i.field == "userData.firstName" || i.field == "userData.lastName"
              || i.field == "userData.email" || i.field == "userData.phoneNumber") {

              if (i.field == "userData.firstName") {

                if (i.code == "NotNull") {
                  this.firstNameNotNull = true;
                }
                if (i.code == "Size" && i.rejectedValue == "") {
                  this.firstNameNotNull = true;
                }
                if (i.code == "Size" && i.rejectedValue != "") {
                  this.firstNameSize = true;
                }
                this.selectedTab = 0;
              }

              if (i.field == "userData.lastName") {

                if (i.code == "NotNull") {
                  this.lastNameNotNull = true;
                }
                if (i.code == "Size" && i.rejectedValue == "") {
                  this.lastNameNotNull = true;
                }
                if (i.code == "Size" && i.rejectedValue != "") {
                  this.lastNameSize = true;
                }
              }

              if (i.field == "userData.email") {

                if (i.code == "Email") {
                  this.emailSyntax = true;
                }
                if (i.code == "NotNull") {
                  this.emailNotNull = true;
                }
                if (i.code == "UniqueEmail") {
                  this.uniqueEmail = true;
                }
                if (i.code == "Size" && i.rejectedValue == "") {
                  this.emailNotNull = true;
                }
                if (i.code == "Size" && i.rejectedValue != "") {
                  this.emailSize = true;
                }
              }

              if (i.field == "userData.phoneNumber") {

                if (i.code == "NotNull") {
                  this.phoneNotNull = true;
                } else if (i.code == "Pattern" && i.rejectedValue == "") {
                  this.phoneNotNull = true;
                }

                if (i.code == "Pattern" && i.rejectedValue != "") {
                  this.phonePattern = true;
                }
              }
              this.selectedTab = 1;
            }


            //third tab
            if (i.field == "wallet.balance") {

              if (i.code == "Size") {
                this.walletSize = true;
              }

              if (i.code == "Pattern") {
                this.walletPattern = true;
              }
            }

          }
        }
      );
    } else {
      this.passwordsNotMatch = true;
      this.selectedTab = 0;

      const dialogRef = this.dialog.open(PasswordNotMatchComponent, {
        width: '300px',
      }
      )
    }
  }
}
