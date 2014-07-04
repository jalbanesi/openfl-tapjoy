package ilmare.extensions.events;

import openfl.events.Event;

/**
 * ...
 * @author Juan Ignacio Albanesi
 */
class TapjoyEvent extends Event
{
	public static inline var CONNECT_SUCCESS: String = "onConnectSuccess";
	public static inline var FULLSCREEN_AD_RESPONSE: String = "onGetFullScreenAdResponse";	
	public static inline var VIEW_CLOSED: String = "onViewClosed";	
	
	public function new(type: String, bubbles: Bool = false, cancelable: Bool = false) 
	{
		super(type, bubbles, cancelable);
	}
	
}