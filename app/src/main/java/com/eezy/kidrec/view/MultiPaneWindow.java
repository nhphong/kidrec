package com.eezy.kidrec.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.eezy.kidrec.model.Pane;

import java.util.ArrayList;
import java.util.List;

public class MultiPaneWindow extends View implements View.OnTouchListener {

    private List<Pane> mPanes;

    public MultiPaneWindow(Context context) {
        super(context);
        init();
    }

    public MultiPaneWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MultiPaneWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPanes = new ArrayList<>();
        setOnTouchListener(this);
    }

    public void addPane(Pane pane) {
        mPanes.add(pane);
    }

    private Pane getClickedPane(int x, int y) {
        for (Pane pane : mPanes) {
            if (pane.isClicked(x, y)) {
                return pane;
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Pane pane : mPanes) {
            pane.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Pane p = getClickedPane(x, y);
            Toast.makeText(getContext(), p != null ? p.getName() : "outside", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
