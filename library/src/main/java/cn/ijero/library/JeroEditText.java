package cn.ijero.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 自定义右下角带字数显示的编辑框
 * Created by Jero on 2017/3/6.
 */

public class JeroEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    private int defTextColor = Color.parseColor("#FFCCCCCC");
    private int maxTextColor = Color.parseColor("#FFE02E54");
    private int maxLength = 20;
    private float defTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
    private TextPaint defPaint = new TextPaint();
    private TextPaint maxPaint = new TextPaint();
    private int maxTextWidth;
    private float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    private int length;
    private int maxMarginRight = 3;
    private boolean isShowTip = true;

    public JeroEditText(Context context) {
        this(context, null);
    }

    public JeroEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JeroEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyStyle(attrs);

        if (isShowTip)
            init();
    }

    private void applyStyle(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.JeroEditText);
        defTextColor = ta.getColor(R.styleable.JeroEditText_jet_defTextColor, defTextColor);
        maxTextColor = ta.getColor(R.styleable.JeroEditText_jet_maxTextColor, maxTextColor);
        maxLength = ta.getInteger(R.styleable.JeroEditText_jet_maxLength, maxLength);
        defTextSize = ta.getDimensionPixelSize(R.styleable.JeroEditText_jet_defTextSize, (int) defTextSize);
        isShowTip = ta.getBoolean(R.styleable.JeroEditText_jet_showMaxTip, isShowTip);
        ta.recycle();
    }

    private void init() {
        initSettings();
        initDefStyle();
        initPaints();
    }

    private void initSettings() {
        setFocusableInTouchMode(true);
    }

    private void initDefStyle() {
        this.setMaxLength(maxLength);
        this.addTextChangedListener(this);
    }

    private void initPaints() {
        // 抗锯齿
        defPaint.setAntiAlias(true);
        maxPaint.setAntiAlias(true);

        // 设置tipPaint参数
        defPaint.setTextSize(defTextSize);
        defPaint.setColor(defTextColor);

        // 设置fillPaint参数
        maxPaint.setTextSize(defTextSize);
        maxPaint.setColor(maxTextColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isShowTip)
            drawTip(canvas);
    }

    private void drawTip(Canvas canvas) {
        String maxStr = "/" + String.valueOf(maxLength);

        // 绘制最大的限制字符串
        int x = (int) (getWidth() - getTextMeasureWH(maxStr)[0] - margin);
        int y = (int) (getHeight() - margin);

        String tempLength = String.valueOf(length);
        maxTextWidth = getTextMeasureWH(tempLength)[0];

        canvas.drawText(maxStr, x, y, defPaint);
        if (length == maxLength)
            canvas.drawText(tempLength, x - maxTextWidth - maxMarginRight, y, maxPaint);
        else
            canvas.drawText(tempLength, x - maxTextWidth - maxMarginRight, y, defPaint);
    }


    private int[] getTextMeasureWH(String str) {
        int[] wh = new int[2];
        Rect rect = new Rect();
        defPaint.getTextBounds(str, 0, str.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        wh[0] = rect.width();
        wh[1] = rect.height();
        return wh;
    }

    /**
     * 获取提示文本的颜色
     *
     * @return
     */
    public int getDefTextColor() {
        return defTextColor;
    }

    /**
     * 设置提示文本的颜色
     *
     * @param defTextColor
     */
    public void setDefTextColor(int defTextColor) {
        this.defTextColor = defTextColor;
    }

    /**
     * 获取达到最大字数限制时的文本颜色
     *
     * @return
     */
    public int getMaxTextColor() {
        return maxTextColor;
    }

    /**
     * 设置达到最大字数限制时的文本颜色
     *
     * @param maxTextColor
     */
    public void setMaxTextColor(int maxTextColor) {
        this.maxTextColor = maxTextColor;
    }

    /**
     * 获取最大字数限制
     *
     * @return
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * 设置最大字数长度限制
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        invalidate();
    }

    /**
     * 获取最大字数时的文本大小
     *
     * @return
     */
    public float getDefTextSize() {
        return defTextSize;
    }

    /**
     * 设置最大字数时的文本大小
     *
     * @param defTextSize
     */
    public void setDefTextSize(float defTextSize) {
        this.defTextSize = defTextSize;
    }

    /**
     * 获取最大限制的文本画笔
     *
     * @return
     */
    public TextPaint getDefPaint() {
        return defPaint;
    }

    /**
     * 获取达到最大字数限制时的文本画笔
     *
     * @return
     */
    public TextPaint getMaxPaint() {
        return maxPaint;
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public final void afterTextChanged(Editable s) {
        length = s.length();
        invalidate();
    }
}
