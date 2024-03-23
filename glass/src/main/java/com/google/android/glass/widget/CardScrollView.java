//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.google.android.glass.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class CardScrollView extends AdapterView<CardScrollAdapter> {
    public CardScrollView(Context context) {
        super((Context)null, (AttributeSet)null, 0);
        throw new RuntimeException("Stub!");
    }

    public CardScrollView(Context context, AttributeSet attrs) {
        super((Context)null, (AttributeSet)null, 0);
        throw new RuntimeException("Stub!");
    }

    public CardScrollView(Context context, AttributeSet attrs, int defStyle) {
        super((Context)null, (AttributeSet)null, 0);
        throw new RuntimeException("Stub!");
    }

    public void activate() {
        throw new RuntimeException("Stub!");
    }

    public void deactivate() {
        throw new RuntimeException("Stub!");
    }

    public boolean animate(int position, Animation animationType) {
        throw new RuntimeException("Stub!");
    }

    public CardScrollAdapter getAdapter() {
        throw new RuntimeException("Stub!");
    }

    public void setAdapter(CardScrollAdapter adapter) {
        throw new RuntimeException("Stub!");
    }

    public boolean isActivated() {
        throw new RuntimeException("Stub!");
    }

    public int getSelectedItemPosition() {
        throw new RuntimeException("Stub!");
    }

    public View getSelectedView() {
        throw new RuntimeException("Stub!");
    }

    public long getSelectedItemId() {
        throw new RuntimeException("Stub!");
    }

    public void setSelection(int position) {
        throw new RuntimeException("Stub!");
    }

    public void setEmptyView(View emptyView) {
        throw new RuntimeException("Stub!");
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        throw new RuntimeException("Stub!");
    }

    protected void initializeScrollbars(TypedArray a) {
        throw new RuntimeException("Stub!");
    }

    public void setHorizontalScrollBarEnabled(boolean enable) {
        throw new RuntimeException("Stub!");
    }

    public boolean isHorizontalScrollBarEnabled() {
        throw new RuntimeException("Stub!");
    }

    protected boolean awakenScrollBars() {
        throw new RuntimeException("Stub!");
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        throw new RuntimeException("Stub!");
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        throw new RuntimeException("Stub!");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        throw new RuntimeException("Stub!");
    }

    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        throw new RuntimeException("Stub!");
    }

    public static enum Animation {
        DELETION,
        INSERTION,
        NAVIGATION;

        private Animation() {
            throw new RuntimeException("Stub!");
        }
    }
}
