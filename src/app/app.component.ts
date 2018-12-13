import { Component, ViewChild } from '@angular/core';
import { Platform, Nav } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HomePage } from '../pages/home/home';
import { SecondPage } from '../pages/second/second';
import { WebIntent } from '@ionic-native/web-intent';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  //rootPage:any = HomePage;
  @ViewChild(Nav) nav: Nav;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen
    , private webIntent: WebIntent) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      console.log("Inside appcomp constructor");
     
if(platform.is('android')){ 
  console.log("Android");    
this.webIntent.getIntent().then((myintent) => {
  var intentExtras = myintent.extras;
        if (intentExtras == null)
            intentExtras = "No extras in intent";
            else
        console.log('Launch Intent Extras: ' + JSON.stringify(intentExtras));
  //console.log('Received Intent: ' + intentExtras);
  this.nav.setRoot(SecondPage);
})
.catch((error) => console.log('Received Error: ' +error))}
      
      statusBar.styleDefault();
      splashScreen.hide();
      this.nav.setRoot(HomePage);
    });
  }

  goToHomePage(){
    console.log("Inside goToHomePage");
    this.nav.setRoot(HomePage);
  }
}

