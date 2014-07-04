package org.haxe.extension;
// REPLACE_LICENSE_HEADER

/*
java implementation of the s3eTapjoy extension.

Add android-specific functionality here.

These functions are called via JNI from native code.
 */
/*
 * NOTE: This file was originally written by the extension builder, but will not
 * be overwritten (unless --force is specified) and is intended to be modified.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.tapjoy.TJError;
import com.tapjoy.TJEvent;
import com.tapjoy.TJEventCallback;
import com.tapjoy.TJEventManager;
import com.tapjoy.TJEventRequest;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnectNotifier;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyFullScreenAdNotifier;
import com.tapjoy.TapjoyViewNotifier;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyVideoNotifier;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyConnectFlag;

// For banners.
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

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
public class Tapjoy extends Extension //implements TJEventCallback
{
	public static LinearLayout linearLayout = null;
	
	public static View bannerView = null;
	static int bannerX;
	static int bannerY;
	static boolean hideBanner;
	
	private static HaxeObject _callback;
	
	public static final int ERROR_NONE			= 0;
	public static final int ERROR_NETWORK_ERROR = 5000;
	public static final int ERROR_NO_CONTENT	= 5001;
	
	public static final String TAG = "TAPJOY Extension";
	
	/*private static Tapjoy _instance = null;	
	public static Tapjoy getInstance()
	{
		
		if(_instance == null)
			_instance = new Tapjoy();

		return _instance;
	}*/

	
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


	/* TAPJOY */
	
	public static void TapjoySetCallBack(HaxeObject callback)
	{
		_callback = callback;
		
		TapjoyConnect.getTapjoyConnectInstance().setTapjoyViewNotifier(new TapjoyViewNotifier()
		{
			@Override
			public void viewWillOpen(int viewType)
			{

			}

			@Override
			public void viewWillClose(int viewType)
			{

			}

			@Override
			public void viewDidOpen(int viewType)
			{

			}

			@Override
			public void viewDidClose(int viewType)
			{
				_callback.call("onViewClosed", new Object[] { "" } );				
			}
		});		
	}	
	
	static public int TapjoyEnableLogging(boolean enable)
	{
		TapjoyLog.enableLogging(enable);
		return 0;
	}	
	
	static public void TapjoyRequestConnect(String appID, String secretKey)
	{
		Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
		connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
		
		// NOTE: This is the only step required if you're an advertiser.
		TapjoyConnect.requestTapjoyConnect(Extension.mainContext, appID, secretKey, connectFlags, new TapjoyConnectNotifier()
		{
			@Override
			public void connectSuccess() {
				Log.i(TAG, "connectSuccess");
				_callback.call("onConnectSuccess", new Object[] { "" } );
			}

			@Override
			public void connectFail() {
				Log.e(TAG, "connectFail");
				_callback.call("onConnectFail", new Object[] { "" } );
			}
		});		
	}
	
	static public void TapjoyActionComplete(String actionID)
	{
		TapjoyConnect.getTapjoyConnectInstance().actionComplete(actionID);
	}
	
	static public void TapjoySetUserID(String userID)
	{
		TapjoyConnect.getTapjoyConnectInstance().setUserID(userID);		
	}
	
	static public void TapjoyShowOffers()
	{
		TapjoyConnect.getTapjoyConnectInstance().showOffers();
	}
	
	static public void TapjoyShowOffersWithCurrenyID(String currencyID, boolean enableCurrencySelector)
	{
		TapjoyConnect.getTapjoyConnectInstance().showOffersWithCurrencyID(currencyID, enableCurrencySelector);		
	}
	
	static public void TapjoyGetTapPoints()
	{
		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(new TapjoyNotifier()
		{
			@Override
			public void getUpdatePointsFailed(String error)
			{
				Log.e(TAG, error);
				_callback.call("onGetUpdatePointsFailed", new Object[] { error } );				
			}
			
			@Override
			public void getUpdatePoints(String currencyName, int pointTotal)
			{
				Log.i(TAG, "getUpdatePoints " + currencyName + ": " + pointTotal);
				_callback.call("onGetUpdatePoints", new Object[] { currencyName, pointTotal } );				
			}
		});		
	}
	
	static public void TapjoySpendTapPoints(int amount)
	{
		TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(amount, new TapjoySpendPointsNotifier()
		{
			@Override
			public void getSpendPointsResponseFailed(String error)
			{
				Log.e(TAG, error);
				_callback.call("onGetSpendPointsResponseFailed", new Object[] { error } );
			}
			
			@Override
			public void getSpendPointsResponse(String currencyName, int pointTotal)
			{
				Log.i(TAG, "getSpendPointsResponse " + currencyName + ": " + pointTotal);
				_callback.call("onGetSpendPointsResponse", new Object[] { currencyName, pointTotal } );				
			}
		});
		
	}
	
	static public void TapjoyAwardTapPoints(int amount)
	{
		TapjoyConnect.getTapjoyConnectInstance().awardTapPoints(amount, new TapjoyAwardPointsNotifier()
		{					
			@Override
			public void getAwardPointsResponseFailed(String error)
			{
				Log.e(TAG, error);
				_callback.call("onGetAwardPointsResponseFailed", new Object[] { error } );
			}
			
			@Override
			public void getAwardPointsResponse(String currencyName, int pointTotal)
			{
				Log.i(TAG, "getAwardPointsResponse " + currencyName + ": " + pointTotal);
				_callback.call("onGetAwardPointsResponse", new Object[] { currencyName, pointTotal } );		
			}
		});
	}
	
	static public void TapjoyGetFullScreenAd()
	{
		TapjoyConnect.getTapjoyConnectInstance().getFullScreenAd(new TapjoyFullScreenAdNotifier()
		{
			@Override
			public void getFullScreenAdResponseFailed(int error)
			{
				String sError = Integer.toString(error);
				
				Log.e(TAG, sError);
				_callback.call("onGetFullScreenAdResponseFailed", new Object[] { sError } );
			}
			
			@Override
			public void getFullScreenAdResponse()
			{
				Log.i(TAG, "getFullScreenAdResponse");
				_callback.call("onGetFullScreenAdResponse", new Object[] { "" } );
			}
		});
	}
	
	static public void TapjoyGetFullScreenAdWithCurrencyID(String currencyID)
	{
		TapjoyConnect.getTapjoyConnectInstance().getFullScreenAdWithCurrencyID(currencyID, new TapjoyFullScreenAdNotifier()
		{
			@Override
			public void getFullScreenAdResponseFailed(int error)
			{
				String sError = Integer.toString(error);
				
				Log.e(TAG, sError);
				_callback.call("onGetFullScreenAdResponseFailed", new Object[] { sError } );
			}
			
			@Override
			public void getFullScreenAdResponse()
			{
				Log.i(TAG, "getFullScreenAdResponse");
				_callback.call("onGetFullScreenAdResponse", new Object[] { "" } );
			}
		});
	}
	
	static public void TapjoyShowFullScreenAd()
	{
		TapjoyConnect.getTapjoyConnectInstance().showFullScreenAd();		
	}
	
