package com.example.firebase_6;

import android.content.Context;
import android.graphics.Canvas;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

public class HamburgerDrawable extends DrawerArrowDrawable {

    public HamburgerDrawable(Context context){
        super(context);
        setColor(context.getResources().getColor(R.color.colorGrey));
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        setBarLength(60.0f);
        setBarThickness(6.5f);
        setGapSize(10.0f);
    }
}