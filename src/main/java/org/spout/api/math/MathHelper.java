/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, Spout LLC <http://www.spout.org/>
 * SpoutAPI is licensed under the Spout License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.api.math;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import javolution.context.StackContext;

/**
 * Class containing various mathematical functions
 */
public class MathHelper {
	/**
	 * A "close to zero" double epsilon value for use
	 */
	public static final double DBL_EPSILON = Double.longBitsToDouble(0x3cb0000000000000L);

	/**
	 * A "close to zero" float epsilon value for use
	 */
	public static final float FLT_EPSILON = Float.intBitsToFloat(0x34000000);

	public static final double PI = Math.PI;

	public static final double SQUARED_PI = PI * PI;

	public static final double HALF_PI = 0.5 * PI;

	public static final double QUARTER_PI = 0.5 * HALF_PI;

	public static final double TWO_PI = 2.0 * PI;

	public static final double THREE_PI_HALVES = TWO_PI - HALF_PI;

	public static final double DEGTORAD = PI / 180.0;

	public static final double RADTODEG = 180.0 / PI;

	public static final double SQRTOFTWO = Math.sqrt(2.0);

	public static final double HALF_SQRTOFTWO = 0.5 * SQRTOFTWO;

	/**
	 * Calculates the squared length of all axis offsets given
	 *
	 * @param values of the axis to get the squared length of
	 * @return the squared length
	 */
	public static double lengthSquared(double... values) {
		double rval = 0;
		for (double value : values) {
			rval += value * value;
		}
		return rval;
	}

	/**
	 * Calculates the length of all axis offsets given
	 *
	 * @param values of the axis to get the length of
	 * @return the length
	 */
	public static double length(double... values) {
		return Math.sqrt(lengthSquared(values));
	}
	

	/**
	 * Gets the difference between two angles
	 * This value is always positive (0 - 180)
	 *
	 * @param angle1
	 * @param angle2
	 * @return the positive angle difference
	 */
	public static float getAngleDifference(float angle1, float angle2) {
		return Math.abs(wrapAngle(angle1 - angle2));
	}

	/**
	 * Gets the difference between two radians
	 * This value is always positive (0 - PI)
	 *
	 * @param radian1
	 * @param radian2
	 * @return the positive radian difference
	 */
	public static double getRadianDifference(double radian1, double radian2) {
		return Math.abs(wrapRadian(radian1 - radian2));
	}

	/**
	 * Wraps the angle between -180 and 180 degrees
	 *
	 * @param angle to wrap
	 * @return -180 < angle <= 180
	 */
	public static float wrapAngle(float angle) {
		angle %= 360f;
		if (angle <= -180) {
			return angle + 360;
		} else if (angle > 180) {
			return angle - 360;
		} else {
			return angle;
		}
	}
	
	/**
	 * Wraps the pitch angle between -90 and 90 degrees
	 * 
	 * @param angle to wrap
	 * @return -90 < angle < 90
	 */
	public static float wrapAnglePitch(float angle) {
		angle = wrapAngle(angle);
		
		if (angle < -90)
			return -90;
		if (angle > 90)
			return 90;
		return angle;
	}

	/**
	 * Wraps a byte between 0 and 256
	 * 
	 * @param value to wrap
	 * @return 0 < byte < 256
	 */
	public static byte wrapByte(int value) {
		value %= 256;
		if (value < 0) {
			value += 256;
		}
		return (byte) value;
	}
	
	/**
	 * Wraps the radian between -PI and PI
	 *
	 * @param radian to wrap
	 * @return -PI < radian <= PI
	 */
	public static double wrapRadian(double radian) {
		radian %= TWO_PI;
		if (radian <= -PI) {
			return radian + TWO_PI;
		} else if (radian > PI) {
			return radian - TWO_PI;
		} else {
			return radian;
		}
	}

