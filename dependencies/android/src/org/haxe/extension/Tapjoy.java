package org.haxe.extension;


import android.app.Activity;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tapjoy.TJError;
import com.tapjoy.TJEvent;
import com.tapjoy.TJEventCallback;
import com.tapjoy.TJEventRequest;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyFullScreenAdNotifier;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoyOffersNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyVideoNotifier;
import com.tapjoy.TapjoyViewNotifier;
import com.tapjoy.TapjoyViewType;


/* 
	You can use the Android Extension class in order to hook
	into the Android activity lifecycle. This is not required
	for standard Java code, this is designed for when you need
	deeper integration.
	
	You can access additional references from the Extension class,
	depending on your needs:
	
	- Extension.assetManager (android.content.res.AssetManager)
	- Extension.callbackHandler (android.os.Handler)
	- Extension.mainActivity (android.app.Activity)
	- Extension.mainContext (android.content.Context)
	- Extension.mainView (android.view.View)
	
	You can also make references to static or instance methods
	and properties on Java classes. These classes can be included 
	as single files using <java path="to/File.java" /> within your
	project, or use the full Android Library Project format (such
	as this example) in order to include your own AndroidManifest
	data, additional dependencies, etc.
	
	These are also optional, though this example shows a static
	function for performing a single task, like returning a value
	back to Haxe from Java.
*/
public class Tapjoy extends Extension 
{
	
	View adView;
	RelativeLayout relativeLayout;
	LinearLayout adLinearLayout;
	
	boolean earnedPoints = false;
	
	public static int sampleMethod (int inputValue) 
	{
		
		TapjoyConnect.getTapjoyConnectInstance().enableDisplayAdAutoRefresh(true);
		TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(Extension.mainActivity, new TapjoyDisplayAdNotifier()
		{
			@Override
			public void getDisplayAdResponseFailed(String error)
			{
				showPopupMessage("getDisplayAd error: " + error);				
			}
			
			@Override
			public void getDisplayAdResponse(View view)
			{
				// Using screen width, but substitute for the any width.
				int desired_width = adLinearLayout.getMeasuredWidth();
				
				// Scale the display ad to fit incase the width is smaller than the display ad width.
				adView = scaleDisplayAd(view, desired_width);
				
				updateDisplayAdInUI(adView);
				showPopupMessage("getDisplayAd success");				
			}
		});
		
		
		return inputValue * 100;
		
	}
	
	
	/**
	 * Called when an activity you launched exits, giving you the requestCode 
	 * you started it with, the resultCode it returned, and any additional data 
	 * from it.
	 */
	public boolean onActivityResult (int requestCode, int resultCode, Intent data) 
	{
		
		return true;
		
	}
	
	
	/**
	 * Called when the activity is starting.
	 */
	public void onCreate (Bundle savedInstanceState) 
	{
		// OPTIONAL: For custom startup flags.
		Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
		connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
		
		// If you are not using Tapjoy Managed currency, you would set your own user ID here.
		//	connectFlags.put(TapjoyConnectFlag.USER_ID, "A_UNIQUE_USER_ID");
	   
		// You can also set your event segmentation parameters here.
		//  Hashtable<String, String> segmentationParams = new Hashtable<String, String>();
		//  segmentationParams.put("iap", "true");
		//  connectFlags.put(TapjoyConnectFlag.SEGMENTATION_PARAMS, segmentationParams);
		
		// Connect with the Tapjoy server.  Call this when the application first starts.
		// REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
		String tapjoyAppID = "06a6af7b-ecf0-46c1-b39b-fcd7108e248e";
		// REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
		String tapjoySecretKey = "NlMByzlSsqLxSSMkfbgt";
		
		// NOTE: This is the only step required if you're an advertiser.
		TapjoyConnect.requestTapjoyConnect(Extension.mainContext, tapjoyAppID, tapjoySecretKey, connectFlags, new TapjoyConnectNotifier()
		{
			@Override
			public void connectSuccess() {
				onConnectSuccess();
			}

			@Override
			public void connectFail() {
				onConnectFail();
			}
		});		
		
	}
	
	
	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	public void onDestroy () 
	{
		
		
		
	}
	
	
	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed.
	 */
	public void onPause () 
	{
		
		
		
	}
	
	
	/**
	 * Called after {@link #onStop} when the current activity is being 
	 * re-displayed to the user (the user has navigated back to it).
	 */
	public void onRestart () 
	{
		
		
		
	}
	
	
	/**
	 * Called after {@link #onRestart}, or {@link #onPause}, for your activity 
	 * to start interacting with the user.
	 */
	public void onResume () 
	{
		
		
		
	}
	
	
	/**
	 * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when  
	 * the activity had been stopped, but is now again being displayed to the 
	 * user.
	 */
	public void onStart () 
	{
		
		
		
	}
	
	
	/**
	 * Called when the activity is no longer visible to the user, because 
	 * another activity has been resumed and is covering this one. 
	 */
	public void onStop () 
	{
		
		
		
	}
	
	
	////////////////////////////////////
	
