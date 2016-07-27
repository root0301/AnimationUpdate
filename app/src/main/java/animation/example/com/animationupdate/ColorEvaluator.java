package animation.example.com.animationupdate;

import android.animation.TypeEvaluator;

/**
 * Created by slience on 2016/7/27.
 */
public class ColorEvaluator implements TypeEvaluator {

    private int mRed = -1;

    private int mGreen = -1;

    private int mBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        int startRed = Integer.parseInt(startColor.substring(1,3),16);
        int startGreen = Integer.parseInt(startColor.substring(3,5),16);
        int startBlue = Integer.parseInt(startColor.substring(5,7),16);
        int endRed = Integer.parseInt(endColor.substring(1,3),16);
        int endGreen = Integer.parseInt(endColor.substring(3,5),16);
        int endBlue = Integer.parseInt(endColor.substring(5,7),16);

        if(mRed == -1) {
            mRed = startRed;
        }
        if(mGreen == -1) {
            mGreen = startGreen;
        }
        if (mBlue == -1 ) {
            mBlue = startBlue;
        }

        int subRed = Math.abs(endRed - startRed);
        int subGreen = Math.abs(endGreen - startGreen);
        int subBlue = Math.abs(endBlue - startBlue);
        int subColor = subBlue + subGreen + subRed;

        if(mRed != endRed) {
            mRed = getCurrentColor(startRed,endRed,subColor,0,fraction);
        } else if (mGreen != endGreen) {
            mGreen = getCurrentColor(startGreen, endGreen, subColor,subRed,fraction);
        } else if (mBlue != endBlue) {
            mBlue = getCurrentColor(startBlue,endBlue,subColor,startRed+startGreen,fraction);
        }
        String currentColor = "#" + getHexColor(mRed) + getHexColor(mGreen) + getHexColor(mBlue);
        return currentColor;
    }

    /**
     *  计算当前颜色
     */
    private int getCurrentColor(int startColor, int endColor, int subColor, int offset, float fraction) {

        int currentColor;
        if(startColor > endColor) {
            currentColor = (int)(startColor - (fraction * subColor - offset));
            if(currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int)(startColor + (fraction * subColor - offset));
            if(currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将十进制的颜色值转换成十六进制
     */
    private String getHexColor(int color) {
        String colorH = Integer.toHexString(color);
        if (colorH.length() == 1) {
            colorH = "0" + colorH;
        }
        return colorH;
    }
}
