package ilmare.extensions;
import openfl.events.Event;
import openfl.events.EventDispatcher;
import openfl.events.IEventDispatcher;

#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#end

#if (android && openfl)
import openfl.utils.JNI;
#end


@:build( ShortCuts.mirrors( ) )
class Tapjoy implements IEventDispatcher
{
	var _callback: TapjoyCallback;
	private static var _setCallback : Dynamic = null;
	
	/*@JNI("org.haxe.extension.Tapjoy","getInstance")
	static public function getJNIInstance(): Tapjoy { }	*/

	static var _instance: Tapjoy;
	public static var instance(get, never): Tapjoy;
	static function get_instance(): Tapjoy
	{
		if (_instance == null)
			_instance = new Tapjoy();
		return _instance;
	}
	
	public function new()
	{
		_callback = new TapjoyCallback();
	}

/*	@JNI("org.haxe.extension.Tapjoy", "TapjoySetCallBack")
	static function _setCallback(callbackObject: TapjoyCallback):Void { }*/
	
	@JNI("org.haxe.extension.Tapjoy", "TapjoyRequestConnect")
	static function _connect(appID: String, secretKey: String): Void { }
	
	@JNI("org.haxe.extension.Tapjoy", "TapjoyShowOffers")
	static function _showOffers(): Void { }

	@JNI("org.haxe.extension.Tapjoy", "TapjoyGetTapPoints")
	static function _getPoints(): Void { }

	@JNI("org.haxe.extension.Tapjoy", "TapjoySpendTapPoints")
	static function _spendPoints(amount: Int): Void { }

	@JNI("org.haxe.extension.Tapjoy", "TapjoyAwardTapPoints")
	static function _awardPoints(amount: Int): Void { }

	@JNI("org.haxe.extension.Tapjoy", "TapjoyShowFullScreenAd")
	static function _showFullScreenAd(): Void { }
	
	public function connect(appID: String, secretKey: String): Void
	{
		_connect(appID, secretKey);	
		if(_setCallback == null)
		{
			_setCallback = openfl.utils.JNI.createStaticMethod("org.haxe.extension.Tapjoy", "TapjoySetCallBack", "(Lorg/haxe/lime/HaxeObject;)V");
		}
		
		_setCallback(_callback);
	}
	
	public function showOffers(): Void
	{
		_showOffers();
	}
	
	public function retrievePoints(): Void
	{
		_getPoints();
	}

	public function spendPoints(amount: Int): Void
	{
		_spendPoints(amount);
	}

	public function awardPoints(amount: Int): Void
	{
		_awardPoints(amount);
	}

	public function showFullscreenAd(): Void
	{
		_showFullScreenAd();
	}

	
	
	// Event Listener Interface
	public function addEventListener (type:String, listener:Dynamic->Void, useCapture:Bool = false, priority:Int = 0, useWeakReference:Bool = false):Void
	{
		_callback.addEventListener(type, listener, useCapture, priority, useWeakReference);
	}
	
	public function dispatchEvent (event:Event):Bool
	{
		return _callback.dispatchEvent(event);
	}
	
	public function hasEventListener (type:String):Bool
	{
		return _callback.hasEventListener(type);
	}
	
	public function removeEventListener (type:String, listener:Dynamic->Void, useCapture:Bool = false):Void
	{
		_callback.removeEventListener(type, listener, useCapture);
	}
	
	
	public function willTrigger (type:String):Bool
	{
		return _callback.willTrigger(type);
	}
	
}