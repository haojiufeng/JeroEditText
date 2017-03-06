package cn.ijero.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 自定义右下角带字数显示的编辑框
 * Created by Jero on 2017/3/6.
 */

public class JeroEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    private int maxTextColor = Color.parseColor("#FFCCCCCC");
    private int fillTextColor = Color.parseColor("#FFE02E54");
    private int maxLength = 20;
    private float maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
    private TextPaint maxPaint = new TextPaint();
    private TextPaint fillPaint = new TextPaint();
    private int fillTextWidth;
    private String tempText = "";

    public JeroEditText(Context context) {
        this(context, null);
    }

    public JeroEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JeroEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyStyle(attrs);
        init();
    }

    private void applyStyle(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.JeroEditText);
        maxTextColor = ta.getColor(R.styleable.JeroEditText_jet_maxTextColor, maxTextColor);
        fillTextColor = ta.getColor(R.styleable.JeroEditText_jet_fillTextColor, fillTextColor);
        maxLength = ta.getInteger(R.styleable.JeroEditText_jet_maxLength, maxLength);
        maxTextSize = ta.getDimensionPixelSize(R.styleable.JeroEditText_jet_tipTextSize, (int) maxTextSize);
        ta.recycle();
    }

    private void init() {
        initDefStyle();
        initPaints();
    }


    private void initDefStyle() {
        this.setMaxLength(maxLength);
        this.addTextChangedListener(this);
    }

    private void initPaints() {
        // 抗锯齿
        maxPaint.setAntiAlias(true);
        fillPaint.setAntiAlias(true);

        // 设置tipPaint参数
        maxPaint.setTextSize(maxTextSize);
        maxPaint.setColor(maxTextColor);

        // 设置fillPaint参数
        fillPaint.setTextSize(maxTextSize);
        fillPaint.setColor(fillTextColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTip(canvas);
    }

    private void drawTip(Canvas canvas) {
        String maxStr = " / " + String.valueOf(maxLength);

        // 绘制最大的限制字符串
        int x = getWidth() - getTextMeasureWH(maxStr)[0] - getPaddingRight();
        int y = getHeight() - getTextMeasureWH(maxStr)[1] - getPaddingBottom();

        String tempLength = String.valueOf(tempText.length());
        fillTextWidth = getTextMeasureWH(tempLength)[0];

        canvas.drawText(maxStr, x, y, maxPaint);
        canvas.drawText(tempLength, x - fillTextWidth, y, fillPaint);
    }


    private int[] getTextMeasureWH(String str) {
        int[] wh = new int[2];
        Rect rect = new Rect();
        maxPaint.getTextBounds(str, 0, str.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        wh[0] = rect.width();
        wh[1] = rect.height();
        return wh;
    }

    /**
     * 获取提示文本的颜色
     *
     * @return
     */
    public int getMaxTextColor() {
        return maxTextColor;
    }

    /**
     * 设置提示文本的颜色
     *
     * @param maxTextColor
     */
    public void setMaxTextColor(int maxTextColor) {
        this.maxTextColor = maxTextColor;
    }

    /**
     * 获取达到最大字数限制时的文本颜色
     *
     * @return
     */
    public int getFillTextColor() {
        return fillTextColor;
    }

    /**
     * 设置达到最大字数限制时的文本颜色
     *
     * @param fillTextColor
     */
    public void setFillTextColor(int fillTextColor) {
        this.fillTextColor = fillTextColor;
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
    public float getMaxTextSize() {
        return maxTextSize;
    }

    /**
     * 设置最大字数时的文本大小
     *
     * @param maxTextSize
     */
    public void setMaxTextSize(float maxTextSize) {
        this.maxTextSize = maxTextSize;
    }

    /**
     * 获取最大限制的文本画笔
     *
     * @return
     */
    public TextPaint getMaxPaint() {
        return maxPaint;
    }

    /**
     * 获取达到最大字数限制时的文本画笔
     *
     * @return
     */
    public TextPaint getFillPaint() {
        return fillPaint;
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public final void afterTextChanged(Editable s) {
        String trim = s.toString().trim();
        tempText = TextUtils.isEmpty(trim) ? "" : trim;
        invalidate();
    }
}
