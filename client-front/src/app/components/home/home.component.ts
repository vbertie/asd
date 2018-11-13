import { Component, Inject, OnInit } from '@angular/core';
import { UserService } from '../../services/UserService/user.service'
import { StockService } from '../../services/StockService/stock.service'
import { User } from '../../models/user';
import { Wallet } from '../../models/wallet';
import { StockItem } from '../../models/stockItem';
import { Stock } from '../../models/stock';
import { Sale } from '../../models/sale';
import { Purchase } from '../../models/purchase';
import { MarketStockItem } from '../../models/marketStockItem';
import { Http, Headers } from '@angular/http';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private userService: UserService, private stockService: StockService,
    private router: Router, private route: ActivatedRoute, private dialog: MatDialog) { }

  private user: User = new User();
  private wallet: Wallet = new Wallet();
  private userStockItems: StockItem[];
  private stockMarketItems: MarketStockItem[];
  private stock: Stock = new Stock();
  private sale: Sale = new Sale();
  private purchase: Purchase = new Purchase();

  private loggedIn: boolean;
  private amount: number;
  private saleAmount: number;

  private correctDate: string;

  private unsuccessfullyInfo: string;

  ngOnInit() {
    if (localStorage.getItem("xAuthToken") != null) {
      this.loggedIn = true;
    }

    this.userService.findUserById(localStorage.getItem("xAuthToken")).subscribe(
      res => {
        this.user = res.json();
        this.wallet = this.user.wallet;
        this.userStockItems = this.wallet.stockItems
      },
      err => {
        console.log(err);
      }
    )

    this.stockService.getStock().subscribe(
      res => {
        this.stock = res.json();
        this.stockMarketItems = this.stock.stockItems;

        var arr = this.stock.date[0] + "-" + this.stock.date[1] + "-" + this.stock.date[2] + " " +
          this.stock.date[3] + ":" + this.stock.date[4] + ":" + this.stock.date[5];

        this.correctDate = arr;
        console.log(arr)
      },
      err => {
        console.log(err);
      }
    );
    //


  }


  openDialogSell(company: string, avaibleAmount: number, unitPirce: number) {

    const dialogRef = this.dialog.open(DialogSellShare, {
      width: '450px',
      height: '250px',

      data: {
        company: company,
        amount: this.saleAmount
      }

    })


    dialogRef.afterClosed().subscribe(result => {

      this.saleAmount = result;

      if (this.saleAmount > avaibleAmount) {
        this.unsuccessfullyInfo = "Choosen amount exceeds your avaible shares amount (" + avaibleAmount + ").";

        const dialogRefExceed = this.dialog.open(DialogSellShareUnsuccesfully, {
          width: '350px',
          height: '200px',

          data: {
            unsuccessfullyInfo: this.unsuccessfullyInfo
          }
        });

      } else if (this.saleAmount != null) {

        this.sale.amount = this.saleAmount;
        this.sale.stockName = this.stock.stockName;
        this.sale.companyName = company;
        this.sale.userId = this.user.id;

        this.userService.processSale(this.sale).subscribe(
          res => {
            location.reload();
          },
          err => {
            console.log(err);
          }
        )
      }
    });

  }

  openDialogBuy(company: string, avaibleAmount: number, unit: number,
    unitPrice: number, stockItem: MarketStockItem) {

    const dialogRef = this.dialog.open(DialogBuyShare, {
      width: '450px',
      height: '250px',

      data: {
        company: company,
        unit: unit,
        amount: this.amount
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.amount = result;

      console.log(this.amount)

      if (this.amount > avaibleAmount) {
        this.unsuccessfullyInfo = "Choosen amount exceeds avaible stock share amount (" + avaibleAmount + ").";

        const dialogRefExceed = this.dialog.open(DialogBuyShareUnsuccesfully, {
          width: '350px',
          height: '200px',

          data: {
            unsuccessfullyInfo: this.unsuccessfullyInfo
          }
        });

      } else if (this.amount % unit != 0 && this.amount != null) {
        this.unsuccessfullyInfo = "Choosen amount is not multiple of " + unit + ".";

        const dialogRefExceed = this.dialog.open(DialogBuyShareUnsuccesfully, {
          width: '350px',
          height: '200px',

          data: {
            unsuccessfullyInfo: this.unsuccessfullyInfo
          }
        });

      } else if (this.amount * unitPrice > this.wallet.balance) {
        this.unsuccessfullyInfo = "You dont have enough money to purchase shares.";

        const dialogRefExceed = this.dialog.open(DialogBuyShareUnsuccesfully, {
          width: '350px',
          height: '200px',

          data: {
            unsuccessfullyInfo: this.unsuccessfullyInfo
          }
        });

      } else if (this.amount != null) {
        this.purchase.amount = this.amount;
        this.purchase.userId = this.user.id;
        this.purchase.payment = this.amount * unitPrice;
        this.purchase.stockItem = stockItem;

        this.stockService.processPurchase(this.purchase).subscribe(
          res => {
            location.reload();
          },
          err => {

          }
        )
      }
    });


  }
}

@Component({
  selector: 'dialog-sell-share',
  templateUrl: 'dialog-sell-share.html',
})
export class DialogSellShare {

  constructor(
    public dialogRef: MatDialogRef<DialogSellShare>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onOk(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'dialog-sell-share-unsuccessfully',
  templateUrl: 'dialog-sell-share-unsuccessfully.html',
})
export class DialogSellShareUnsuccesfully {

  constructor(
    public dialogRefExceed: MatDialogRef<DialogSellShareUnsuccesfully>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onOk(): void {
    this.dialogRefExceed.close();
  }
}



@Component({
  selector: 'dialog-buy-share',
  templateUrl: 'dialog-buy-share.html',
})
export class DialogBuyShare {

  constructor(
    public dialogRef: MatDialogRef<DialogBuyShare>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onOk(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'dialog-buy-share-unsuccessfully',
  templateUrl: 'dialog-buy-share-unsuccessfully.html',
})
export class DialogBuyShareUnsuccesfully {

  constructor(
    public dialogRefExceed: MatDialogRef<DialogBuyShareUnsuccesfully>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onOk(): void {
    this.dialogRefExceed.close();
  }
}
