package ilmare.extensions.events;

import openfl.events.Event;

/**
 * ...
 * @author Juan Ignacio Albanesi
 */
class TapjoyPointsEvent extends Event
{
	public static inline var POINTS_UPDATED: String = "onGetUpdatePoints";
	public static inline var POINTS_SPENT: String = "onGetSpendPointsResponse";
	public static inline var POINTS_AWARDED: String = "onGetAwardPointsResponse";
	
	var _currencyName: String;
	var _points: Int;
	
	public var currencyName(get, never): String;
	public var points(get, never): Int;
	
	public function new(type: String, currencyName: String, points: Int, bubbles: Bool = false, cancelable: Bool = false) 
	{
		super(type, bubbles, cancelable);
		
		_currencyName = currencyName;
		_points = points;
	}
	
	function get_currencyName(): String
	{
		return _currencyName;
	}
	
	function get_points(): Int
	{
		return _points;
	}
	
}