	public void onConnectSuccess()
	{
		// NOTE:  The get/spend/awardTapPoints methods will only work if your virtual currency
		// is managed by Tapjoy.
		//
		// For NON-MANAGED virtual currency, TapjoyConnect.getTapjoyConnectInsance().setUserID(...)
		// must be called after requestTapjoyConnect.

		// Get notifications whenever Tapjoy currency is earned.
		TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier()
		{
			@Override
			public void earnedTapPoints(int amount)
			{
				earnedPoints = true;
				//updateTextInUI("You've just earned " + amount + " Tap Points!");
				showPopupMessage("You've just earned " + amount + " Tap Points!");
			}
		});
		
		// Get notifications when Tapjoy views open or close.
		TapjoyConnect.getTapjoyConnectInstance().setTapjoyViewNotifier(new TapjoyViewNotifier()
		{
			@Override
			public void viewWillOpen(int viewType)
			{
				TapjoyLog.i(TAG, getViewName(viewType) + " is about to open");
			}
			
			@Override
			public void viewWillClose(int viewType)
			{
				TapjoyLog.i(TAG, getViewName(viewType) + " is about to close");
			}
			
			@Override
			public void viewDidOpen(int viewType)
			{
				TapjoyLog.i(TAG, getViewName(viewType) + " did open");
			}
			
			@Override
			public void viewDidClose(int viewType)
			{
				TapjoyLog.i(TAG, getViewName(viewType) + " did close");
				
				// Best Practice: We recommend calling getTapPoints as often as possible so the user’s balance is always up-to-date.
				TapjoyConnect.getTapjoyConnectInstance().getTapPoints(TapjoyEasyApp.this);
			}
		});
		
		// Get notifications on video start, complete and error
		TapjoyConnect.getTapjoyConnectInstance().setVideoNotifier(new TapjoyVideoNotifier() {

			@Override
			public void videoStart() {
				Log.i(TAG, "video has started");
			}

			@Override
			public void videoError(int statusCode) {
				Log.i(TAG, "there was an error with the video: " + statusCode);
			}

			@Override
			public void videoComplete() {
				Log.i(TAG, "video has completed");
				
				// Best Practice: We recommend calling getTapPoints as often as possible so the user’s balance is always up-to-date.
				TapjoyConnect.getTapjoyConnectInstance().getTapPoints(TapjoyEasyApp.this);
			}
			
		});
	}
	
	public void onConnectFail()
	{
		Log.e(TAG, "Tapjoy connect call failed.");
		updateTextInUI("Tapjoy connect failed!");
	}

	private void showPopupMessage(final String text)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast toast = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show(); 
			}
		});
	}	
	
	/**
	 * Add the banner ad to our UI.
	 * @param view							Banner ad view.
	 */
	private void updateDisplayAdInUI(final View view)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				// Remove all subviews of our ad layout.
				adLinearLayout.removeAllViews();
				
				// Add the ad to our layout.
				adLinearLayout.addView(view);
			}
		});
	}	
	
}