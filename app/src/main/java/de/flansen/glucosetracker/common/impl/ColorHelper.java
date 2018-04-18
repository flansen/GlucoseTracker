package de.flansen.glucosetracker.common.impl;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * Created by Florian on 05.04.2016.
 */
public class ColorHelper {

    private ColorHelper() {
    }

    public static int addAlphaToColor(int color, int value) {
        return Color.argb(value, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static View setViewBackgroundToGradient(final View viewToFill, final int startColor, final int endColor) {
        //http://stackoverflow.com/questions/13929877/how-to-make-gradient-background-in-android
        Drawable[] layers = new Drawable[1];
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(
                        0,
                        0,
                        0,
                        viewToFill.getHeight(),
                        new int[]{
                                startColor,
                                endColor},
                        new float[]{0, 1},
                        Shader.TileMode.CLAMP);
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[]{5, 5, 5, 5, 0, 0, 0, 0});
        layers[0] = p;

        LayerDrawable composite = new LayerDrawable(layers);
        viewToFill.setBackground(composite);
        return viewToFill;
    }

    public static int calculateColor(float fromValue) {
        return ColorHelper.calculateColor(fromValue, 0.8f, 0.8f);
    }

    public static int calculateColor(float fromValue, float saturation, float brightness) {
        float lowerBound = 80f;
        float upperBound = 160f;
        float hue = 120f;

        if (fromValue <= lowerBound) {
            hue = ColorHelper.map(fromValue, 55f, lowerBound, 0f, 70f);
        } else if (lowerBound < fromValue && fromValue < upperBound) {
            hue = ColorHelper.map(fromValue, lowerBound, upperBound, 70, 160);
        } else {
            hue = ColorHelper.map(fromValue, upperBound, 300, 160, 250);
        }
        hue = Math.max(Math.min(hue, 250), 0);
        return Color.HSVToColor(new float[]{hue, saturation, brightness});
    }

    private static float map(float value, float iStart, float iStop, float oStart, float oStop) {
        return oStart + (oStop - oStart) * ((value - iStart) / (iStop - iStart));
    }

    public static int[] calculateColors(float fromValue) {
        float valueOffset = fromValue * 0.075f;
        int topColor = ColorHelper.calculateColor(fromValue + valueOffset);
        int bottomColor = ColorHelper.calculateColor(fromValue - valueOffset);
        return new int[]{topColor, bottomColor};
    }


}
