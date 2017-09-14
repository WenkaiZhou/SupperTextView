package com.kevin.suppertextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhouwenkai on 2017/9/3.
 */

@SuppressLint("AppCompatCustomView")
public class SupperTextView extends TextView {

    private GradientDrawable normalGD;
    private GradientDrawable pressedGD;
    private GradientDrawable disabledGD;
    private StateListDrawable selector;

    private int mStrokeWidth;
    private int mStrokeColor;

    private int mRadius;
    private int leftTopRadius;
    private int leftBottomRadius;
    private int rightTopRadius;
    private int rightBottomRadius;

    private int normalTextColor;
    private int selectedTextColor;

    private int normalSolidColor;
    private int pressedSolidColor;
    private int disabledSolidColor;

    private boolean noLeftStroke;
    private boolean noRightStroke;
    private boolean noTopStroke;
    private boolean noBottomStroke;


    private boolean mIsSelected;

    public SupperTextView(Context context) {
        super(context);
    }

    public SupperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
    }

    public SupperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context, attrs);
    }


    private void setAttributeSet(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SupperTextView);
        mStrokeColor = a.getColor(R.styleable.SupperTextView_textStrokeColor, Color.TRANSPARENT);
        mRadius = a.getDimensionPixelSize(R.styleable.SupperTextView_textRadius, 0);
        leftTopRadius = a.getDimensionPixelSize(R.styleable.SupperTextView_textLeftTopRadius, 0);
        leftBottomRadius = a.getDimensionPixelSize(R.styleable.SupperTextView_textLeftBottomRadius, 0);
        rightTopRadius = a.getDimensionPixelSize(R.styleable.SupperTextView_textRightTopRadius, 0);
        rightBottomRadius = a.getDimensionPixelSize(R.styleable.SupperTextView_textRightBottomRadius, 0);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.SupperTextView_textStrokeWidth, 0);
        normalTextColor = a.getColor(R.styleable.SupperTextView_textNormalTextColor, Color.TRANSPARENT);
        selectedTextColor = a.getColor(R.styleable.SupperTextView_textSelectedTextColor, Color.TRANSPARENT);
        normalSolidColor = a.getColor(R.styleable.SupperTextView_textNormalSolidColor, Color.TRANSPARENT);
        pressedSolidColor = a.getColor(R.styleable.SupperTextView_textPressedSolidColor, Color.TRANSPARENT);
        disabledSolidColor = a.getColor(R.styleable.SupperTextView_textDisabledSolidColor, Color.TRANSPARENT);
        mIsSelected = a.getBoolean(R.styleable.SupperTextView_textIsSelected, false);
        noLeftStroke = a.getBoolean(R.styleable.SupperTextView_textNoLeftStroke, false);
        noRightStroke = a.getBoolean(R.styleable.SupperTextView_textNoRightStroke, false);
        noTopStroke = a.getBoolean(R.styleable.SupperTextView_textNoTopStroke, false);
        noBottomStroke = a.getBoolean(R.styleable.SupperTextView_textNoBottomStroke, false);
        BitmapDrawable textDrawable = (BitmapDrawable) a.getDrawable(R.styleable.SupperTextView_textDrawable);

        a.recycle();

        setStateListDrawable();

        setTextDrawable(textDrawable);

        setTextColor(normalTextColor, selectedTextColor);

        setClickable(true);
    }

    private void setStateListDrawable() {
        selector = new StateListDrawable();
        normalGD = new GradientDrawable();
        pressedGD = new GradientDrawable();
        disabledGD = new GradientDrawable();

        // set selected state
        setPressedState(leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius, mStrokeColor,
                pressedSolidColor, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke, mIsSelected);

        // set disabled state
        setDisabledState(leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius, mStrokeColor,
                disabledSolidColor, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke);

        // set normal state
        setNormalState(leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius, mStrokeColor,
                normalSolidColor, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke);

        // set selector
        setBackgroundDrawable(selector);
    }

    /**
     * 设置正常状态下drawable
     *
     * @param leftTopRadius     左上角角度
     * @param leftBottomRadius  左下角角度
     * @param rightBottomRadius 右下角角度
     * @param rightTopRadius    右上角角度
     * @param strokeColor       描边颜色
     * @param normalSolid       正常状态下填充颜色
     * @param noLeftStroke      无左描边
     * @param noRightStroke     无右描边
     * @param noTopStroke       无上描边
     * @param noBottomStroke    无底描边
     */
    private void setNormalState(int leftTopRadius, int leftBottomRadius, int rightBottomRadius, int rightTopRadius,
                                int strokeColor, int normalSolid, boolean noLeftStroke,
                                boolean noRightStroke, boolean noTopStroke, boolean noBottomStroke) {

        //设置正常状态下填充色
        normalGD.setColor(normalSolid);
        //设置描边
        normalGD.setStroke(mStrokeWidth, strokeColor);
        //设置圆角
        setRadius(normalGD, leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius);
        //normal drawable
        LayerDrawable normalLayerDrawable = new LayerDrawable(new Drawable[]{normalGD});
        //设置正常状态下描边边距
        setStrokeMargin(normalLayerDrawable, 0, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke);
        //设置正常状态下的drawable
        selector.addState(new int[]{}, normalLayerDrawable);
    }

    /**
     * 设置Disable状态下drawable
     *
     * @param leftTopRadius     左上角角度
     * @param leftBottomRadius  左下角角度
     * @param rightBottomRadius 右下角角度
     * @param rightTopRadius    右上角角度
     * @param strokeColor       描边颜色
     * @param disabledSolid     disabled状态填充色
     * @param noLeftStroke      无左描边
     * @param noRightStroke     无右描边
     * @param noTopStroke       无上描边
     * @param noBottomStroke    无底描边
     */
    private void setDisabledState(int leftTopRadius, int leftBottomRadius, int rightBottomRadius, int rightTopRadius,
                                  int strokeColor, int disabledSolid, boolean noLeftStroke,
                                  boolean noRightStroke, boolean noTopStroke, boolean noBottomStroke) {

        if (disabledSolid != Color.TRANSPARENT) {
            //设置Disable状态下填充色
            disabledGD.setColor(disabledSolid);
            //设置描边
            disabledGD.setStroke(mStrokeWidth, strokeColor);
            //设置圆角
            setRadius(disabledGD, leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius);
            //normal drawable
            LayerDrawable disableLayerDrawable = new LayerDrawable(new Drawable[]{disabledGD});
            //设置正常状态下描边边距
            setStrokeMargin(disableLayerDrawable, 0, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke);
            //设置Disable状态下的drawable
            selector.addState(new int[]{-android.R.attr.state_enabled}, disableLayerDrawable);
        }
    }

    /**
     * 设置按下状态drawable
     *
     * @param leftTopRadius     左上角角度
     * @param leftBottomRadius  左下角角度
     * @param rightBottomRadius 右下角角度
     * @param rightTopRadius    右上角角度
     * @param strokeColor       描边颜色
     * @param pressedSolid      按下状态填充色
     * @param noLeftStroke      无左描边
     * @param noRightStroke     无右描边
     * @param noTopStroke       无上描边
     * @param noBottomStroke    无底描边
     * @param isSelected        是否可以选择状态
     */
    private void setPressedState(int leftTopRadius, int leftBottomRadius, int rightBottomRadius, int rightTopRadius,
                                 int strokeColor, int pressedSolid, boolean noLeftStroke,
                                 boolean noRightStroke, boolean noTopStroke, boolean noBottomStroke, boolean isSelected) {

        if (pressedSolid != Color.TRANSPARENT) {
            //设置按下填充色
            pressedGD.setColor(pressedSolid);
            //设置选中状态下描边边距
            pressedGD.setStroke(mStrokeWidth, strokeColor);
            //设置圆角
            setRadius(pressedGD, leftTopRadius, leftBottomRadius, rightBottomRadius, rightTopRadius);

            LayerDrawable pressedLayerDrawable = new LayerDrawable(new Drawable[]{pressedGD});
            setStrokeMargin(pressedLayerDrawable, 0, noLeftStroke, noRightStroke, noTopStroke, noBottomStroke);
            //设置按下状态
            if (isSelected) {
                selector.addState(new int[]{android.R.attr.state_selected}, pressedLayerDrawable);
            } else {
                selector.addState(new int[]{android.R.attr.state_pressed}, pressedLayerDrawable);
            }
        }
    }

    /**
     * 设置角度
     *
     * @param drawable          drawable
     * @param leftTopRadius     左上角角度
     * @param leftBottomRadius  左下角角度
     * @param rightBottomRadius 右下角角度
     * @param rightTopRadius    右上角角度
     */
    private void setRadius(GradientDrawable drawable, int leftTopRadius, int leftBottomRadius,
                           int rightBottomRadius, int rightTopRadius) {
        if (mRadius != 0) {
            drawable.setCornerRadius(mRadius);
        } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
            drawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius,
                    rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        }
    }


    /**
     * 设置 button 四个边距
     *
     * @param layerDrawable LayerDrawable
     * @param index         下标
     * @param left          左边距
     * @param right         右边距
     * @param top           上边距
     * @param bottom        下边距
     */
    private void setStrokeMargin(LayerDrawable layerDrawable, int index, boolean left, boolean right, boolean top, boolean bottom) {

        int leftMargin = left ? -mStrokeWidth : 0;
        int rightMargin = right ? -mStrokeWidth : 0;
        int topMargin = top ? -mStrokeWidth : 0;
        int bottomMargin = bottom ? -mStrokeWidth : 0;

        layerDrawable.setLayerInset(index, leftMargin, topMargin, rightMargin, bottomMargin);
    }

    /**
     * 设置填充图片
     *
     * @param drawableId
     */
    public void setTextDrawable(@DrawableRes int drawableId) {
        if (drawableId != 0) {
            Drawable drawable = getResources().getDrawable(drawableId);
            setTextDrawable((BitmapDrawable) drawable);
        }
    }

    /**
     * @param drawable
     */
    public void setTextDrawable(BitmapDrawable drawable) {
        if (drawable != null) {
            ImageSpan imageSpan = new ImageSpan(getContext(), drawable.getBitmap());

            String text = "[icon]";
            SpannableString ss = new SpannableString("[icon]");

            ss.setSpan(imageSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }
    }

    /**
     * 设置填充颜色
     *
     * @param color 颜色id
     */
    public void setNormalSolidColor(@ColorInt int color) {
        normalSolidColor = color;
        setStateListDrawable();
    }

    /**
     * 设置边框颜色
     *
     * @param color
     */
    public void setStrokeColor(@ColorInt int color) {
        this.mStrokeColor = color;
        setStateListDrawable();
    }

    /**
     * 设置textView状态颜色
     *
     * @param normalTextColor   正常状态颜色
     * @param selectedTextColor 按下状态颜色
     */
    public void setTextColor(int normalTextColor, int selectedTextColor) {

        if (normalTextColor != 0 && selectedTextColor != 0) {
            //设置state_selected状态时，和正常状态时文字的颜色
            ColorStateList textColorSelect;

            if (mIsSelected) { //是否可以选中
                int[][] states = new int[2][1];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};
                textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, normalTextColor});
            } else {
                int[][] states = new int[3][1];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{android.R.attr.state_pressed};
                states[2] = new int[]{};
                textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
            }

            setTextColor(textColorSelect);
        }

    }
}
