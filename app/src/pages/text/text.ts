import { Component } from '@angular/core';
import { AlertController, IonicPage, LoadingController, NavController, NavParams, ActionSheetController } from 'ionic-angular';
import { Http } from "@angular/http";
import { PhotoLibrary } from '@ionic-native/photo-library';
/**
 * Generated class for the TextPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
declare var cordova: any;

@IonicPage()
@Component({
  selector: 'page-text',
  templateUrl: 'text.html',
})
export class TextPage {
  defaultImg;
  myText:String;
  loading;

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: Http, public loadingCtrl: LoadingController, public alertCtrl: AlertController, public actionSheetCtrl: ActionSheetController) {
    this.defaultImg="https://ws1.sinaimg.cn/large/77ce63b1gy1fl5wqpcxbtj208c08iaa3.jpg";
  }

  onHold(){
    var imgSrc = this.defaultImg;
    this.presentActionSheet(imgSrc);
  }

  presentActionSheet(imgUrl: String){
    let actionSheet = this.actionSheetCtrl.create({
      title: '存储表情包',
      buttons:[
        {
          text:'保存到本地',
          handler:()=>{
            this.saveImage(imgUrl);
            console.log('Destructive clicked');
          }
        },
        {
          text:'取消',
          role: 'cancel',
          handler:()=>{
            console.log('Cancel clicked');
          }
        }
      ]
    });
    actionSheet.present();
  }

  saveImage(imgUrl) {
    cordova.plugins.photoLibrary.requestAuthorization(
      function () {
        // User gave us permission to his library, retry reading it!
        cordova.plugins.photoLibrary.getLibrary(
          function ({library}) {
            //var url = 'file:///...'; // file or remote URL. url can also be dataURL, but giving it a file path is much faster
            var album = 'HelloStickers';
            cordova.plugins.photoLibrary.saveImage(imgUrl, album,
              function (libraryItem) {
                alert("保存成功"+libraryItem);
              }, function (err) {
                alert('保存失败'+err);
              });
          },
          function (err) {
            if (err.startsWith('Permission')) {
              // call requestAuthorization, and retry
            }
            // Handle error - it's not permission-related
            console.log('权限'+err);

          }
        );
      },
      function (err) {
        // User denied the access
        alert('用户拒绝访问'+err);
      }, // if options not provided, defaults to {read: true}.
      {
        read: true,
        write: true
      }
    );

  }

  getResult(){
    if(!this.myText){
      alert("还没有填写文字");
      return;
    }
    let url = 'http://192.168.42.16:8081/reigister1?text='+this.myText;
    this.loading = this.loadingCtrl.create({
      content: '生成专属表情中'
    });
    this.loading.present();
    this.http.get(url).subscribe((res)=>{
      this.loading.dismiss();
      if(res.json().success){
        this.defaultImg = res.json().data;
      }else {
        alert("请重试");
      }
    })
  }

}
