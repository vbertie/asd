import {ModuleWithProviders} from '@angular/core';
import{RouterModule, Routes}   from '@angular/router';
import {LoginComponent} from './components/login/login.component'
import {CreateAccountComponent} from './components/create-account/create-account.component'
import {HomeComponent} from './components/home/home.component'
import {EditProfileComponent} from './components/edit-profile/edit-profile.component'

const appRoutes: Routes = [
  {
    path : '',
    redirectTo: '/user/login',
    pathMatch: 'full'
  },
  {
    path : 'user/login',
    component: LoginComponent
  },
  {
    path : 'user/sign-in',
    component: CreateAccountComponent
  },
  {
    path : 'home',
    component: HomeComponent
  },
  {
    path: 'home/edit',
    component: EditProfileComponent
  }
]
  export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
