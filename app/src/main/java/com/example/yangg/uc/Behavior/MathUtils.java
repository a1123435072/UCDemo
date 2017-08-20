package com.example.yangg.uc.Behavior;

/**
 * Created by sszz on 2017/2/21.
 */

public class MathUtils {
	static int constrain(int amount, int low, int high) {
		return amount < low ? low : (amount > high ? high : amount);
	}

	static float constrain(float amount, float low, float high) {
		return amount < low ? low : (amount > high ? high : amount);
	}
}
