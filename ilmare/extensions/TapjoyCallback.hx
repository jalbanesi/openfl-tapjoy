package ilmare.extensions;
import ilmare.extensions.events.TapjoyPointsEvent;
import ilmare.extensions.events.TapjoyErrorEvent;
import ilmare.extensions.events.TapjoyEvent;
import openfl.events.EventDispatcher;

/**
 * ...
 * @author Juan Ignacio Albanesi
 */
class TapjoyCallback extends EventDispatcher
{
	
	public function onConnectSuccess(): Void
	{
		dispatchEvent(new TapjoyEvent(TapjoyEvent.CONNECT_SUCCESS));
	}
	
	public function onConnectFail(): Void
	{
		dispatchEvent(new TapjoyErrorEvent(TapjoyErrorEvent.CONNECT_FAILED, "Connection Failed"));
	}
	
	public function onGetUpdatePointsFailed(error: String): Void 
	{ 
		dispatchEvent(new TapjoyErrorEvent(TapjoyErrorEvent.POINTS_UPDATE_FAILED, error));
	}
	
	public function onGetUpdatePoints(currencyName: String, pointTotal: Int): Void 
	{
		dispatchEvent(new TapjoyPointsEvent(TapjoyPointsEvent.POINTS_UPDATED, currencyName, pointTotal));
	}
	
	public function onGetSpendPointsResponseFailed(error: String): Void 
	{ 
		dispatchEvent(new TapjoyErrorEvent(TapjoyErrorEvent.POINTS_SPEND_FAILED, error));
	}
	
	public function onGetSpendPointsResponse(currencyName: String, pointTotal: Int): Void 
	{
		dispatchEvent(new TapjoyPointsEvent(TapjoyPointsEvent.POINTS_SPENT, currencyName, pointTotal));
	}
	
	public function onGetAwardPointsResponseFailed(error: String): Void 
	{ 
		dispatchEvent(new TapjoyErrorEvent(TapjoyErrorEvent.POINTS_AWARD_FAILED, error));
	}
	
	public function onGetAwardPointsResponse(currencyName: String, pointTotal: Int): Void 
	{
		dispatchEvent(new TapjoyPointsEvent(TapjoyPointsEvent.POINTS_AWARDED, currencyName, pointTotal));
	}
	
	public function onGetFullScreenAdResponseFailed(error: String): Void 
	{ 
		dispatchEvent(new TapjoyErrorEvent(TapjoyErrorEvent.FULLSCREEN_AD_RESPONSE_FAILED, error));
	}
	
	public function onGetFullScreenAdResponse(): Void
	{
		dispatchEvent(new TapjoyEvent(TapjoyEvent.FULLSCREEN_AD_RESPONSE));
	}
		
	public function onViewClosed(): Void
	{
		dispatchEvent(new TapjoyEvent(TapjoyEvent.VIEW_CLOSED));
	}
	
}