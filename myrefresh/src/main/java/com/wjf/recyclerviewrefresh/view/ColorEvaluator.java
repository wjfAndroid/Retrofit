package com.wjf.recyclerviewrefresh.view;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ColorEvaluator implements TypeEvaluator<String> {
    int mCurrentRed = -1;
    int mCurrentGreen = -1;
    int mCurrentBlue = -1;

    @Override
    public String evaluate(float fraction, String startValue, String endValue) {
        System.out.println("fraction = [" + fraction + "], startValue = [" + startValue + "], endValue = [" + endValue + "]");
        int startRed = Integer.parseInt(startValue.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startValue.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startValue.substring(5, 7), 16);
        int endRed = Integer.parseInt(endValue.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endValue.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endValue.substring(5, 7), 16);
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;

        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff, fraction);
        }


        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);

        return currentColor;
    }


    public int getCurrentColor(int startColor, int endColor, int colorDiff, int offest, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (colorDiff * fraction - offest));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (colorDiff * fraction - offest));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }

        return currentColor;
    }

    public String getHexString(int color) {
        String c = Integer.toHexString(color);
        if (c.length() == 1) {
            c = "0" + c;
        }
        return c;
    }
}
