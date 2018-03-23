package com.opera.app.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.opera.app.MainApplication;
import com.opera.app.R;

public class EditTextWithFont extends android.support.v7.widget.AppCompatEditText {

    private int defaultDimension = 0;

    private int TYPE_BOLD = 1;

    private int TYPE_ITALIC = 2;

    private int FONT_LIGHT = 1;

    private int FONT_MEDIUM = 2;

    private int FONT_REGULAR = 3;

    private int FONT_BOLD = 4;

    private int fontType;

    private int fontName;

    public EditTextWithFont(Context context) {
        super(context);
        init(null, 0);
    }

    public EditTextWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EditTextWithFont(Context context, AttributeSet attrs, int defStyle) {
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
            } else if (fontName == FONT_BOLD) {
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
            setTypeface(font);
        }
    }

}
