import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { Media } from "@ionic-native/media";
import { HttpModule } from "@angular/http";
import { PhotoLibrary } from "@ionic-native/photo-library";

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { AudioPage } from "../pages/audio/audio";
import { TextPage } from "../pages/text/text";
import { VideoPage } from "../pages/video/video";
import { MyPicProvider } from '../providers/my-pic/my-pic';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    AudioPage,
    TextPage,
    VideoPage
  ],
  imports: [
    HttpModule,
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    AudioPage,
    TextPage,
    VideoPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    Media,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    MyPicProvider
  ]
})
export class AppModule {}
