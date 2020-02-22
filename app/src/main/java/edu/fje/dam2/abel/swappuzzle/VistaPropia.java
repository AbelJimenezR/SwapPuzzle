package edu.fje.dam2.abel.swappuzzle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class VistaPropia extends View {

        private float x;
        private float y;
        private String cadena;

        private int color;


        public VistaPropia(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.VistaPropia,
                    0, 0);

            try {
                x=0;
                y=0;
                cadena = a.getString(R.styleable.VistaPropia_cadena);
            } finally {
                a.recycle();
            }
        }
        RectF r;
        Paint paint;
        @Override
        protected void onDraw(Canvas canvas) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            r = new RectF(x, y, x+400  , y+100);
            canvas.drawRoundRect(r ,50,50,paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText(cadena, r.centerX()-70, r.centerY()+18, paint);

        }

        public String getCadena() {
            return cadena;
        }

        public void setCadena(String cadena) {
            this.cadena = cadena;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public void setX(float x) {
            this.x = x;
        }

        @Override
        public float getY() {
            return y;
        }

        @Override
        public void setY(float y) {
            this.y = y;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }


    }