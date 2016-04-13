package com.eezy.kidrec.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;

import com.eezy.kidrec.util.GeoUtil;

import java.util.ArrayList;
import java.util.List;

public class Pane {
    private String mName;
    private List<Point> mVertices;
    private Paint mPaint;

    public Pane(String name) {
        mName = name;
        mVertices = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    public void addVertex(int x, int y) {
        mVertices.add(new Point(x, y));
    }

    public void setBackground(Resources res, int bkResId) {
        Bitmap background = BitmapFactory.decodeResource(res, bkResId);

        Shader shader = new BitmapShader(background, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(getTransformedMatrix(background));
        mPaint.setShader(shader);
    }

    public void draw(Canvas canvas) {
        Path path = getPath();
        if (path != null) {
            canvas.drawPath(path, mPaint);
        }
    }

    public boolean isClicked(int x, int y) {
        return isValid() && GeoUtil.isPointInPolygon(new Point(x, y), mVertices);
    }

    public String getName() {
        return mName;
    }

    private Path getPath() {
        if (!isValid()) {
            return null;
        }

        Path path = new Path();
        Point firstPoint = mVertices.get(0);
        path.moveTo(firstPoint.x, firstPoint.y);

        for (int i = 1; i < mVertices.size(); ++i) {
            Point nextPoint = mVertices.get(i);
            path.lineTo(nextPoint.x, nextPoint.y);
        }
        path.lineTo(firstPoint.x, firstPoint.y);
        return path;
    }

    private boolean isValid() {
        return mVertices != null && mVertices.size() >= 3;
    }

    private Rect getBoundingRect() {
        if (!isValid()) {
            return null;
        }

        Point topLeftPivot = new Point(mVertices.get(0));
        Point rightBottomPivot = new Point(mVertices.get(0));
        for (int i = 1; i < mVertices.size(); ++i) {
            Point p = mVertices.get(i);
            topLeftPivot.x = p.x < topLeftPivot.x ? p.x : topLeftPivot.x;
            topLeftPivot.y = p.y < topLeftPivot.y ? p.y : topLeftPivot.y;
            rightBottomPivot.x = p.x > rightBottomPivot.x ? p.x : rightBottomPivot.x;
            rightBottomPivot.y = p.y > rightBottomPivot.y ? p.y : rightBottomPivot.y;
        }
        return new Rect(topLeftPivot.x, topLeftPivot.y, rightBottomPivot.x, rightBottomPivot.y);
    }

    private Matrix getTransformedMatrix(Bitmap background) {
        Rect boundingRect = getBoundingRect();
        Matrix result = new Matrix();
        if (boundingRect != null) {
            result.postScale((float)boundingRect.width() / background.getWidth(),
                    (float)boundingRect.height() / background.getHeight());
            result.postTranslate(boundingRect.left, boundingRect.top);
        }
        return result;
    }
}
