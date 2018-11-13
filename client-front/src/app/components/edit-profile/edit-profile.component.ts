import { Component, Inject, OnInit } from '@angular/core';
import { UserService } from '../../services/UserService/user.service'
import { User } from '../../models/user';
import { Wallet } from '../../models/wallet';
import { UserData } from '../../models/userData';
import { Http, Headers } from '@angular/http';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { PasswordNotMatchComponent } from '../../components/password-not-match/password-not-match.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  constructor(private userService: UserService, private router: Router,
    private route: ActivatedRoute, private dialog: MatDialog) { }

  private user: User = new User();
  private wallet: Wallet = new Wallet();
  private userData: UserData = new UserData();

  private confirmedPassword: string;
  private selectedTab: number;

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
  private passwordsNotMatch: boolean;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.user = JSON.parse(params['user']);
    });

    this.wallet = this.user.wallet;
    this.userData = this.user.userData
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

    if (this.confirmedPassword == this.user.password) {

      this.userService.updateUserData(this.user).subscribe(
        res => {
          location.reload();
          this.router.navigate(['/home'])
        },
        err => {

          for (let i of err.json()) {

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
      )
    } else {
      this.passwordsNotMatch = true;
      this.selectedTab = 0;

      this.passwordsNotMatch = true;
      this.selectedTab = 0;

      const dialogRef = this.dialog.open(PasswordNotMatchComponent, {
        width: '250px',
        height: '200px',
      }
      )
    }
  }
}
