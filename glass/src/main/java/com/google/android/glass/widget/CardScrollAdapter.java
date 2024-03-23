//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.google.android.glass.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CardScrollAdapter extends BaseAdapter {
    public CardScrollAdapter() {
        throw new RuntimeException("Stub!");
    }

    public abstract int getCount();

    public abstract Object getItem(int var1);

    public abstract View getView(int var1, View var2, ViewGroup var3);

    public int getItemViewType(int position) {
        throw new RuntimeException("Stub!");
    }

    public int getViewTypeCount() {
        throw new RuntimeException("Stub!");
    }

    public long getItemId(int position) {
        throw new RuntimeException("Stub!");
    }

    public abstract int getPosition(Object var1);

    public int getHomePosition() {
        throw new RuntimeException("Stub!");
    }
}
