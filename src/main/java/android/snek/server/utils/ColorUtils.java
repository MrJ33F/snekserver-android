package android.snek.server.utils;

import java.awt.*;

public final class ColorUtils {

    public static int rainbowColor() {
        final float hue = RandomUtils.nextFloat() * 360;
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black

        return Color.getHSBColor(hue, saturation, luminance).getRGB();
    }

}
