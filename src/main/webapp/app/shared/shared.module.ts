import { NgModule } from '@angular/core';
import { GpaperSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import {HasAccessDirective} from "app/shared/auth/has-access.directive";

@NgModule({
  imports: [GpaperSharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, HasAccessDirective],
  entryComponents: [LoginModalComponent],
  exports: [GpaperSharedLibsModule, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, HasAccessDirective]
})
export class GpaperSharedModule {}
