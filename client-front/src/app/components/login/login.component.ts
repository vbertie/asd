import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/UserService/user.service'
import { Http, Headers } from '@angular/http';
import { Router, NavigationExtras } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService,
    private http: Http, private router: Router, private dialog: MatDialog) { }

  private credential = { 'username': '', 'password': '' }
  private wrongCredentials: boolean;
  private loggedIn = false;

  ngOnInit() {
    if (localStorage.getItem("xAuthToken") != null) {
      this.loggedIn = true;
      this.router.navigate(["/home"])
    }
  }

  onSubmit() {
    this.userService.sendCredential(this.credential.username, this.credential.password).subscribe(
      res => {
        console.log(res);
        localStorage.setItem("xAuthToken", res.json().id);
        location.reload();


        this.router.navigate(['/home']);
        this.loggedIn = true;

      },
      error => {
        const dialogRef = this.dialog.open(WrongCredentials, {
          width: '250px',
        })
      }
    );
  }
}

@Component({
  selector: 'wrong-credentials',
  templateUrl: 'wrong-credentials.html',
})
export class WrongCredentials {

  constructor(
    public dialogRefExceed: MatDialogRef<WrongCredentials>) { }

  onOk(): void {
    this.dialogRefExceed.close();
  }
}
