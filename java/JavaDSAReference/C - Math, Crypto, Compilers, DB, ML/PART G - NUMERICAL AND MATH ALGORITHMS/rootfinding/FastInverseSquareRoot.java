package math.rootfinding;

/**
 * WHAT IT IS: Fast Inverse Square Root. Computes 1/sqrt(x) quickly.
 * STRATEGY: Bit-level manipulation of IEEE 754 floats. The magic number 0x5f3759df provides a highly accurate initial guess for Newton's method.
 * TIME/SPACE COMPLEXITY: Time: O(1), Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: 3D graphics (Quake III Arena) for vector normalization.
 * WHEN TO USE / COMBINATION: Performance-critical float math where slight inaccuracy is fine.
 * 
 * PSEUDOCODE:
 * i = floatBitsToInt(x)
 * i = 0x5f3759df - (i >> 1)
 * y = intBitsToFloat(i)
 * y = y * (1.5 - 0.5 * x * y * y) // 1 iteration of Newton
 */
public class FastInverseSquareRoot {
    public static float invSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        float y = Float.intBitsToFloat(i);
        y = y * (1.5f - xhalf * y * y); // 1st iteration
        // y = y * (1.5f - xhalf * y * y); // 2nd iteration, usually not needed
        return y;
    }
    
    public static void main(String[] args) {
        System.out.println("Fast InvSqrt of 4: " + invSqrt(4.0f));
        System.out.println("Actual 1/sqrt(4): " + (1.0 / Math.sqrt(4.0)));
    }
}
