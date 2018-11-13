import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-password-not-match',
  templateUrl: './password-not-match.component.html',
  styleUrls: ['./password-not-match.component.css']
})
export class PasswordNotMatchComponent implements OnInit {

  constructor(
    public dialogRefExceed: MatDialogRef<PasswordNotMatchComponent>) { }

  ngOnInit() {
  }

  onOk(): void {
    this.dialogRefExceed.close();
  }
}
