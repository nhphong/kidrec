package com.eezy.kidrec.util;

import android.graphics.Point;

import java.util.List;

public class GeoUtil {

    public static boolean isPointInPolygon(Point tap, List<Point> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private static boolean rayCastIntersect(Point tap, Point vertA, Point vertB) {
        double aY = vertA.x;
        double bY = vertB.x;
        double aX = vertA.y;
        double bX = vertB.y;
        double pY = tap.x;
        double pX = tap.y;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY) || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!
        return x > pX;
    }
}
