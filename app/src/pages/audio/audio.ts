import {Component} from '@angular/core';
import {IonicPage, NavController, NavParams} from 'ionic-angular';
import {AlertController, LoadingController, ActionSheetController} from 'ionic-angular';
import {Http} from "@angular/http";
import jQuery from "jquery";
import * as resampler from "audio-resampler";

declare var Recorder: any;
var that: any;
/**
 * Generated class for the AudioPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
declare var cordova: any;

@IonicPage()
@Component({
  selector: 'page-audio',
  templateUrl: 'audio.html',
})
export class AudioPage {
  recorded: boolean;
  AudioUrl: String;
  loading;
  defaultImg;
  isClicked: boolean;
  public recorder: any;
  private audio_context: any;

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: Http, public loadingCtrl: LoadingController, public alertCtrl: AlertController, public actionSheetCtrl: ActionSheetController) {
    this.recorded = false;
    this.isClicked = false;
    this.defaultImg = "https://ws1.sinaimg.cn/large/77ce63b1ly1fl652p73krj205k05kzk6.jpg";
  }

  ngOnInit(): void {
    try {
      this.audio_context = new AudioContext;
      console.log('Audio context set up');
      console.log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
    } catch (e) {
      alert('No web audio support in this browser!');
    }

    navigator.getUserMedia({audio: true}, (stream) => {
        var input = this.audio_context.createMediaStreamSource(stream);
        console.log('Media stream created.');
        this.recorder = new Recorder(input);
        console.log('Recorder initialised.');
      },
      function (e) {
        console.log('No live audio input: ' + e);
      });
  }

  startRecording() {
    this.record();
    this.recorder && this.recorder.record();
    console.log('Recording...');
  }

  stopRecording() {
    this.record();
    var name = new Date().getTime().toString() + '.wav';
    this.AudioUrl = name;
    this.recorder && this.recorder.stop();
    console.log('Stopped recording.');
    that = this;
    this.loading = this.loadingCtrl.create({
      content: '正在为您生成中'
    });
    this.loading.present();
    this.recorder && this.recorder.exportWAV(function (blob) {
      var url = URL.createObjectURL(blob);
      resampler(url, 16000, function (event) {
        event.getFile(function (fileEvent) {
          console.log("Finish downsampling!");
          fetch(fileEvent)
            .then(res => res.blob())
            .then(blob => {
              var data = new FormData();
              data.append('key', name);
              data.append('file', blob);
              jQuery.ajax({
                type: "POST",
                url: "http://stickers-audio.sh1a.qingstor.com",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                  console.log("SUCCESS : ", data);
                  that.submit();
                },
                error: function (e) {
                  console.log("ERROR");
                }
              });
            });
        });

      });
    });
    this.recorder.clear();
  }

  record() {
    this.isClicked = !this.isClicked;
  }

  onHold() {
    var imgSrc = this.defaultImg;
    this.presentActionSheet(imgSrc);
  }

  presentActionSheet(imgUrl: String) {
    let actionSheet = this.actionSheetCtrl.create({
      title: '存储表情包',
      buttons: [
        {
          text: '保存到本地',
          handler: () => {
            this.saveImage(imgUrl);
            console.log('Destructive clicked');
          }
        },
        {
          text: '取消',
          role: 'cancel',
          handler: () => {
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
                alert("保存成功" + libraryItem);
              }, function (err) {
                alert('保存失败' + err);
              });
          },
          function (err) {
            if (err.startsWith('Permission')) {
              // call requestAuthorization, and retry
            }
            // Handle error - it's not permission-related
            console.log('权限' + err);

          }
        );
      },
      function (err) {
        // User denied the access
        alert('用户拒绝访问' + err);
      }, // if options not provided, defaults to {read: true}.
      {
        read: true,
        write: true
      }
    );

  }


  submit() {
    let url = 'http://192.168.42.16:8081/reigister2?audioUrl='+"http://stickers-audio.sh1a.qingstor.com/"+this.AudioUrl;

    this.http.get(url).subscribe((res)=>{
      this.loading.dismiss();
      if(res.json().success){
        this.defaultImg = res.json().data;
      }
      else {
        alert("请重试~");
      }
    })
  }
}
