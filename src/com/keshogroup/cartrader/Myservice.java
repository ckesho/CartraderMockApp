package com.keshogroup.cartrader;






import com.keshogroup.cartrader.MainActivitycartrader;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.IntentService;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.*;

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
		  
		  JSONArray jarray=null; JSONArray jarray2=null;
		  JSONObject jobj=null;
		  JSONTokener jtoken=null;
		  String array[]= new String[400];
		  String gotaction; String gotaction2;
		  String gotaction3, sresult;
		  gotaction=intent.getAction(); gotaction2=intent.getAction(); gotaction3=intent.getAction();
		  int code =0;
		  int length=0;
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
			  String smethod= "GET";//PUT? or GET? or POST
			  //String surl= "https://www.google.com/search?q=weather+atlanta&ie=utf-8&oe=utf-8";
			  //String surl= "https://www.facebook.com/dialog/feed?app_id=145634995501895&display=popup&caption=This%20isA%20Test&link=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F&redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer";
			  
			  //basic GET method layout GET http://api.wunderground.com/api/852ddd19c6a16593/features/settings(optional)/q/query.format
			  // Features:
			  /*
    			geolookup 	Returns the the city name, zip code / postal code, latitude-longitude coordinates and nearby personal weather stations.
				autocomplete 	Returns a list of locations or hurricanes which match against a partial query.
				conditions		Returns Current conditions
				forecast		Returns 3-day forecast summary
				almanac 	Historical average temperature for today
				astronomy 	Returns the moon phase, sunrise and sunset times.
				
								
				query
				    The location for which you want weather information. Examples:
				    CA/San_Francisco	US state/city
				    60290				US zipcode
				    Australia/Sydney	country/city
				    37.8,-122.4			latitude,longitude
				    KJFK				airport code
				    pws:KCASANFR70		PWS id
				    autoip				AutoIP address location
				    autoip.json?geo_ip=38.102.136.138	specific IP address location
				format
				    json, or xml
				
				    Output format.
				
				    For JSONP, you may add ?callback=your_js_callback_function to the request URL
				*/
			  String query= "/q/30328"; String format=".json"; String web = "http://api.wunderground.com/api";
			  String please= "/852ddd19c6a16593"; String feature= "/conditions"; 
			  String surl= web+please+feature+query+format;
			  //String surl="http://api.wunderground.com/api/852ddd19c6a16593/forecast/geolookup/conditions/q/CA/San_Francisco.json";//"http://api.wunderground.com/api/852ddd19c6a16593/features/settings/q/query.format";
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
			  gotaction2= f.getResponseMessage();
			  code= f.getResponseCode();
			  
			  length=is.available();
			  
			  br= new BufferedReader(new InputStreamReader(is));
			  
			  /*
			   * invalid code
			   *
			  while (!br.ready()){
				wait(100);  
			  }
			  */

			  gotaction=br.readLine();//toString();
			  //br.mark(15);
			  //br.reset();
			  //gotaction="c"+br.readLine()+br.readLine()+br.readLine()+"c"+br.readLine()+"c";
			  int cnt=0;
			  while(br.read()!=-1){
				  //This pulls every potential line inside the buffer. 
				  //This addresses websites that send info line by line;
				  cnt++;
				  if(cnt==51){//51 is the line with temperature information
					  gotaction3=br.readLine();
					  //gotaction=gotaction+cnt+ gotaction3;//removed count from string
					  gotaction=gotaction+ gotaction3;
					  cnt++;
				  }
				  gotaction=gotaction+ br.readLine();
				  //gotaction=gotaction+cnt+ br.readLine();//removed count from string
				  
			  }//end while
			  
			  //jobj= (JSONObject) new JSONTokener(gotaction3).nextValue();
			  //jarray= new JSONArray(gotaction3);
			  //jarray= (JSONArray)new JSONTokener(gotaction).nextValue();
			  //jarray2= new JSONArray(jobj.names());
			  jtoken=new JSONTokener(gotaction);
			  //sresult=(String)jtoken.nextValue();
			  //Log.i("JSON", sresult);
			  
			  int cnt2=0;
			  while (jtoken.more()){
			  array[cnt2]=(String)jtoken.nextValue();
			  //Log.i("JSON", array[cnt2]);
			  jtoken.nextString('"');//fastforward to next string value by moving to \"
			  jtoken.back();//back up one char so that \" can be properly consumed with "nextValue" call
			  cnt2++;
			  }
			  
			  /*
			  while (jtoken.more()){
			  sresult=(String)jtoken.nextValue();
			  Log.i("JSON", sresult);
			  jtoken.nextString('"');//fastforward to next string value by moving to \"
			  jtoken.back();//back up one char so that \" can be properly consumed with "nextValue" call
			  }
			  */
			  
			  //jobj= (JSONObject) jtoken.nextValue();
			  //JSONObject a[]= new JSONObject[101];
			  
			  //a[10]=jarray2.getJSONObject(0);
			  //Log.i("JSON", "jobj= "+jobj.toString()+"length of jobj= "+jobj.length() +", jarray="+ jarray.getString(51)+"jarray.length"+ jarray.length()+", jarray2=" + jarray2.getString(14)+"jarray2.length"+ jarray2.length()+"j2 tostring"+jarray2.toString()+ ", a10="+a[10].toString());
			  //Log.i("JSON", jtoken.toString());
			  
			  sresult="results";
			  intent.putExtra(sresult, gotaction);
			  Log.i("popfly", "http sent, intent return");
			  
			  
			  
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
			  String str= " " +networkready1+ networkready2+ gotaction3;
			  CharSequence tickerText= str.subSequence(0, str.length());
			  
			  noticebuilder.setTicker(tickerText);
			  noticebuilder.setSmallIcon(com.keshogroup.cartrader.R.drawable.autotraderbw);
			  noticebuilder.setContentTitle(tickerText);
			  //noticebuilder.setContentText("background service, facebook update");
			  noticebuilder.setContentText(gotaction3);
			  noticebuilder.setContentIntent(null); //no intent needed for test
			  noticebuilder.setAutoCancel(true);
			  notice= noticebuilder.build();
			 // notice= new Notification.Builder(this).build();
			  
			  NotificationManager nm;
			  nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//attach to the system service
			  nm.notify(29, notice); //send out notice with notification manager
			  
			  
			  
			  Log.i("popfly", "code" + code+", length= "+length+", responsecode= "+ gotaction2+ ", output" + gotaction);
			  
		  /*
				Intent myintent2= new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
	           	startActivity(myintent2); //not needed
			 */
			  
			  Intent returnintent= new Intent();
			  returnintent.putExtra("com.keshogroup.cartrader.array", array);
			  returnintent.setAction("actionpack");
			  LocalBroadcastManager lbm = null;
			  //lbm=null;
			  //lbm.getInstance(this);
			  //lbm.sendBroadcast(returnintent);
			  //LocalBroadcastManager.getInstance(this).sendBroadcast(returnintent);
			  //lbm.getInstance(this).sendBroadcast(returnintent);

			  sendBroadcast(returnintent);
			  Log.i("JSON", "Broadcasr sent");
			  //startActivity(returnintent);

			  /*
			   * cannot place in shared preference because arrays not allowed
			  static final String REST_ARRAY = "restarray";
			  alanview.level.score = savedInstanceState.getInt(STATE_SCORE);
			  
		        savedInstanceState.putInt(STATE_SCORE, alanview.level.score);
		        savedInstanceState.putInt(STATE_LEVEL, alanview.level.currentnumber);
		        //bundle will not keep info if completely destroyed ie turn off phone so in addition use shardpref!
		        SharedPreferences sharedPrefcartrader = MainActivitycartrader.this.getPreferences(Context.MODE_PRIVATE);
		        //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		        SharedPreferences.Editor editor = sharedPref.edit();
		        editor.putInt(getString(R.string.string_score), alanview.level.score);
		        editor.putInt(getString(R.string.string_level), alanview.level.currentnumber);
		        //editor.putInt(getString(R.string.saved_high_score), newHighScore);
		        editor.commit();
			 */
		  
	  }//end of on Handle

	
	}