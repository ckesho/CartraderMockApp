package com.keshogroup.cartrader;






import com.keshogroup.cartrader.MainActivitycartrader;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.app.IntentService;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Myservice extends IntentService{

	 /**
	   * A constructor is required, and must call the super IntentService(String)
	   * constructor with a name for the worker thread.
	   */

	public Myservice() {
		super("Myservice");
		// TODO Auto-generated constructor stub
		Log.i("popfly", "constructor ran");
	}
	
	//NO need to implement oncreate its handled by onhandle intent
	/*
public void onCreate(){
	Log.i("popfly", "service created");
}*/


	/*
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	  /**
	   * The IntentService calls this method from the default worker thread with
	   * the intent that started the service. When this method returns, IntentService
	   * stops the service, as appropriate.
	   */
	  @Override
	  protected void onHandleIntent(Intent intent) {
		  //receives info from intent to do work
		  //this is where your work is done
		  Log.i("popfly", "recieved intent");
		  
		  
		  String gotaction, sresult;
		  gotaction=intent.getAction();
		  
		  //check newtowrk connection
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//in case some network stuff is done
		  @SuppressWarnings("deprecation")
		boolean networkready1= cm.getBackgroundDataSetting();
		  boolean networkready2= cm.getActiveNetworkInfo() !=null;
		  if (networkready1==false || networkready2==false){
			  return;
			  }
		  else{
			  
			  HttpURLConnection f;
			  String smethod= "POST";//PUT? or GET? or POST
			  //String surl= "https://www.google.com/search?q=weather+atlanta&ie=utf-8&oe=utf-8";
			  String surl= "https://www.facebook.com/dialog/feed?app_id=145634995501895&display=popup&caption=This%20isA%20Test&link=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F&redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer";
			  OutputStream os = null;
			  BufferedReader br = null;
			  InputStream is;
			  try{
				  //client to server send
			  URL url = new URL(surl);
			  f= (HttpURLConnection) url.openConnection();
			  f.setRequestMethod(smethod);
			  //Server to client returned info
			  is=f.getInputStream();
			  br= new BufferedReader(new InputStreamReader(is));
			  gotaction=br.readLine();//toString();
			  sresult="results";
			  intent.putExtra(sresult, gotaction);
			  Log.i("popfly", "hhtp sent, intent return");
			  }
			  catch(Exception ex){
				  Log.i("popfly", "error"+ex); 
			  }
			  //finally{}
			  
			  
			  /*
			  https://www.facebook.com/dialog/feed?
				  app_id=145634995501895
				  &display=popup&caption=This%20isA%20Test 
				  //&link=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F//no link needed
				  &redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer
				  */
		  }
		  
		  
			  Notification notice;
			  Builder noticebuilder= new Builder(this);
			  String str= " " +networkready1+ networkready2+ " Autotrader fb login";
			  CharSequence tickerText= str.subSequence(0, str.length());
			  
			  noticebuilder.setTicker(tickerText);
			  noticebuilder.setSmallIcon(com.keshogroup.cartrader.R.drawable.autotraderbw);
			  noticebuilder.setContentTitle(tickerText);
			  noticebuilder.setContentText("background service, facebook update");
			  noticebuilder.setContentIntent(null); //no intent needed for test
			  noticebuilder.setAutoCancel(true);
			  notice= noticebuilder.build();
			 // notice= new Notification.Builder(this).build();
			  
			  NotificationManager nm;
			  nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//attach to the system service
			  nm.notify(29, notice); //send out notice with notification manager
			  
			  
			  
			  Log.i("popfly", gotaction);
			  
		  /*
				Intent myintent2= new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
	           	startActivity(myintent2); //not needed
			 */
			  
			  
			  
			 
		  
	  }

	
	}