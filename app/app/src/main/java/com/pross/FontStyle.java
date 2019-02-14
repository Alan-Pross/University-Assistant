package com.pross;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.TextView;

public class FontStyle {
    private Context mContext;
    private Typeface mTypeface;

    public FontStyle(Context context, String ttfPath) {
        mContext = context;
        mTypeface = getTypefaceFromTTF(ttfPath);
    }

    public Typeface getTypefaceFromTTF(String ttfPath) {

        if (ttfPath == null) {
            return Typeface.DEFAULT;
        } else {
            return Typeface.createFromAsset(mContext.getAssets(), ttfPath);
        }
    }

    public void setTypeface(TextView tv, boolean isBold) {
        tv.setTypeface(mTypeface);
        setBold(tv, isBold);
    }

    public void setBold(TextView tv, boolean isBold) {
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(isBold);
    }
}