	/**
	 * Rounds a number to the amount of decimals specified
	 *
	 * @param input to round
	 * @param decimals to round to
	 * @return the rounded number
	 */
	public static double round(double input, int decimals) {
		double p = Math.pow(10, decimals);
		return Math.round(input * p) / p;
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return the interpolated value
	 */
	public static double lerp(double a, double b, double percent) {
		return (1 - percent) * a + percent * b;
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return the interpolated value
	 */
	public static float lerp(float a, float b, float percent) {
		return (1 - percent) * a + percent * b;
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return the interpolated value
	 */
	public static int lerp(int a, int b, double percent) {
		return (int) ((1 - percent) * a + percent * b);
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return the interpolated vector
	 */
	public static Vector3 lerp(Vector3 a, Vector3 b, float percent) {
		return a.multiply(1 - percent).add(b.multiply(percent));
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return the interpolated vector
	 */
	public static Vector2 lerp(Vector2 a, Vector2 b, float percent) {
		return a.multiply(1 - percent).add(b.multiply(percent));
	}

	/**
	 * Calculates the value at x using linear interpolation
	 *
	 * @param x the X coord of the value to interpolate
	 * @param x1 the X coord of q0
	 * @param x2 the X coord of q1
	 * @param q0 the first known value (x1)
	 * @param q1 the second known value (x2)
	 * @return the interpolated value
	 */
	public static double lerp(double x, double x1, double x2, double q0, double q1) {
		return ((x2 - x) / (x2 - x1)) * q0 + ((x - x1) / (x2 - x1)) * q1;
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 *
	 * @param a
	 * @param b
	 * @param percent
	 * @return Color
	 */
	public static Color lerp(Color a, Color b, double percent) {
		int red = lerp(a.getRed(), b.getRed(), percent);
		int blue = lerp(a.getBlue(), b.getBlue(), percent);
		int green = lerp(a.getGreen(), b.getGreen(), percent);
		int alpha = lerp(a.getAlpha(), b.getAlpha(), percent);
		return new Color(red, green, blue, alpha);
	}

	/**
	 * Calculates the linear interpolation between a and b with the given
	 * percent
	 * 
	 * @param a
	 * @param b
	 * @param percent
	 * @return Quarternion
	 */
	public static Quaternion lerp(Quaternion a, Quaternion b, float percent) {
		float x = lerp(a.getX(), b.getX(), percent);
		float y = lerp(a.getY(), b.getY(), percent);
		float z = lerp(a.getZ(), b.getZ(), percent);
		float w = lerp(a.getW(), b.getW(), percent);
		return new Quaternion(x, y, z, w, true);
	}

	/**
	 * Calculates the value at x,y using bilinear interpolation
	 *
	 * @param x the X coord of the value to interpolate
	 * @param y the Y coord of the value to interpolate
	 * @param q00 the first known value (x1, y1)
	 * @param q01 the second known value (x1, y2)
	 * @param q10 the third known value (x2, y1)
	 * @param q11 the fourth known value (x2, y2)
	 * @param x1 the X coord of q00 and q01
	 * @param x2 the X coord of q10 and q11
	 * @param y1 the Y coord of q00 and q10
	 * @param y2 the Y coord of q01 and q11
	 * @return the interpolated value
	 */
	public static double biLerp(double x, double y, double q00, double q01,
			double q10, double q11, double x1, double x2, double y1, double y2) {
		double q0 = lerp(x, x1, x2, q00, q10);
		double q1 = lerp(x, x1, x2, q01, q11);
		return lerp(y, y1, y2, q0, q1);
	}
	
	/**
	 * Calculates the value at a target using bilinear interpolation
	 *
	 * @param target the vector of the value to interpolate
	 * @param q00 the first known value (known1.x, known1.y)
	 * @param q01 the second known value (known1.x, known2.y)
	 * @param q10 the third known value (known2.x, known1.y)
	 * @param q11 the fourth known value (known2.x, known2.y)
	 * @param known1 the X coord of q00 and q01 and the Y coord of q00 and q10
	 * @param known2 the X coord of q10 and q11 and the Y coord of q01 and q11
	 * @return the interpolated value
	 */
	public static double biLerp(Vector2 target, double q00, double q01,
			double q10, double q11, Vector2 known1, Vector2 known2) {
		double q0 = lerp(target.getX(), known1.getX(), known2.getX(), q00, q10);
		double q1 = lerp(target.getX(), known1.getX(), known2.getX(), q01, q11);
		return lerp(target.getY(), known1.getY(), known2.getY(), q0, q1);
	}

	/**
	 * Calculates the value at x,y,z using trilinear interpolation
	 *
	 * @param x the X coord of the value to interpolate
	 * @param y the Y coord of the value to interpolate
	 * @param z the Z coord of the value to interpolate
	 * @param q000 the first known value (x1, y1, z1)
	 * @param q001 the second known value (x1, y2, z1)
	 * @param q010 the third known value (x1, y1, z2)
	 * @param q011 the fourth known value (x1, y2, z2)
	 * @param q100 the fifth known value (x2, y1, z1)
	 * @param q101 the sixth known value (x2, y2, z1)
	 * @param q110 the seventh known value (x2, y1, z2)
	 * @param q111 the eighth known value (x2, y2, z2)
	 * @param x1 the X coord of q000, q001, q010 and q011
	 * @param x2 the X coord of q100, q101, q110 and q111
	 * @param y1 the Y coord of q000, q010, q100 and q110
	 * @param y2 the Y coord of q001, q011, q101 and q111
	 * @param z1 the Z coord of q000, q001, q100 and q101
	 * @param z2 the Z coord of q010, q011, q110 and q111
	 * @return the interpolated value
	 */
	public static double triLerp(double x, double y, double z, double q000, double q001,
			double q010, double q011, double q100, double q101, double q110, double q111,
			double x1, double x2, double y1, double y2, double z1, double z2) {
		double q00 = lerp(x, x1, x2, q000, q100);
		double q01 = lerp(x, x1, x2, q010, q110);
		double q10 = lerp(x, x1, x2, q001, q101);
		double q11 = lerp(x, x1, x2, q011, q111);
		double q0 = lerp(y, y1, y2, q00, q10);
		double q1 = lerp(y, y1, y2, q01, q11);
		return lerp(z, z1, z2, q0, q1);
	}
	
	/**
	 * Calculates the value at target using trilinear interpolation
	 *
	 * @param target the vector of the value to interpolate
	 * @param q000 the first known value (known1.x, known1.y, known1.z)
	 * @param q001 the second known value (known1.x, known2.y, known1.z)
	 * @param q010 the third known value (known1.x, known1.y, known2.z)
	 * @param q011 the fourth known value (known1.x, known2.y, known2.z)
	 * @param q100 the fifth known value (known2.x, known1.y, known1.z)
	 * @param q101 the sixth known value (known2.x, known2.y, known1.z)
	 * @param q110 the seventh known value (known2.x, known1.y, known2.z)
	 * @param q111 the eighth known value (known2.x, known2.y, known2.z)
	 * @param known1 the X coord of q000, q001, q010 and q011, the Y coord of q000, q010, q100 and q110
	 * and the Z coord of q000, q001, q100 and q101
	 * @param known2 the X coord of q100, q101, q110 and q111, the Y coord of q001, q011, q101 and q111
	 * and the Z coord of q010, q011, q110 and q111
	 * @return the interpolated value
	 */
	public static double triLerp(Vector3 target, double q000, double q001, double q010,
			double q011, double q100, double q101, double q110, double q111, Vector3 known1, Vector3 known2) {
		double q00 = lerp(target.getX(), known1.getX(), known2.getX(), q000, q100);
		double q01 = lerp(target.getX(), known1.getX(), known2.getX(), q010, q110);
		double q10 = lerp(target.getX(), known1.getX(), known2.getX(), q001, q101);
		double q11 = lerp(target.getX(), known1.getX(), known2.getX(), q011, q111);
		double q0 = lerp(target.getY(), known1.getY(), known2.getY(), q00, q10);
		double q1 = lerp(target.getY(), known1.getY(), known2.getY(), q01, q11);
		return lerp(target.getZ(), known1.getZ(), known2.getZ(), q0, q1);
	}

	public static Color blend(Color a, Color b) {
		int red = lerp(a.getRed(), b.getRed(), (a.getAlpha()/255.0));
		int blue = lerp(a.getBlue(), b.getBlue(), (a.getAlpha()/255.0));
		int green = lerp(a.getGreen(), b.getGreen(), (a.getAlpha()/255.0));
		int alpha = lerp(a.getAlpha(), b.getAlpha(), (a.getAlpha()/255.0));
		return new Color(red, green, blue, alpha);
	}

	/**
	 * Generates a random color
	 * 
	 * @return Random color
	 */
	public static Color randomColor() {
		Random rng = new Random();
		return new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
	}
	
	/**
	 * Clamps the value between the low and high boundaries
	 * 
	 * @param value
	 * @param low
	 * @param high
	 * @return Clamped value
	 */
	public static double clamp(double value, double low, double high) {
		if (value < low) {
			return low;
		}
		if (value > high) {
			return high;
		}
		return value;
	}
	
	/**
	 * Clamps the value between the low and high boundaries
	 * 
	 * @param value
	 * @param low
	 * @param high
	 * @return Clamped value
	 */
	public static int clamp(int value, int low, int high) {
		if (value < low) {
			return low;
		}
		if (value > high) {
			return high;
		}
		return value;
	}

	/**
	 * Returns the forward vector transformed by the provided pitch and yaw
	 *
	 * @param pitch
	 * @param yaw
	 * @return
	 */
	public static Vector3 getDirectionVector(float pitch, float yaw) {
		return transform(Vector3.UNIT_X, rotate(new Quaternion(pitch, Vector3.UNIT_Z).multiply(new Quaternion(yaw, Vector3.UNIT_Y))));
	}

	/**
	 * Returns the forward vector transformed by the provided quaternion
	 *
	 * @param rot
	 * @return
	 */
	public static Vector3 getDirectionVector(Quaternion rot) {
		return transform(Vector3.FORWARD, rotate(rot));
	}

	//Fast Math Implementation
	/**
	 * Fast implementation of cos(x).  If -PI <= x <= PI, then the maximum error is 0.0015
	 * 
	 * @param x in radians
	 * @return sin(x)
	 */
	public static double cos(final double x) {
		return sin(x + (x > HALF_PI ? -THREE_PI_HALVES : HALF_PI));
	}

	/**
	 * Fast implementation of sin(x).  If -PI <= x <= PI, then the maximum error is 0.0015
	 * 
	 * @param x in radians
	 * @return sin(x)
	 */
	public static double sin(double x) {
		x = sin_a * x * Math.abs(x) + sin_b * x;
		return sin_p * (x * Math.abs(x) - x) + x;
	}

	public static double tan(final double x) {
		return sin(x) / cos(x);
	}

	public static double asin(final double x) {
		return x * (Math.abs(x) * (Math.abs(x) * asin_a + asin_b) + asin_c) + Math.signum(x) * (asin_d - Math.sqrt(1 - x * x));
	}

	public static double acos(final double x) {
		return HALF_PI - asin(x);
	}

	public static double atan(final double x) {
		return Math.abs(x) < 1 ? x / (1 + atan_a * x * x) : Math.signum(x) * HALF_PI - x / (x * x + atan_a);
	}

	public static double inverseSqrt(double x) {
		final double xhalves = 0.5d * x;
		x = Double.longBitsToDouble(0x5FE6EB50C7B537AAl - (Double.doubleToRawLongBits(x) >> 1));
		return x * (1.5d - xhalves * x * x);
	}

	public static double sqrt(final double x) {
		return x * inverseSqrt(x);
	}

	private static final double sin_a = -4 / SQUARED_PI;

	private static final double sin_b = 4 / PI;

	private static final double sin_p = 9d / 40;

	private final static double asin_a = -0.0481295276831013447d;

	private final static double asin_b = -0.343835993947915197d;

	private final static double asin_c = 0.962761848425913169d;

	private final static double asin_d = 1.00138940860107040d;

	private final static double atan_a = 0.280872d;

	// Integer Maths

	/**
	 * Rounds x down to the cloest integer
	 * 
	 * @param x
	 * @return int
	 */
	public static int floor(double x) {
		int y = (int) x;
		if (x < y) {
			return y - 1;
		}
		return y;
	}

	/**
	 * Rounds x down to the cloest integer
	 * 
	 * @param x
	 * @return int
	 */
	public static int floor(float x) {
		int y = (int) x;
		if (x < y) {
			return y - 1;
		}
		return y;
	}

	/**
	 * Gets the maximum byte value from two values
	 * 
	 * @param value1
	 * @param value2
	 * @return the maximum value
	 */
	public static byte max(byte value1, byte value2) {
		return value1 > value2 ? value1 : value2;
	}

	/**
	 * Rounds an integer up to the next power of 2.
	 *
	 * @param x
	 * @return the lowest power of 2 greater or equal to x
	 */
	public static int roundUpPow2(int x) {
		if (x <= 0) {
			return 1;
		} else if (x > 0x40000000) {
			throw new IllegalArgumentException("Rounding " + x + " to the next highest power of two would exceed the int range");
		} else {
			x--;
			x |= x >> 1;
			x |= x >> 2;
			x |= x >> 4;
			x |= x >> 8;
			x |= x >> 16;
			x++;
			return x;
		}
	}
	
	/**
	 * Rounds an integer up to the next power of 2.
	 *
	 * @param x
	 * @return the lowest power of 2 greater or equal to x
	 */
	public static long roundUpPow2(long x) {
		if (x <= 0) {
			return 1;
		} else if (x > 0x4000000000000000L) {
			throw new IllegalArgumentException("Rounding " + x + " to the next highest power of two would exceed the int range");
		} else {
			x--;
			x |= x >> 1;
			x |= x >> 2;
			x |= x >> 4;
			x |= x >> 8;
			x |= x >> 16;
			x |= x >> 32;
			x++;
			return x;
		}
	}

	/**
	 * Casts a value to a float. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Float castFloat(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number) o).floatValue();
		}

		try {
			return Float.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to a byte. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Byte castByte(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number)o).byteValue();
		}

		try {
			return Byte.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to a short. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Short castShort(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number)o).shortValue();
		}

		try {
			return Short.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to an integer. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Integer castInt(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number)o).intValue();
		}

		try {
			return Integer.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to a double. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Double castDouble(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number)o).doubleValue();
		}

		try {
			return Double.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to a long. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Long castLong(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Number) {
			return ((Number)o).longValue();
		}

		try {
			return Long.valueOf(o.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Casts a value to a boolean. May return null.
	 *
	 * @param o
	 * @return
	 */
	public static Boolean castBoolean(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Boolean) {
			return (Boolean) o;
		} else if (o instanceof String) {
			try {
				return Boolean.parseBoolean((String) o);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}

		return null;
	}

	/**
	 * Calculates the mean of a set of values
	 *
	 * @param values to calculate the mean of
	 * @return the mean
	 */
	public static int mean(int... values) {
		int sum = 0;
		for (int v : values) {
			sum += v;
		}
		return sum/values.length;
	}

	/**
	 * Calculates the mean of a set of values.
	 *
	 * @param values to calculate the mean of
	 */
	public static double mean(double... values) {
		double sum = 0;
		for (double v : values) {
			sum += v;
		}
		return sum/values.length;
	}

	/* Quaterion Helpers */

	/**
	 * Returns the length squared of the given Quaternion
	 *
	 * @param a
	 * @return
	 */
	public static float lengthSquared(Quaternion a) {
		return a.x * a.x + a.y * a.y + a.z * a.z + a.w * a.w;
	}

	/**
	 * Returns the length of the given Quaternion <br/>
	 * <br/>
	 * Note: Uses Math.sqrt.
	 *
	 * @param a
	 * @return length of Quaternion
	 */
	public static float length(Quaternion a) {
		return (float) Math.sqrt(lengthSquared(a));
	}

	/**
	 * Constructs and returns a new Quaternion that is the given Quaternion but
	 * length() == 1
	 *
	 * @param a
	 * @return normalized Quaternion
	 */
	public static Quaternion normalize(Quaternion a) {
		float length = length(a);
		return new Quaternion(a.x / length, a.y / length, a.z / length, a.w / length, true);
	}

	/**
	 * Constructs and returns a new Quaternion that is A * B
	 *
	 * @param a
	 * @param b
	 * @return multiplied Quaternion
	 */
	public static Quaternion multiply(Quaternion a, Quaternion b) {
		float x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;

		float y = a.w * b.y + a.y * b.w + a.z * b.x - a.x * b.z;

		float z = a.w * b.z + a.z * b.w + a.x * b.y - a.y * b.x;

		float w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;

		return new Quaternion(x, y, z, w, true);
	}

	public static Quaternion rotation(float pitch, float yaw, float roll) {
		StackContext.enter(); 
		try {
			final Quaternion qpitch = new Quaternion(pitch, Vector3.RIGHT);
			final Quaternion qyaw = new Quaternion(yaw, Vector3.UP);
			final Quaternion qroll = new Quaternion(roll, Vector3.FORWARD);
			
			return StackContext.outerCopy(qyaw.multiply(qpitch).multiply(qroll));
		} finally {
			StackContext.exit();
		}
	}

	/**
	 * Constructs and returns a new Quaternion that is rotated about the axis
	 * and angle
	 *
	 * @param a
	 * @param angle
	 * @param axis
	 * @return rotated Quaternion
	 */
	public static Quaternion rotate(Quaternion a, float angle, Vector3 axis) {
		return multiply(new Quaternion(angle, axis), a);
	}

	/**
	 * Constructs and returns a new Quaternion that is rotated about the axis
	 * and angle
	 *
	 * @param a
	 * @param angle
	 * @param x axis
	 * @param y axis
	 * @param z axis
	 * @return rotated Quaternion
	 */
	public static Quaternion rotate(Quaternion a, float angle, float x, float y, float z) {
		return multiply(new Quaternion(angle, x, y, z), a);
	}

	/**
	 * Returns the rotation between two vectors
	 * @param a
	 * @param b
	 * @return the rotation Quaternion
	 */
	public static Quaternion rotationTo(Vector3 a, Vector3 b) {
		if (a == b || a.equals(b)) {
			return Quaternion.IDENTITY;
		}
		//Normalize the input vectors before doing math on them.
		a = a.normalize();
		b = b.normalize();
		return new Quaternion((float)Math.toDegrees(Math.acos(a.dot(b))), a.cross(b));
	}

	/* Vector3 Helpers */

	public static Vector3 toVector3(Vector3f vector) {
		return new Vector3(vector.x, vector.y, vector.z);
	}

	public static Vector3f toVector3f(Vector3 vector) {
		return toVector3f(vector.x, vector.y, vector.z);
	}

	public static Vector3f toVector3f(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}

	public static Quat4f toQuaternionf(float w, float y, float p, float r) {
		return new Quat4f(y, p, r, w);
	}

	public static Quat4f toQuaternionf(Quaternion quaternion) {
		return toQuaternionf(quaternion.w, quaternion.x, quaternion.y, quaternion.z);
	}

	public static Quaternion toQuaternion(Quat4f quaternion) {
		return new Quaternion(quaternion.x, quaternion.y, quaternion.z, quaternion.w, false);
	}

	/**
	 * Returns the angles, in degrees, about each axis of this quaternion stored
	 * in a Vector3 <br/> <br/>
	 *
	 * vect.X = Rotation about the X axis (Pitch) <br/>
	 * vect.Y = Rotation about the Y axis (Yaw) <br/>
	 * vect.Z = Rotation about the Z axis (Roll) <br/>
	 *
	 * @param a
	 * @return axis angles
	 */
	public static Vector3 getAxisAngles(Quaternion a) {
		// Map to Euler angles
		final float q0 = a.w;
		final float q1 = a.z; // roll
		final float q2 = a.x; // pitch
		final float q3 = a.y; // yaw

		final double r1, r2, r3, test;
		test = q0 * q2 - q3 * q1;

		if (Math.abs(test) < 0.4999) {
			r1 = Math.atan2(2 * (q0 * q1 + q2 * q3), 1 - 2 * (q1 * q1 + q2 * q2));
			r2 = Math.asin(2 * test);
			r3 = Math.atan2(2 * (q0 * q3 + q1 * q2), 1 - 2 * (q2 * q2 + q3 * q3));
		} else { // pitch is at north or south pole
			int sign = (test < 0) ? -1 : 1;
			r1 = 0;
			r2 = sign * Math.PI / 2;
			r3 = -sign * 2 * Math.atan2(q1, q0);
		}

		// ...and back to Tait-Bryan
		final float roll = (float) Math.toDegrees(r1);
		final float pitch = (float) Math.toDegrees(r2);
		float yaw = (float) Math.toDegrees(r3);
		if (yaw > 180) // keep -180 < yaw < 180
			yaw -= 360;
		else if (yaw < -180)
			yaw += 360;
		return new Vector3(pitch, yaw, roll);
	}

	/* Vector3 Helpers */
	/**
	 * Returns the length of the given vector.
	 *
	 * Note: Makes use of Math.sqrt and is not cached, so can be slow
	 *
	 * Also known as norm. ||a||
	 *
	 * @param a
	 * @return
	 */
	public static float length(Vector3 a) {
		return (float) Math.sqrt(lengthSquared(a));
	}

	/**
	 * Returns an approximate length of the given vector.
	 *
	 * @param a
	 * @return
	 */
	public static float fastLength(Vector3 a) {
		return (float) MathHelper.sqrt(lengthSquared(a));
	}

	/**
	 * returns the length squared to the given vector
	 *
	 * @param a
	 * @return
	 */
	public static float lengthSquared(Vector3 a) {
		return dot(a, a);
	}

	/**
	 * Returns a new vector that is the given vector but length 1
	 *
	 * @param a
	 * @return
	 */
	public static Vector3 normalize(Vector3 a) {
		return a.multiply(1.f / a.length());
	}

	/**
	 * Creates a new Vector that is A + B
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3 add(Vector3 a, Vector3 b) {
		return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	/**
	 * Creates a new vector that is A - B
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3 subtract(Vector3 a, Vector3 b) {
		return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	/**
	 * Multiplies one Vector3 by the other Vector3
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3 multiply(Vector3 a, Vector3 b) {
		return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z);
	}

	/**
	 * Divides one Vector3 by the other Vector3
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3 divide(Vector3 a, Vector3 b) {
		return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z);
	}

	/**
	 * Returns the dot product of A and B
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static float dot(Vector3 a, Vector3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	/**
	 * Creates a new Vector that is the A x B The Cross Product is the vector
	 * orthogonal to both A and B
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3 cross(Vector3 a, Vector3 b) {
		return new Vector3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}

	/**
	 * Rounds the X, Y, and Z values of the given Vector3 up to the nearest
	 * integer value.
	 *
	 * @param o Vector3 to use
	 * @return
	 */
	public static Vector3 ceil(Vector3 o) {
		return new Vector3(Math.ceil(o.x), Math.ceil(o.y), Math.ceil(o.z));
	}

	/**
	 * Rounds the X, Y, and Z values of the given Vector3 down to the nearest
	 * integer value.
	 *
	 * @param o Vector3 to use
	 * @return
	 */
	public static Vector3 floor(Vector3 o) {
		return new Vector3(Math.floor(o.x), Math.floor(o.y), Math.floor(o.z));
	}

	/**
	 * Rounds the X, Y, and Z values of the given Vector3 to the nearest integer
	 * value.
	 *
	 * @param o Vector3 to use
	 * @return
	 */
	public static Vector3 round(Vector3 o) {
		return new Vector3(Math.round(o.x), Math.round(o.y), Math.round(o.z));
	}
	
	/**
	 * Rounds all fields of the vector to the nearest integer value.
	 * 
	 * @param o Matrix to use
	 * @return
	 */
	public static Matrix round(Matrix o) {
		Matrix ret = new Matrix(o);
		for (int x = 0; x < o.dimension; x ++) {
			for (int y = 0; y < o.dimension; y ++) {
				ret.set(x, y, Math.round(o.get(x, y)));
			}
		}
		return ret;
	}

	/**
	 * Sets the X, Y, and Z values of the given Vector3 to their absolute value.
	 *
	 * @param o Vector3 to use
	 * @return
	 */
	public static Vector3 abs(Vector3 o) {
		return new Vector3(Math.abs(o.x), Math.abs(o.y), Math.abs(o.z));
	}

	/**
	 * Returns a Vector3 containing the smallest X, Y, and Z values.
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Vector3 min(Vector3 o1, Vector3 o2) {
		return new Vector3(Math.min(o1.x, o2.x), Math.min(o1.y, o2.y), Math.min(o1.z, o2.z));
	}

	/**
	 * Returns a Vector3 containing the largest X, Y, and Z values.
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Vector3 max(Vector3 o1, Vector3 o2) {
		return new Vector3(Math.max(o1.x, o2.x), Math.max(o1.y, o2.y), Math.max(o1.z, o2.z));
	}

	/**
	 * Returns a Vector3 with random X, Y, and Z values (between 0 and 1)
	 *
	 * @return
	 */
	public static Vector3 rand() {
		double[] rands = new double[3];
		for (int i = 0; i < 3; i++) {
			rands[i] = Math.random() * 2 - 1;
		}
		return new Vector3(rands[0], rands[1], rands[2]);
	}

	/**
	 * Gets the distance between two Vector3.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static double distance(Vector3 a, Vector3 b) {
		return MathHelper.length(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	/**
	 * Gets the squared distance between two Vector3.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static double distanceSquared(Vector3 a, Vector3 b) {
		return MathHelper.lengthSquared(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	/**
	 * Raises the X, Y, and Z values of a Vector3 to the given power.
	 *
	 * @param o
	 * @param power
	 * @return
	 */
	public static Vector3 pow(Vector3 o, double power) {
		return new Vector3(Math.pow(o.x, power), Math.pow(o.y, power), Math.pow(o.z, power));
	}

	/**
	 * Returns a Vector2 object using the X and Z values of the given Vector3.
	 * The x of the Vector3 becomes the x of the Vector2, and the z of this
	 * Vector3 becomes the y of the Vector2m.
	 *
	 * @param o Vector3 object to use
	 * @return
	 */
	public static Vector2 toVector2(Vector3 o) {
		return new Vector2(o.x, o.z);
	}

	/**
	 * Returns a new float array that is {x, y, z}
	 *
	 * @param a
	 * @return
	 */
	public static float[] toArray(Vector3 a) {
		return new float[] {a.x, a.y, a.z};
	}

	/**
	 * Calculates and returns a new Vector3 transformed by the given quaternion
	 *
	 * @param vector
	 * @param rot
	 * @return
	 */
	public static Vector3 transform(Vector3 vector, Quaternion rot) {
		return transform(vector, rotate(rot));
	}

	/**
	 * Compares two Vector3s
	 */
	public static int compareTo(Vector3 a, Vector3 b) {
		return (int) a.lengthSquared() - (int) b.lengthSquared();
	}

	/**
	 * Checks if two Vector3s are equal
	 */
	public static boolean equals(Vector3 a, Vector3 b) {
		return a.equals(b);
	}

	/* Matrix Helpers */

	/**
	 * Adds two matricies together
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Matrix add(Matrix a, Matrix b) {
		if (a.dimension != b.dimension) {
			throw new IllegalArgumentException("Matrix Dimensions must be equal");
		}
		Matrix res = new Matrix(a.dimension);
		for (int x = 0; x < res.dimension; x++) {
			for (int y = 0; y < res.dimension; y++) {
				res.data[index(x, y, res.dimension)] = a.data[index(x, y, res.dimension)] + b.data[index(x, y, res.dimension)];
			}
		}
		return res;
	}

	/**
	 * Multiplies two matricies together
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Matrix multiply(Matrix a, Matrix b) {
		if (a.dimension != b.dimension) {
			throw new IllegalArgumentException("Matrix Dimensions must be equal");
		}
		Matrix res = new Matrix(a.dimension);
		for (int i = 0; i < res.dimension; i++) {
			for (int j = 0; j < res.dimension; j++) {
				res.set(i, j, 0);
				for (int k = 0; k < res.dimension; k++) {
					float r = a.get(i, k) * b.get(k, j);
					res.set(i, j, res.get(i, j) + r);

				}
			}
		}
		return res;
	}

	/**
	 * Creates and returns a 4x4 identity matrix
	 *
	 * @return
	 */
	public static Matrix createIdentity() {
		return new Matrix(4);
	}

	/**
	 * Creates and returns a 4x4 matrix that represents the translation provided
	 * by the given Vector3
	 *
	 * @param vector
	 * @return
	 */
	public static Matrix translate(Vector3 vector) {
		Matrix res = createIdentity();
		res.set(3, 0, vector.getX());
		res.set(3, 1, vector.getY());
		res.set(3, 2, vector.getZ());
		return res;
	}

	/**
	 * Creates and returns a 4x4 uniform scalar matrix
	 *
	 * @param ammount
	 * @return
	 */
	public static Matrix scale(float ammount) {
		Matrix res = createIdentity();
		res.set(0, 0, ammount);
		res.set(1, 1, ammount);
		res.set(2, 2, ammount);
		return res;
	}

	/**
	 * Creates and returns a 4x4 scalar matrix that multiplys each axis given by
	 * the provided Vector3
	 *
	 * @param ammount
	 * @return
	 */
	public static Matrix scale(Vector3 ammount) {
		Matrix res = createIdentity();
		res.set(0, 0, ammount.getX());
		res.set(1, 1, ammount.getY());
		res.set(2, 2, ammount.getZ());
		return res;
	}

	/**
	 * Creates and returns a 4x4 rotation matrix around the X axis
	 *
	 * @param rot
	 * @return
	 */
	public static Matrix rotateX(float rot) {
		Matrix res = createIdentity();
		res.set(1, 1, (float) Math.cos(Math.toRadians(rot)));
		res.set(1, 2, (float) -Math.sin(Math.toRadians(rot)));
		res.set(2, 1, (float) Math.sin(Math.toRadians(rot)));
		res.set(2, 2, (float) Math.cos(Math.toRadians(rot)));

		return res;
	}

	/**
	 * Creates and returns a 4x4 rotation matrix around the Y axis
	 *
	 * @param rot
	 * @return
	 */
	public static Matrix rotateY(float rot) {
		Matrix res = createIdentity();
		res.set(0, 0, (float) Math.cos(Math.toRadians(rot)));
		res.set(0, 2, (float) Math.sin(Math.toRadians(rot)));
		res.set(2, 0, (float) -Math.sin(Math.toRadians(rot)));
		res.set(2, 2, (float) Math.cos(Math.toRadians(rot)));
		return res;
	}

	/**
	 * Creates and returns a 4x4 rotation matrix around the Z axis
	 *
	 * @param rot
	 * @return
	 */
	public static Matrix rotateZ(float rot) {
		Matrix res = createIdentity();
		res.set(0, 0, (float) Math.cos(Math.toRadians(rot)));
		res.set(0, 1, (float) -Math.sin(Math.toRadians(rot)));
		res.set(1, 0, (float) Math.sin(Math.toRadians(rot)));
		res.set(1, 1, (float) Math.cos(Math.toRadians(rot)));
		return res;
	}

	/**
	 * Creates and returns a 4x4 rotation matrix given by the provided
	 * Quaternion
	 *
	 * @param rot
	 * @return
	 */
	public static Matrix rotate(Quaternion rot) {
		Matrix res = createIdentity();
		Quaternion r = rot.normalize(); //Confirm that we are dealing with a unit quaternion

		res.set(0, 0, 1 - 2 * r.getY() * r.getY() - 2 * r.getZ() * r.getZ());
		res.set(0, 1, 2 * r.getX() * r.getY() - 2 * r.getW() * r.getZ());
		res.set(0, 2, 2 * r.getX() * r.getZ() + 2 * r.getW() * r.getY());
		res.set(0, 3, 0);

		res.set(1, 0, 2 * r.getX() * r.getY() + 2 * r.getW() * r.getZ());
		res.set(1, 1, 1 - 2 * r.getX() * r.getX() - 2 * r.getZ() * r.getZ());
		res.set(1, 2, 2 * r.getY() * r.getZ() - 2 * r.getW() * r.getX());
		res.set(1, 3, 0);

		res.set(2, 0, 2 * r.getX() * r.getZ() - 2 * r.getW() * r.getY());
		res.set(2, 1, 2.f * r.getY() * r.getZ() + 2.f * r.getX() * r.getW());
		res.set(2, 2, 1 - 2 * r.getX() * r.getX() - 2 * r.getY() * r.getY());
		res.set(2, 3, 0);

		//3, [0-3] will be 0,0,0,1 due to identity matrix

		return res;
	}

	/**
	 * Calculates and returns a new Vector3 transformed by the transformation
	 * matrix
	 *
	 * @param v the vector to transform
	 * @param m the transformation matrix
	 * @return
	 */
	public static Vector3 transform(Vector3 v, Matrix m) {
		float[] vector = {v.getX(), v.getY(), v.getZ(), 1};
		float[] vres = new float[4];
		for (int i = 0; i < m.dimension; i++) {
			vres[i] = 0;
			for (int k = 0; k < m.dimension; k++) {
				float n = m.get(i, k) * vector[k];
				vres[i] += n;

			}
		}

		return new Vector3(vres[0], vres[1], vres[2]);
	}

	/**
	 * Returns the given matrix in a single dimension float array
	 *
	 * @return
	 */
	public static float[] toArray(Matrix m) {
		return m.data.clone();
	}

	/**
	 * Creates a lookat matrix with the given eye point.
	 *
	 * @param eye The location of the camera
	 * @param at The location that the camera is looking at
	 * @param up The direction that corrisponds to Up
	 * @return A rotational transform that corrisponds to a camera looking at
	 *         the given values
	 */
	public static Matrix createLookAt(Vector3 eye, Vector3 at, Vector3 up) {
		Vector3 f = at.subtract(eye).normalize();
		up = up.normalize();

		Vector3 s = f.cross(up).normalize();
		Vector3 u = s.cross(f).normalize();

		Matrix mat = new Matrix(4);

		mat.set(0, 0, s.getX());
		mat.set(1, 0, s.getY());
		mat.set(2, 0, s.getZ());

		mat.set(0, 1, u.getX());
		mat.set(1, 1, u.getY());
		mat.set(2, 1, u.getZ());

		mat.set(0, 2, -f.getX());
		mat.set(1, 2, -f.getY());
		mat.set(2, 2, -f.getZ());

		Matrix trans = translate(eye.multiply(-1));
		mat = multiply(trans, mat);
		return mat;
	}

	/**
	 * Creates a perspective projection matrix with the given (x) FOV, aspect,
	 * near and far planes
	 *
	 * @param fov The Field of View in the x direction
	 * @param aspect The aspect ratio, usually width/height
	 * @param znear The near plane. Cannot be 0
	 * @param zfar the far plane. zfar cannot equal znear
	 * @return A perspective projection matrix built from the given values
	 */
	public static Matrix createPerspective(float fov, float aspect, float znear, float zfar) {
		float ymax, xmax;
		ymax = znear * (float) Math.tan(fov * Math.PI / 360.0);
		xmax = ymax * aspect;
		return createOrthographic(xmax, -xmax, ymax, -ymax, znear, zfar);
	}

	/**
	 * Creates an orthographic viewing fustrum built from the provided values
	 *
	 * @param right the right most plane of the viewing fustrum
	 * @param left the left most plane of the viewing fustrum
	 * @param top the top plane of the viewing fustrum
	 * @param bottom the bottom plane of the viewing fustrum
	 * @param near the near plane of the viewing fustrum
	 * @param far the far plane of the viewing fustrum
	 * @return A viewing fustrum build from the provided values
	 */
	public static Matrix createOrthographic(float right, float left, float top, float bottom, float near, float far) {
		Matrix ortho = new Matrix();
		float temp, temp2, temp3, temp4;
		temp = 2.0f * near;
		temp2 = right - left;
		temp3 = top - bottom;
		temp4 = far - near;

		ortho.set(0, 0, temp / temp2);
		ortho.set(1, 1, temp / temp3);

		ortho.set(0, 2, (right + left) / temp2);
		ortho.set(1, 2, (top + bottom) / temp3);
		ortho.set(2, 2, (-far - near) / temp4);
		ortho.set(2, 3, -1);

		ortho.set(3, 2, -temp * far / temp4);
		ortho.set(3, 3, 0);

		return ortho;
	}

	public static Matrix transpose(Matrix in) {
		Matrix r = new Matrix(in.dimension);

		for (int i = 0; i < in.dimension; i++) {
			for (int j = 0; j < in.dimension; j++) {
				r.set(j, i, in.get(i, j));
			}
		}
		return r;
	}

	public static Matrix toMatrix(Matrix4f matrix) {
		Matrix out = new Matrix(4);
		for (int i = 0; i < out.dimension; i++) {
			for (int j = 0; j <= i; j++) {
				out.set(i, j, matrix.getElement(i, j));
			}
		}
		return out;
	}

	public static int index(int x, int y, int dim) {
		return x * dim + y;
	}

	public static String decToHex(int dec, int minDigits) {
		String ret = Integer.toHexString(dec);
		while (ret.length() < minDigits) {
			ret = "0"+ret;
		}
		return ret;
	}

	public static int mod(int x, int div) {
		return x < 0 ? (x % div) + div : x % div;
	}
	
	public static float mod(float x, float div) {
		return x < 0 ? (x % div) + div : x % div;
	}
	
	public static double mod(double x, double div) {
		return x < 0 ? (x % div) + div : x % div;
	}
	
	private final static ThreadLocal<Random> randomThreadLocal = new ThreadLocal<Random>() {
		private final long HASH_SEED = 0x710677E178DFAF2EL;
		
		protected Random initialValue() {
			// Overkill, since only a standard Random is used after seeding
			long hash = HASH_SEED;
			byte[] arr = SecureRandom.getSeed(8);
			for (int i = 0; i < 8; i++) {
				hash = hash << 8 | (arr[i] & 0xFFL);
			}
			return new Random(hash);
		}
	};
	
	/**
	 * Gets a thread local Random object that is seeded using SecureRandom.  Only one Random is created per thread.
	 * 
	 * @return
	 */
	public static Random getRandom() {
		return randomThreadLocal.get();
	}
}
