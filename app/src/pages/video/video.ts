import {AfterViewInit, Component, Directive, ViewChild} from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import jQuery from "jquery";
import * as RecordRTC from 'recordrtc';
import { Http } from "@angular/http";
import { LoadingController } from "ionic-angular";

/**
 * Generated class for the VideoPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
var that: any;
@IonicPage()
@Component({
  selector: 'page-video',
  templateUrl: 'video.html',
})

export class VideoPage {

	// /@ViewChild(Pane) video: any
	private stream: any;
	private recordRTC: any;
	private controls: boolean;
	videoUrl: String;
  loading;
  defaultImg;

	ngAfterViewInit() {
	    // set the initial state of the video
	    //let video:HTMLVideoElement = this.video.nativeElement;
	    //video.muted = false;
	    //video.controls = true;
	    //video.autoplay = false;
	    this.controls = true;
	  }

	videoRecording() {
		if(this.controls) this.startRecording();
		else this.stopRecording();
	}

	startRecording() {
	    let mediaConstraints = {
	      video: {
	          width: {min: 720, ideal:1280, max:1920},
	          height: {min: 720, ideal:1280, max:1920},
          facingMode: "user"
	      }, audio: true
	    };
	    navigator.mediaDevices
	      .getUserMedia(mediaConstraints)
	      .then(this.successCallback.bind(this), this.errorCallback.bind(this));
	}

	successCallback(stream: MediaStream) {
		var options = {
		      mimeType: 'video/webm', // or video/webm\;codecs=h264 or video/webm\;codecs=vp9
		      audioBitsPerSecond: 128000,
		      videoBitsPerSecond: 2500000,
		    };
	    this.stream = stream;
	    this.recordRTC = RecordRTC(stream, options);
	    this.recordRTC.startRecording();
	    //let video: HTMLVideoElement = this.video.nativeElement;
	    //video.src = window.URL.createObjectURL(stream);
	    this.toggleControls();
	 }

	errorCallback(data:any) {
	}

	toggleControls() {
	    //let video: HTMLVideoElement = this.video.nativeElement;
	    //video.muted = !video.muted;
	    //video.controls = !video.controls;
	    //video.autoplay = !video.autoplay;
	    this.controls = !this.controls;
	  }

	stopRecording() {
		let recordRTC = this.recordRTC;
		recordRTC.stopRecording(this.processVideo.bind(this));
		let stream = this.stream;
		stream.getAudioTracks().forEach(track => track.stop());
		stream.getVideoTracks().forEach(track => track.stop());
	}

	processVideo(audioVideoWebMURL) {
	  that = this;
    this.loading = this.loadingCtrl.create({
      content: '正在生成专属表情'
    });
    this.loading.present();
    var name = new Date().getTime().toString() + '.webm';
    this.videoUrl = name;
		//let video: HTMLVideoElement = this.video.nativeElement;
		let recordRTC = this.recordRTC;
		//video.src = audioVideoWebMURL;
		this.toggleControls();
		var recordedBlob = recordRTC.getBlob();
		//recordRTC.getDataURL(function (dataURL) { console.log(dataURL); });
		var data = new FormData();
	    data.append('key',name);
	    data.append('file',recordedBlob);
	    jQuery.ajax({
	      type: "POST",
	      url: "http://stickers-video.sh1a.qingstor.com",
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
	}

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: Http, public loadingCtrl: LoadingController) {
	  this.defaultImg = "https://ws1.sinaimg.cn/large/77ce63b1gy1fl6vj6moa5j20dw0dw0tc.jpg";
	}

  ionViewDidLoad() {
    console.log('ionViewDidLoad VideoPage');
  }

  submit(){
    let url = 'http://192.168.42.16:8081/reigister3?videoUrl='+"http://stickers-video.sh1a.qingstor.com/"+this.videoUrl;
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
