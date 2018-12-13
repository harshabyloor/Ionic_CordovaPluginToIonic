import { Component } from '@angular/core';
import { IonicPage, NavController } from 'ionic-angular';

/**
 * Generated class for the SecondPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-second',
  templateUrl: 'second.html',
})
export class SecondPage {

  constructor(public navCtrl: NavController) {
    console.log('constructor SecondPage');
    // Should reach here When intent is present
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SecondPage');
  }

}
