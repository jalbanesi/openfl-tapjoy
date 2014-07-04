package ilmare.extensions.events;

import openfl.events.Event;

/**
 * ...
 * @author Juan Ignacio Albanesi
 */
class TapjoyErrorEvent extends Event
{
	public static inline var CONNECT_FAILED: String = "onConnectFail";
	public static inline var POINTS_UPDATE_FAILED: String = "onGetUpdatePointsFailed";
	public static inline var POINTS_SPEND_FAILED: String = "onGetSpendPointsResponseFailed";
	public static inline var POINTS_AWARD_FAILED: String = "onGetAwardPointsResponseFailed";
	public static inline var FULLSCREEN_AD_RESPONSE_FAILED: String = "onGetFullScreenAdResponseFailed";	
	
	var _error: String;
	
	public var error(get, never): String;
	
	public function new(type: String, error: String, bubbles: Bool = false, cancelable: Bool = false) 
	{
		super(type, bubbles, cancelable);
		
		_error = error;
	}
	
	function get_error(): String
	{
		return _error;
	}
	
}