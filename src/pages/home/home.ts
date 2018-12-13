import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SecondPage } from '../second/second';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {

    // Should reach here in normal app launch
console.log("******Inside Home constructor*******");

let data=this.navParams.get('Data');
console.log("******Data: "+data); //Data will be undefined
}
 
 
  

  ionViewDidLoad() {
    console.log("ionViewDidLoad HomePage");
  }

 

}
