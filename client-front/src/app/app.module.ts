import {routing} from './app.routing';
import { AppComponent } from './app.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { LoginComponent, WrongCredentials } from './components/login/login.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {HttpModule} from '@angular/http';
import {AppConst} from './constants/app-const';
import { HomeComponent,DialogBuyShare,DialogSellShare,
        DialogBuyShareUnsuccesfully,DialogSellShareUnsuccesfully} from './components/home/home.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import {CdkStepperModule} from '@angular/cdk/stepper';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FormsModule} from '@angular/forms';

import {
  MatButtonModule,
  MatMenuModule,
  MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatGridListModule,
  MatInputModule,
  MatFormFieldModule,
  MatSelectModule,
  MatProgressBarModule,
  MatPaginatorModule,
  MatAutocompleteModule,
  MatTableModule,
  MatCheckboxModule,
  MatTabsModule,
  MatRadioModule,
} from '@angular/material';


import 'hammerjs';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { PasswordNotMatchComponent } from './components/password-not-match/password-not-match.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    LoginComponent,
    HomeComponent,
    NavBarComponent,
    DialogBuyShare,
    DialogSellShare,
    DialogBuyShareUnsuccesfully,
    DialogSellShareUnsuccesfully,
    EditProfileComponent,
    PasswordNotMatchComponent,
    WrongCredentials
  ],
  imports: [
    BrowserModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    HttpModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatProgressBarModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatTableModule,
    MatCheckboxModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatRadioModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    routing,
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [DialogBuyShare,DialogBuyShareUnsuccesfully,DialogSellShareUnsuccesfully,
    DialogSellShare,PasswordNotMatchComponent,WrongCredentials]
})
export class AppModule { }
