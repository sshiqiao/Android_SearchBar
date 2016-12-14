package com.start.searchbardemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.start.searchbardemo.R;
import com.start.searchbardemo.utils.Utils;


/**
 * Created by Start on 16/12/2.
 */

public class SearchBar extends AppCompatEditText implements View.OnTouchListener {


    private Context context;
    private boolean isFirstIn;
    private boolean isStretch;
    private float searchBarStretchWidth;
    private float currentSearchBarStretchWidth;
    private float searchBarHandleHeight;
    private float searchBarOriginX;
    private SearchBarStretchDirection searchBarStretchDirection;
    private String searchBarHintString;
    private int searchBarEdgeColor;
    private int searchBarBackgroundColor;
    private int searchBarEdgePadding;
    private boolean isStartSearch;
    private int searchBarSearchingColor;
    private float rotateDegrees;

    private float leftStartDrawableX;
    private float leftStartDrawableY;
    private float leftEndDrawableX;
    private float leftEndDrawableY;
    private float leftDrawableA;
    private float leftDrawableB;
    private float leftDrawableC;

    private float rightStartDrawableX;
    private float rightStartDrawableY;
    private float rightEndDrawableX;
    private float rightEndDrawableY;
    private float rightDrawableA;
    private float rightDrawableB;
    private float rightDrawableC;

    private Paint mPaint;
    private TouchState touchState;

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public void init(Context context, AttributeSet attrs) {
        this.context = context;
        isFirstIn = true;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBar);
        isStretch = typedArray.getBoolean(R.styleable.SearchBar_searchbar_stretch, false);
        searchBarBackgroundColor = typedArray.getColor(R.styleable.SearchBar_searchbar_background_color,Color.WHITE);
        searchBarEdgeColor = typedArray.getColor(R.styleable.SearchBar_searchbar_edge_color,Color.BLACK);
        searchBarSearchingColor = typedArray.getColor(R.styleable.SearchBar_searchbar_searching_color,Color.WHITE);
        searchBarStretchWidth = typedArray.getDimension(R.styleable.SearchBar_searchbar_stretch_width,Utils.dp2px(context, 250));
        searchBarStretchDirection = typedArray.getInteger(R.styleable.SearchBar_searchbar_stretch_direction,0)==0?SearchBarStretchDirection.SEARCHBAR_STRETCH_LEFT:SearchBarStretchDirection.SEARCHBAR_STRETCH_RIGHT;
        isStartSearch = false;
        rotateDegrees = 0;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(searchBarEdgeColor);
        mPaint.setStrokeWidth(Utils.dp2px(context,2));
        mPaint.setStyle(Paint.Style.STROKE);

