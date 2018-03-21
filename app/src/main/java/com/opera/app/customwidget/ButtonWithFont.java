package com.opera.app.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.utils.OperaUtils;

public class ButtonWithFont extends android.support.v7.widget.AppCompatButton {


    private int defaultDimension = 0;

    private int TYPE_BOLD = 1;

    private int TYPE_ITALIC = 2;

    private int FONT_LIGHT = 1;

    private int FONT_MEDIUM = 2;

    private int FONT_REGULAR = 3;

    private int FONT_BOLD = 4;

    private int fontType;

    private int fontName;


    public ButtonWithFont(Context context) {
        super(context);
        init(null, 0);
    }

    public ButtonWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ButtonWithFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.font, defStyle, 0);

        fontName = a.getInt(R.styleable.font_name, defaultDimension);
        fontType = a.getInt(R.styleable.font_type, defaultDimension);

        a.recycle();
        if (!isInEditMode()) {
            MainApplication mainApplication = (MainApplication) getContext().getApplicationContext();
            if (fontName == FONT_LIGHT) {
                setFontType(mainApplication.getFontLight());
            } else if (fontName == FONT_MEDIUM) {
                setFontType(mainApplication.getFontMedium());
            } else if (fontName == FONT_REGULAR) {
                setFontType(mainApplication.getFontRegular());
            }else if (fontName == FONT_BOLD) {
                setFontType(mainApplication.getFontBold());
            }
        }
    }

    private void setFontType(Typeface font) {
        if (fontType == TYPE_BOLD) {
            setTypeface(font, Typeface.BOLD);
        } else if (fontType == TYPE_ITALIC) {
            setTypeface(font, Typeface.ITALIC);
        } else {
            setTypeface(font, Typeface.NORMAL);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable d) {
        CustomButtonBackgroundDrawable layer = new CustomButtonBackgroundDrawable(d);
        super.setBackgroundDrawable(layer);
    }


    protected class CustomButtonBackgroundDrawable extends LayerDrawable {

        protected ColorFilter pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);

        protected int disabledAlpha = 100;

        public CustomButtonBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled) {
                    enabled = true;
                } else if (state == android.R.attr.state_pressed) {
                    pressed = true;
                }
            }

            mutate();
            if (enabled && pressed) {
                setColorFilter(pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(disabledAlpha);
            } else {
                setColorFilter(null);
            }

            invalidateSelf();

            return super.onStateChange(states);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }

}
