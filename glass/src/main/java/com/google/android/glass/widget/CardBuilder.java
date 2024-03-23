package com.google.android.glass.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

public class CardBuilder {
    public CardBuilder(Context context, Layout layout) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setText(CharSequence text) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setText(int textId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setFootnote(CharSequence footnote) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setFootnote(int footnoteId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setTimestamp(CharSequence timestamp) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setTimestamp(int timestampId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setHeading(CharSequence heading) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setHeading(int headingId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setSubheading(CharSequence subheading) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setSubheading(int subheadingId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder addImage(Drawable imageDrawable) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder addImage(Bitmap imageBitmap) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder addImage(int imageId) {
        throw new RuntimeException("Stub!");
    }

    public void clearImages() {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setIcon(Drawable iconDrawable) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setIcon(Bitmap iconBitmap) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setIcon(int iconId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setAttributionIcon(Drawable iconDrawable) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setAttributionIcon(Bitmap iconBitmap) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setAttributionIcon(int iconId) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder showStackIndicator(boolean visible) {
        throw new RuntimeException("Stub!");
    }

    public CardBuilder setEmbeddedLayout(int layoutResId) {
        throw new RuntimeException("Stub!");
    }

    public View getView() {
        throw new RuntimeException("Stub!");
    }

    public View getView(View convertView, ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }

    public RemoteViews getRemoteViews() {
        throw new RuntimeException("Stub!");
    }

    public int getItemViewType() {
        throw new RuntimeException("Stub!");
    }

    public static int getViewTypeCount() {
        throw new RuntimeException("Stub!");
    }

    public static enum Layout {
        ALERT,
        AUTHOR,
        CAPTION,
        COLUMNS,
        COLUMNS_FIXED,
        EMBED_INSIDE,
        MENU,
        TEXT,
        TEXT_FIXED,
        TITLE;

        private Layout() {
            throw new RuntimeException("Stub!");
        }
    }
}