package ilmare.extensions;

#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#end

#if (android && openfl)
import openfl.utils.JNI;
#end


class Tapjoy {
	
	
	public static function sampleMethod (inputValue:Int):Int {
		
		#if (android && openfl)
		
		var resultJNI = tapjoy_sample_method_jni(inputValue);
		var resultNative = tapjoy_sample_method(inputValue);
		
		/*if (resultJNI != resultNative) {
			
			throw "Fuzzy math!";
			
		}*/
		
		return resultNative;
		
		#else
		
		return tapjoy_sample_method(inputValue);
		
		#end
		
	}
	
	
	private static var tapjoy_sample_method = Lib.load ("tapjoy", "tapjoy_sample_method", 1);
	
	#if (android && openfl)
	private static var tapjoy_sample_method_jni = JNI.createStaticMethod ("org.haxe.extension.Tapjoy", "sampleMethod", "(I)I");
	#end
	
	
}