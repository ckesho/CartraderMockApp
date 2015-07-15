package com.keshogroup.cartrader;

/*
 * Chris Kesho
 * 7/15/15
 * Cartrader - Autotrader mock app ver 1
 * keshoLLC-info@yahoo.com
 * 
 */

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;

import com.keshogroup.cartrader.Myservice;

import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import java.lang.String;



public class MainActivitycartrader extends Activity {
	
	//define views
	TextView tv2, tv3, tv4;
	CharSequence text2;
	String string2;
	SimpleAdapter simpleadapter;
	ListView lv1,lv3,lv4;
	ListAdapter listadapter;
	//ArrayAdapter aadapter;
	Button button1, button2, button3, button4, buttonback; 
	RelativeLayout main, searchlist;
	//Myservice myservice;
	//Myservice myservice= new Myservice("Myservice");
	//myservice.onHandleIntent(myintent);//works
	Intent myintent;
	Intent myintent2;
	
	//PendingIntent pi;
	//pi =new PendingIntent();
	ServiceConnection sc;
	String sa[] = new String[40];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_cartrader);
		
		myintent = new Intent(this, Myservice.class);
		myintent2= new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);//emulator has no camera
		 
		sa[0]="Dodge Caravan";      sa[4]="Dodge Caravan";        sa[5]="Dodge Caravan";
		sa[1]="Mazda 626";
		sa[2]="Honda CRV";
		sa[3]="Chevorlet Corvette";
		sa[6]="Dodge intrepid";      sa[15]="Dodge Caravan";        sa[24]="Dodge Caravan";
		sa[7]="Dodge charger";      sa[16]="Lincoln Towncar";        sa[25]="Nissan Maxima";
		sa[8]="Dodge neon";      sa[17]="Dodge Caravan";        sa[26]="Dodge Caravan";
		sa[9]="Dodge stingray";      sa[18]="Dodge Caravan";        sa[27]="Dodge Caravan";
		sa[10]="Dodge viper";      sa[19]="Dodge Caravan";        sa[28]="Kia Sorenton";
		sa[11]="Dodge ram";      sa[20]="Dodge neon";        sa[28]="Dodge Caravan";
		sa[12]="Toyota corolla";      sa[21]="Dodge Caravan";        sa[29]="Dodge Caravan";
		sa[13]="Toyota camry";      sa[22]="Lexus is250";        sa[30]="Lexus ES 350";
		sa[14]="Dodge Caravan";      sa[23]="Dodge Caravan";        sa[31]="Dodge Caravan";
		
		ArrayAdapter<String> aadapter = new ArrayAdapter<String>(this, R.layout.lists, sa);
		//ArrayAdapter aadapter = new ArrayAdapter(this, R.string.app_name);
		//ArrayAdapter aadapter = new ArrayAdapter(this, R.id.l2tv1);
		
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		lv1 = (ListView) findViewById(R.id.listView1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		buttonback = (Button) findViewById(R.id.buttonback);
		main = (RelativeLayout) findViewById(R.id.main);
		searchlist = (RelativeLayout) findViewById(R.id.searchlist);
		//ListAdapter la = new ListAdapter(this.setContentView(R.array.car_database));
		//ListAdapter la = new ListAdapter(this, R.array.car_database);
		
		string2= "mystring";
		text2= (CharSequence) string2;
		
		//tv4.setText(text2);
		//lv1.addHeaderView(tv4);
		lv1.setAdapter(aadapter);
		//tv3.setText(getPackageName());
		

		
		
		sc = new ServiceConnection(){

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}};
		
		
		
		


		
		
		//*************Define buttons onclick actions	         
		
        
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click


            	
            	//myservice.startService(myintent3);//crashes
            	
            	startService(myintent);
            	//bindService(myintent, sc, 0);

            	
            	
            	text2= (CharSequence)myintent.getPackage();
            	//tv2.setText(text2);

            	Log.i("popfly", "sent intent");
           	 }
            });        
        
        
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
           	stopService(myintent);
           	
           	//startActivity(myintent2); //not needed
    		
           	//*************
			  Notification notice;
			  Builder noticebuilder= new Builder(MainActivitycartrader.this);
			  String str= "New Car Alert";
			  String str2= "Visit autotrader.com";
			  CharSequence tickerText= str.subSequence(0, str.length());
			  
			  noticebuilder.setTicker(tickerText);
			  noticebuilder.setSmallIcon(com.keshogroup.cartrader.R.drawable.autotraderbw);
			  noticebuilder.setContentTitle(tickerText);
			  noticebuilder.setContentText(str2);
			  noticebuilder.setContentIntent(null); //no intent needed for test
			  noticebuilder.setAutoCancel(true);
			  notice= noticebuilder.build();
			 // notice= new Notification.Builder(this).build();
			  
			  NotificationManager nm;
			  nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//attach to the system service
			  nm.notify(28, notice); //send out notice with notification manager
			  
           	//*************
  		  
			  button2.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP,2);
           	
           	 }
            }); 
        
        
        
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click


            	startActivity(myintent2);//camera
   
            	Log.i("popfly", "sent camera intent");
           	 }
            });        
        
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	//tv4.setText(text2);
            	sa[3]="button4";
            	searchlist.setVisibility(0);//0vis, 4invis, 8 gone
            	main.setVisibility(8);//0vis, 4invis, 8 gone

           	 }
            });       
        
        buttonback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	searchlist.setVisibility(8);//0vis, 4invis, 8 gone
            	main.setVisibility(0);//0vis, 4invis, 8 gone

           	 }
            });       
        
        
        
        
	}//end oncreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_cartrader, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
