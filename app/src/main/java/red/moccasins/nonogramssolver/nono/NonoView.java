package red.moccasins.nonogramssolver.nono;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Автор: Левшин Николай, 707 группа.
 * Дата создания: 19.10.2015.
 */
public class NonoView extends View {

    protected boolean measurementChanged = false;

    private Nonogram nonogram;

    private GestureDetector gestureDetector;

    public NonoView(Context context) {
        super(context);
        init();
    }

    public NonoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), gestureListener);
//        scaleGestureDetector = new ScaleGestureDetector(getContext(), scaleGestureListener);
//        scroller = new OverScroller(getContext());
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        public boolean onDown(MotionEvent e) {
            boolean touch = nonogram.SelectCell(e.getX(), e.getY());
            if (touch) invalidate();
            return touch;
        }

    };

    public void InitNewNonogram(int width, int height) {
        nonogram = new Nonogram(width, height);
    }

    public void AddVParam(int pos, int value) {
        nonogram.verticalParams.get(pos).add(value);
    }

    public void  AddHParam(int pos, int value) {
        nonogram.horizontalParams.get(pos).add(value);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (isInEditMode()) return;

        if (measurementChanged) {
            measurementChanged = false;
            nonogram.initializeTable(getMeasuredWidth(), getMeasuredHeight(), getContext());
        }

        nonogram.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measurementChanged = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {}
        return super.onTouchEvent(event) || gestureDetector.onTouchEvent(event);
    }
}
