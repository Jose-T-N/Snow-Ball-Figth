package com.jtn.snowballfigth.util;

public class Map {
	
	public static float map(double value ,double a,double b, double c, double d) {
	    // first map value from (a..b) to (0..1)
	    value = (value - a) / (b - a);
	    // then map it from (0..1) to (c..d) and return it
	    return (float)(c + value * (d - c));
	}
}