        touchState = isStretch?TouchState.TOUCH_UP_STATE:TouchState.TOUCH_CANCEL_STATE;
        setOnTouchListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setSingleLine();
        searchBarHandleHeight = (int)((getHeight()/2.0-searchBarEdgePadding)*Math.cos(Math.PI/4.0));
        setPadding((int)(getHeight()/2.0), 0, (int)(getHeight()/2.0+searchBarHandleHeight/2.0), 0);
        if(isFirstIn){
            isFirstIn = false;
            searchBarOriginX = getLeft();
            currentSearchBarStretchWidth = getHeight();
            if(this.getHint()!=null){
                searchBarHintString = this.getHint().toString();
            }
            if(!isStretch){
                this.setHint("");
            }
        }
        if(searchBarStretchDirection == SearchBarStretchDirection.SEARCHBAR_STRETCH_RIGHT) {
            leftStartDrawableX = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            leftStartDrawableY = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            leftEndDrawableX = (int) (searchBarStretchWidth - getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
            leftEndDrawableY = (int) (getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));

            rightStartDrawableX = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            rightStartDrawableY = (int) (getHeight() / 2.0 - (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            rightEndDrawableX = (int) (searchBarStretchWidth - getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
            rightEndDrawableY = (int) (getHeight() / 2.0 + searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
        }else{
            leftStartDrawableX = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            leftStartDrawableY = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            leftEndDrawableX = (int) (searchBarStretchWidth - getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
            leftEndDrawableY = (int) (getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));

            rightStartDrawableX = (int) (getHeight() / 2.0 + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            rightStartDrawableY = (int) (getHeight() / 2.0 - (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
            rightEndDrawableX = (int) (searchBarStretchWidth - getHeight() / 2.0 - searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
            rightEndDrawableY = (int) (getHeight() / 2.0 + searchBarHandleHeight / 2.0 * (Math.cos(Math.PI / 4)));
        }
        leftDrawableA = (float)((leftEndDrawableY - leftStartDrawableY)/Math.pow((leftEndDrawableX - leftStartDrawableX),2));
        leftDrawableB = -2*leftDrawableA*leftStartDrawableX;
        leftDrawableC = (float)(leftStartDrawableY + leftDrawableA*Math.pow(leftStartDrawableX,2));

        rightDrawableA = (float)((rightEndDrawableY - rightStartDrawableY)/Math.pow((rightEndDrawableX - rightStartDrawableX),2));
        rightDrawableB = -2*rightDrawableA*rightStartDrawableX;
        rightDrawableC = (float)(rightStartDrawableY + rightDrawableA*Math.pow(rightStartDrawableX,2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (touchState) {
            case TOUCH_DOWN_STATE:
                break;
            case TOUCH_MOVE_STATE:
                break;
            case TOUCH_UP_STATE:
                if(isStretch){
                    if(currentSearchBarStretchWidth < searchBarStretchWidth) {
                        currentSearchBarStretchWidth+= searchBarStretchWidth/10;
                        if(currentSearchBarStretchWidth>searchBarStretchWidth){
                            currentSearchBarStretchWidth = searchBarStretchWidth;
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) currentSearchBarStretchWidth, getHeight());
                        if(searchBarStretchDirection == SearchBarStretchDirection.SEARCHBAR_STRETCH_RIGHT) {
                            layoutParams.leftMargin = getLeft();
                        }else{
                            layoutParams.leftMargin = (int)(searchBarOriginX-(currentSearchBarStretchWidth - getHeight()/2));
                        }
                        layoutParams.topMargin = getTop();
                        setLayoutParams(layoutParams);
                    }
                }else {
                    if(currentSearchBarStretchWidth>getHeight()) {
                        currentSearchBarStretchWidth-=searchBarStretchWidth/10;
                        if(currentSearchBarStretchWidth<getHeight()) {
                            currentSearchBarStretchWidth = getHeight();
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)currentSearchBarStretchWidth, getHeight());
                        if(searchBarStretchDirection == SearchBarStretchDirection.SEARCHBAR_STRETCH_RIGHT){
                            layoutParams.leftMargin = getLeft();
                        }else {
                            layoutParams.leftMargin = (int)(searchBarOriginX-(currentSearchBarStretchWidth - getHeight()/2));
                        }
                        layoutParams.topMargin = getTop();
                        setLayoutParams(layoutParams);
                    }
                }
                break;
            case TOUCH_CANCEL_STATE:
                break;
        }
        setLayerBg();
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchState = TouchState.TOUCH_DOWN_STATE;
                break;
            case MotionEvent.ACTION_MOVE:
                touchState = TouchState.TOUCH_MOVE_STATE;
                break;
            case MotionEvent.ACTION_UP:
                if(!isStretch||(isStretch&&isInCloseRect(event.getX(),event.getY()))) {
                    isStretch = !isStretch;
                    isStartSearch = false;
                    setSearchBarStatus();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                touchState = TouchState.TOUCH_CANCEL_STATE;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setSearchBarStatus () {
        touchState = TouchState.TOUCH_UP_STATE;
        if(!isStretch){
            this.setCursorVisible(false);
            this.setText("");
            this.setHint("");
        }else{
            this.setCursorVisible(true);
            this.setHint(searchBarHintString);
        }
    }

    public void startSearching(boolean startSearchingAnimation) {
        isStretch = false;
        isStartSearch = startSearchingAnimation;
        rotateDegrees = 0;
        setSearchBarStatus();
    }

    public void finishSearching() {
        isStretch = false;
        isStartSearch = false;
        setSearchBarStatus();
    }

    public boolean isInCloseRect(float x, float y) {
        boolean isInCloseRect;
        float x0 = searchBarStretchWidth-getHeight()+searchBarEdgePadding;
        float y0 = searchBarEdgePadding;
        float x1 = searchBarStretchWidth-searchBarEdgePadding*2;
        float y1 = getHeight()-searchBarEdgePadding*2;
        if((x0<x&&x<x1)&&(y0<y&&y<y1)){
            isInCloseRect = true;
        }else{
            isInCloseRect = false;
        }
        return isInCloseRect;
    }

    private void setLayerBg(){

        float radius = (float)(getHeight()/2.0);
        float[] outerR = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };
        RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);

        ShapeDrawable backgroundDrawable = new ShapeDrawable();
        backgroundDrawable.setShape(roundRectShape);
        backgroundDrawable.getPaint().setColor(searchBarBackgroundColor);

        ShapeDrawable edgeDrawable = new ShapeDrawable();
        edgeDrawable.setShape(roundRectShape);
        edgeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        edgeDrawable.getPaint().setStrokeWidth(Utils.dp2px(context, 2));
        edgeDrawable.getPaint().setColor(searchBarEdgeColor);


        LeftDrawable leftDrawable = new LeftDrawable(new RectShape());
        leftDrawable.setBounds(0,0,getWidth(),getHeight());

        RightDrawable rightDrawable = new RightDrawable(new RectShape());
        rightDrawable.setBounds(0,0,getWidth(),getHeight());
        LayerDrawable layerDrawable ;
        if(isStartSearch&&(getWidth() == getHeight())){
            CircleDrawable circleDrawable = new CircleDrawable(new RectShape());
            circleDrawable.setBounds(0,0,getWidth(),getHeight());
            Drawable[] layers = {backgroundDrawable,edgeDrawable,leftDrawable,rightDrawable,circleDrawable};
            layerDrawable = new LayerDrawable(layers);
        }else{
            Drawable[] layers = {backgroundDrawable,edgeDrawable,leftDrawable,rightDrawable};
            layerDrawable = new LayerDrawable(layers);
        }

        searchBarEdgePadding = (int)(mPaint.getStrokeWidth()+Utils.dp2px(context, 5));
        layerDrawable.setLayerInset(0, searchBarEdgePadding, searchBarEdgePadding, searchBarEdgePadding, searchBarEdgePadding);
        layerDrawable.setLayerInset(1, searchBarEdgePadding, searchBarEdgePadding, searchBarEdgePadding, searchBarEdgePadding);

        setBackground(layerDrawable);

    }

    enum SearchBarStretchDirection {
        SEARCHBAR_STRETCH_LEFT, SEARCHBAR_STRETCH_RIGHT
    }

    enum TouchState {
        TOUCH_DOWN_STATE,TOUCH_MOVE_STATE,TOUCH_UP_STATE,TOUCH_CANCEL_STATE
    }

    class CircleDrawable extends ShapeDrawable {
        public CircleDrawable(Shape shape) {
            super(shape);
        }
        public void onDraw(Shape shape, Canvas canvas, Paint paint) {
            canvas.rotate(rotateDegrees, (float)(getHeight()/2.0), (float)(getHeight()/2.0));
            rotateDegrees+=8;
            if(rotateDegrees == 360){
                rotateDegrees=0;
            }
            mPaint.setColor(searchBarSearchingColor);
            float center = (float)(getHeight()/2.0);
            float radius = (float)(getHeight()/2.0-searchBarEdgePadding);
            canvas.drawArc(new RectF(center-radius, center-radius, center+radius, center+radius), -180, 45, false, mPaint);
            mPaint.setColor(searchBarEdgeColor);
        }
    }

    class LeftDrawable extends ShapeDrawable {
        public LeftDrawable(Shape shape) {
            super(shape);
        }
        public void onDraw(Shape shape, Canvas canvas, Paint paint) {
            float startX = (float)(getHeight()/2.0+(currentSearchBarStretchWidth-getHeight())+(getHeight()/2.0-searchBarEdgePadding)*Math.cos(Math.PI/4.0));
            if(startX>leftEndDrawableX){
                startX = leftEndDrawableX;
            }
            float startY = (float)(leftDrawableA*Math.pow(startX,2)+leftDrawableB*startX+leftDrawableC);
            float stopX = (float)(startX+searchBarHandleHeight*Math.cos(Math.PI/4.0));
            float stopY = (float)(startY+searchBarHandleHeight*Math.cos(Math.PI/4.0));
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        }
    }
    class RightDrawable extends ShapeDrawable {
        public RightDrawable(Shape shape) {
            super(shape);
        }
        public void onDraw(Shape shape, Canvas canvas, Paint paint) {
            if(currentSearchBarStretchWidth>searchBarStretchWidth*3.0/4.0) {
                if(!isStretch) {
                    mPaint.setAlpha(0);
                }else{
                    mPaint.setAlpha((int) ((currentSearchBarStretchWidth - searchBarStretchWidth*3.0/4.0) / (searchBarStretchWidth/4.0) * 255));
                }
                float startX = (float) (getHeight() / 2.0 + (currentSearchBarStretchWidth - getHeight()) + (getHeight() / 2.0 - searchBarEdgePadding) * Math.cos(Math.PI / 4.0));
                if (startX > rightEndDrawableX) {
                    startX = rightEndDrawableX;
                }
                float startY = (float) (rightDrawableA * Math.pow(startX, 2) + rightDrawableB * startX + rightDrawableC);
                float stopX = (float) (startX + searchBarHandleHeight * Math.cos(Math.PI / 4.0));
                float stopY = (float) (startY - searchBarHandleHeight * Math.cos(Math.PI / 4.0));
                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
                mPaint.setAlpha(255);
            }
        }
    }
}
