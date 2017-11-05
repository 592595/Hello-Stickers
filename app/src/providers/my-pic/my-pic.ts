import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';

/*
  Generated class for the MyPicProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class MyPicProvider {
  public picList:String[];
  constructor(public http: Http) {
    if(localStorage.getItem("picList").length>0){
      var data = localStorage.getItem("picList");
      this.picList = JSON.parse(data);
    }
  }
  add(imgUrl:String){
    this.picList.push(imgUrl);
    var str = JSON.stringify(this.picList);
    localStorage.setItem("picList",str);
  }
}