/*	public int TapjoyGetDisplayAd(String adContentSize, boolean shouldRefreshAd)
	{
		hideBanner = false;
		TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(adContentSize);
		TapjoyConnect.getTapjoyConnectInstance().enableBannerAdAutoRefresh(shouldRefreshAd);
		TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(this);
		return 0;
	}
	public int TapjoyGetDisplayAdWithCurrencyID(String currencyID, String adContentSize, boolean shouldRefreshAd)
	{
		hideBanner = false;
		TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(adContentSize);
		TapjoyConnect.getTapjoyConnectInstance().enableBannerAdAutoRefresh(shouldRefreshAd);
		TapjoyConnect.getTapjoyConnectInstance().getDisplayAdWithCurrencyID(currencyID, this);
		return 0;
	}
	public int TapjoySetDisplayAdView(int left, int top, int width, int height)
	{
		bannerX = left;
		bannerY = top;
		
		if (!hideBanner)
			Extension.mainActivity.runOnUiThread(new UpdateBannerAd(bannerX, bannerY));
		return 0;
	}
	public int TapjoyShowDisplayAd()
	{
		hideBanner = false;
		Extension.mainActivity.runOnUiThread(new UpdateBannerAd(bannerX, bannerY));
		return 0;
	}
	public int TapjoyHideDisplayAd()
	{
		hideBanner = true;
		TapjoyConnect.getTapjoyConnectInstance().enableBannerAdAutoRefresh(false);
		
		Extension.mainActivity.runOnUiThread(new Runnable()
			{
				public void run() 
				{
					if (linearLayout != null)
					{
						linearLayout.removeAllViews();
					}
				}
			});		
		return 0;
	}
	public int TapjoySetEarnedTapPointsNotifier()
	{
		TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(this);
		return 0;
	}
	public int TapjoyInitVideoAd()
	{
		TapjoyConnect.getTapjoyConnectInstance().initVideoAd(this);
		return 0;
	}
	public int TapjoySetVideoCacheCount(int count)
	{
		TapjoyConnect.getTapjoyConnectInstance().setVideoCacheCount(count);
		return 0;
	}
	public int TapjoyEnableVideoCache(boolean enable)
	{
		TapjoyConnect.getTapjoyConnectInstance().enableVideoCache(enable);
		return 0;
	}
	public int TapjoyEnableBannerAdAutoRefresh(boolean enable)
	{
		TapjoyConnect.getTapjoyConnectInstance().enableBannerAdAutoRefresh(enable);
		return 0;
	}
	
	//================================================================================
	// CALLBACK Methods
	//================================================================================

	
	// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	// It is the callback method which contains the currency and points data.
	public void getUpdatePoints(String currencyName, int pointTotal)
	{
		TapjoyLog.i("TAJPJOY", "getUpdatePoints: " + pointTotal);
		GetTapPointsCallback(pointTotal, ERROR_NONE);
	}
	
	// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	// It is the callback method which contains the currency and points data.
	public void getUpdatePointsFailed(String error)
	{
		GetTapPointsCallback(0, ERROR_NETWORK_ERROR);
	}
	
	// Notifier for receiving the featured app data on a successful connect.
	public void getFullScreenAdResponse(TapjoyFullScreenAdObject featuredApObject)
	{
		TapjoyLog.i("TAJPJOY", "getFullScreenAdResponse");
		FullScreenAdCallback((TapjoyFullScreenAdObject)featuredApObject, ERROR_NONE);
	}

	// Notifier for when there is an error or no featured app to display.
	public void getFullScreenAdResponseFailed(String error)
	{
		FullScreenAdCallback(null, ERROR_NO_CONTENT);
	}

	// Notifier for when spending virtual currency succeeds.
	public void getSpendPointsResponse(String currencyName, int pointTotal)
	{
		TapjoyLog.i("TAJPJOY", "getSpendPointsResponse: " + pointTotal);
		SpendTapPointsCallback(pointTotal, ERROR_NONE);
	}

	// Notifier for when spending virtual currency fails.
	public void getSpendPointsResponseFailed(String error)
	{
		SpendTapPointsCallback(0, ERROR_NETWORK_ERROR);
	}

	public void getDisplayAdResponse(View view)
	{
		TapjoyLog.i("TAJPJOY", "getDisplayAdResponse");
		bannerView = view; 
		DisplayAdCallback(ERROR_NONE);
		
		Extension.mainActivity.runOnUiThread(new UpdateBannerAd(bannerX, bannerY));
	}

	public void getDisplayAdResponseFailed(String error)
	{
		DisplayAdCallback(ERROR_NO_CONTENT);
	}
	
	// Helper method to add/update the banner ad.
	private class UpdateBannerAd implements Runnable
	{
		int x;
		int y;
		
		public UpdateBannerAd(int banner_x, int banner_y)
		{
			x = banner_x;
			y = banner_y;
		}
		
		public void run()
		{
			try
			{
				Context myContext = (Context) Extension.mainActivity;
				
				// Get the screen size.
				WindowManager mW = (WindowManager)Extension.mainActivity.getSystemService(Context.WINDOW_SERVICE);
				int screenWidth = mW.getDefaultDisplay().getWidth();
				int screenHeight = mW.getDefaultDisplay().getHeight();
			
				//bannerView = TapjoyConnect.getTapjoyConnectInstance().getBannerAdView();
			
				// Null check.
				if (bannerView == null)
				{
					return;
				}
					
				int ad_width = bannerView.getLayoutParams().width;
				int ad_height = bannerView.getLayoutParams().height;
			
				// Resize banner if it's too big.
				if (screenWidth < ad_width)
				{
					// Using screen width, but substitute for the any width.
					int desired_width = screenWidth;
			
					// Resize banner to desired width and keep aspect ratio.
					LayoutParams layout = new LayoutParams(desired_width, (desired_width*ad_height)/ad_width);
					bannerView.setLayoutParams(layout);
				}
			
				if (linearLayout != null)
				{
					linearLayout.removeAllViews();
				}
			
				linearLayout = new LinearLayout(myContext);
				linearLayout.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, screenHeight));
			
				// Use padding to set the x/y coordinates.
				linearLayout.setPadding(x, y, 0, 0);
				linearLayout.addView(bannerView);
			
				Extension.mainActivity.getWindow().addContentView(linearLayout, new ViewGroup.LayoutParams(screenWidth, screenHeight));
			}
			catch (Exception e)
			{
				Log.e("TapjoyPluginActivity", "exception adding banner: " + e.toString());
			}
		}
	}

	public void getAwardPointsResponse(String currencyName, int pointTotal)
	{
		TapjoyLog.i("TAJPJOY", "getAwardPointsResponse: " + pointTotal);
		AwardTapPointsCallback(pointTotal, ERROR_NONE);
	}

	public void getAwardPointsResponseFailed(String error)
	{
		AwardTapPointsCallback(0, ERROR_NETWORK_ERROR);
	}
	
	public void earnedTapPoints(int amount)
	{
		TapjoyLog.i("TAJPJOY", "earnedTapPoints: " + amount);
		EarnedTapPointsCallback(amount, ERROR_NONE);
	}
	
	public void videoReady()
	{
		// See s3eTapjoyVideoStatus for the codes, 0 = video ready
		VideoStatusCallback(0, ERROR_NONE);
	}

	public void videoError(int statusCode)
	{
		// See s3eTapjoyVideoStatus for the codes, 2 = start of error codes
		VideoStatusCallback(statusCode+1, ERROR_NONE);
	}

	public void videoComplete()
	{
		// See s3eTapjoyVideoStatus for the codes, 1 = video complete
		VideoStatusCallback(1, ERROR_NONE);
	}
	
	
	// AIRPLAY CALLBACK METHODS 
    public native void GetTapPointsCallback(int points, int error);
    public native void SpendTapPointsCallback(int points, int error);
    public native void AwardTapPointsCallback(int points, int error);
    public native void FullScreenAdCallback(Object featuredAppObject, int error);
	public native void EarnedTapPointsCallback(int points, int error);
	public native void VideoStatusCallback(int status, int error);
	public native void DisplayAdCallback(int error);*/
}
