import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GpaperSharedModule } from 'app/shared/shared.module';
import { GpaperCoreModule } from 'app/core/core.module';
import { GpaperAppRoutingModule } from './app-routing.module';
import { GpaperHomeModule } from './home/home.module';
import { GpaperEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GpaperSharedModule,
    GpaperCoreModule,
    GpaperHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GpaperEntityModule,
    GpaperAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class GpaperAppModule {}
