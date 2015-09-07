*************Cordova : Start_App*****************

By using this plugin you can start another app from app


Install this plugin using:

cordova plugin add com.jp.plugin.startapp



Remove Plugins :

cordova plugin remove com.jp.plugin.startapp



Put the below code in your javascript code: 

startapp.start("com.jp.tradegame", function(message) {
                console.log(message);
            },
            function(error) { 
                console.log(error);
            });


GitHub URL:   https://github.com/jaypratapsingh/StartApp

npm url :     https://www.npmjs.com/package/start_app