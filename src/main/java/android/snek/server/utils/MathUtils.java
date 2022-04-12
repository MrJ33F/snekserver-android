package android.snek.server.utils;

public class MathUtils {

    public static int between(int a, int v, int b) {
        return Math.max(a, Math.min(v, b));
    }

    public static double pythagoras(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

}
