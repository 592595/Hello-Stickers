import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { AudioPage } from "../audio/audio";
import { TextPage } from "../text/text";
import { VideoPage } from "../video/video";


@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  picList:String[];

  constructor(public navCtrl: NavController) {
    var data = localStorage.getItem("picList");
    this.picList = JSON.parse(data);
  }

  navToAudio(){
    this.navCtrl.push(AudioPage);
  }
  navToText(){
    this.navCtrl.push(TextPage);
  }
  navToVideo(){
    this.navCtrl.push(VideoPage);
  }

}
