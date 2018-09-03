package de.verygame.core.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class PolygonUtils {

    private static final int SEGMENT_SIZE = 12;

    private PolygonUtils() {
        //static only
    }

    public static Vector2[] constructRectanglePolygon(float width, float height) {
        float[][] poly = de.verygame.util.PolygonUtils.constructRectanglePolygon(width, height);

        Vector2[] wrapped = new Vector2[poly.length];

        for (int i = 0; i < poly.length; ++i) {
            wrapped[i] = new Vector2(poly[i][0], poly[i][1]);
        }

        return wrapped;
    }

    public static short[] getDefaultTriangles(int vertexCount) {
        short[] triangles = new short[vertexCount];
        for (short i = 0; i < triangles.length; i++) {
            triangles[i] = i;
        }

        return triangles;
    }

    public static float[] calculateCenter(float[] vertices) {
        return de.verygame.util.PolygonUtils.calculateCenter(vertices);
    }

    /**
     * Scales the given local polygon to the given width and height
     *
     * @param vertices polygon to scale
     * @param width    target width
     * @param height   target height
     * @return a new float array containing the scaled vertices
     */
    public static float[] scaleToSize(float[] vertices, float width, float height) {
        return de.verygame.util.PolygonUtils.scaleToSize(vertices, width, height);
    }

    public static float[] buildVertexArray(Vector2[] vertices) {

        float[] res = new float[vertices.length * 2];

        for (int i = 0, j = 0; j < vertices.length; i += 2, j++) {
            res[i] = vertices[j].x;
            res[i + 1] = vertices[j].y;
        }

        return res;
    }

    public static float[][] unwrapPolygon(Vector2[] v){
        return unwrapPolygon(v, 1, 1);
    }

    public static float[][] unwrapPolygon(Vector2[] verts, float xScale, float yScale){
        float[][] unwrapped = new float[verts.length][2];


        for (int i = 0; i < verts.length; i++) {
            Vector2 v = verts[i];
            unwrapped[i][0] = verts[i].x*xScale;
            unwrapped[i][1] = verts[i].y*yScale;
        }

        return unwrapped;
    }

    public static FloatArray createBorderPolygon(FloatArray polygon, float width, float targetwidth, float targetHeight) {
        FloatArray scaledPolygon = new FloatArray(polygon);

        float naturalWidth = 0;
        float naturalHeight = 0;
        for (int j = 0; j < polygon.size; ++j) {
            float e = polygon.get(j);
            if (j % 2 == 0) {
                naturalWidth = Math.max(naturalWidth, e);
            } else {
                naturalHeight = Math.max(naturalHeight, e);
            }
        }

        for (int i = 0; i < polygon.size - 2; i += 2) {

            float x, y, x0, y0, x1, y1, x2, y2, l1, l2, xr, yr;


            if (i == 0) {
                x1 = polygon.get(polygon.size - 4);
                y1 = polygon.get(polygon.size - 3);
            } else {
                x1 = polygon.get(i - 2);
                y1 = polygon.get(i - 1);
            }

            x0 = polygon.get(i);
            y0 = polygon.get(i + 1);
            x2 = polygon.get((i + 2) % polygon.size);
            y2 = polygon.get((i + 3) % polygon.size);

            float v1x, v1y, v2x, v2y, correction;

            v1x = x1 - x0;
            v1y = y1 - y0;
            v2x = x2 - x0;
            v2y = y2 - y0;

            //correction if all x are the same
            correction = MathUtils.isEqual(v1x, 0) && MathUtils.isEqual(v2x, 0) ? width : 0;

            //length of vectors
            l1 = (float) Math.sqrt(v1x * v1x + v1y * v1y);
            l2 = (float) Math.sqrt(v2x * v2x + v2y * v2y);

            v1x /= l1;
            v1y /= l1;
            v2x /= l2;
            v2y /= l2;

            float nwc = naturalWidth / targetwidth;
            float nhc = naturalHeight / targetHeight;

            xr = v1x * width + v2x * width;
            yr = v1y * width + v2y * width;

            x = x0 + xr;
            y = y0 + yr;

            float xo, yo;

            if (!MathUtils.isEqual(v1x, 0)) {
                yo = 1;
                xo = -v1y * yo / v1x;
            } else {
                xo = 1;
                yo = -v1x * xo / v1y;
            }

            float s;
            if (MathUtils.isEqual(yo, 0)) {
                s = ((x0 - x) * (yo / xo) - (y0 - y)) / (-v1x * (yo / xo) + v1y);
            } else {
                s = ((y0 - y) * (xo / yo) - (x0 - x)) / (-v1y * (xo / yo) + v1x);
            }
            float olX = x0 + v1x * s;
            float olY = y0 + v1y * s;
            float olXV = olX - x0;
            float olYV = olY - y0;
            float lengthQ = Vector2.len(olXV, olYV) / Vector2.len(xr, yr);

            xr = v1x * width * nwc + v2x * width * nwc + correction * Math.signum(-v2y);
            yr = v1y * width * nhc + v2y * width * nhc;

            scaledPolygon.set(i, x0 + xr * lengthQ);
            scaledPolygon.set(i + 1, y0 + yr * lengthQ);
        }

        scaledPolygon.set(scaledPolygon.size - 2, scaledPolygon.get(0));
        scaledPolygon.set(scaledPolygon.size - 1, scaledPolygon.get(1));

        FloatArray borderPolygon = new FloatArray(polygon.size * SEGMENT_SIZE);

        for (int i = 0; i < polygon.size - 2; i += 2) {
            borderPolygon.add(polygon.get(i % polygon.size));
            borderPolygon.add(polygon.get((i + 1) % polygon.size));

            borderPolygon.add(polygon.get((i + 2) % polygon.size));
            borderPolygon.add(polygon.get((i + 3) % polygon.size));

            borderPolygon.add(scaledPolygon.get(i % polygon.size));
            borderPolygon.add(scaledPolygon.get((i + 1) % polygon.size));

            borderPolygon.add(polygon.get((i + 2) % polygon.size));
            borderPolygon.add(polygon.get((i + 3) % polygon.size));

            borderPolygon.add(scaledPolygon.get((i + 2) % polygon.size));
            borderPolygon.add(scaledPolygon.get((i + 3) % polygon.size));

            borderPolygon.add(scaledPolygon.get(i % polygon.size));
            borderPolygon.add(scaledPolygon.get((i + 1) % polygon.size));
        }
        return borderPolygon;
    }

    /**
     * Scales the border with a progress value.
     *
     * @param progress progress in percent between 0 and 1
     * @return new FloatArray containing the scaled border
     */
    public static FloatArray calculatePartialBorderPolygon(FloatArray polygon, float progress) {
        //Catch edge cases
        if (progress <= 0) {
            return FloatArray.with(0, 0, 0, 0, 0, 0);
        } else if (progress >= 1) {
            return polygon;
        }

        //Calc inner/outer border length
        float innerLength = calculateInnerLength(polygon);
        float outerLength = calculateOuterLength(polygon);

        innerLength *= progress;
        outerLength *= progress;

        FloatArray partialPolygon = new FloatArray((int) (progress * polygon.size));

        float inner = 0, outer = 0;
        int index = 0;
        Vector2 addedLength = new Vector2();


        while ((inner < innerLength || outer < outerLength) && index < polygon.size / 12) {
            addedLength = addSegment(polygon, index, partialPolygon);

            inner += addedLength.x;
            outer += addedLength.y;

            index++;
        }

        reduceLastSegment(partialPolygon, innerLength, outerLength, inner, outer, addedLength);

        return partialPolygon;
    }

    /**
     * Reduces the last segment to the actual needed length
     *
     * @param polygon     border polygon
     * @param targetInner target length for inner border edge
     * @param targetOuter target length for outer border edge
     * @param lastAdded   Vector2 containing the length of the last segment (x inner, y outer)
     */
    private static void reduceLastSegment(FloatArray polygon, float targetInner, float targetOuter, float currentInner, float currentOuter, Vector2 lastAdded) {
        float x1, y1, x2, y2, x3, y3, x4, y4;
        Vector2 innerEdge, outerEdge;
        int i = polygon.size - 12;

        float innerActual, outerActual;

        innerActual = lastAdded.x - (currentInner - targetInner);
        outerActual = lastAdded.y - (currentOuter - targetOuter);

        innerActual = Math.max(innerActual, 0);
        outerActual = Math.max(outerActual, 0);

        x1 = polygon.get(i + 8);
        y1 = polygon.get(i + 9);

        x2 = polygon.get(i + 10);
        y2 = polygon.get(i + 11);

        //Inner vector
        innerEdge = new Vector2(x1 - x2, y1 - y2);

        innerEdge.scl(innerActual / innerEdge.len());

        x3 = polygon.get(i);
        y3 = polygon.get(i + 1);

        x4 = polygon.get(i + 2);
        y4 = polygon.get(i + 3);

        //Outer vector
        outerEdge = new Vector2(x4 - x3, y4 - y3);

        outerEdge.scl(outerActual / outerEdge.len());

        //set new inner end point
        polygon.set(i + 8, x2 + innerEdge.x);
        polygon.set(i + 9, y2 + innerEdge.y);


        //set new outer end point
        polygon.set(i + 2, x3 + outerEdge.x);
        polygon.set(i + 3, y3 + outerEdge.y);
        polygon.set(i + 6, x3 + outerEdge.x);
        polygon.set(i + 7, y3 + outerEdge.y);
    }

    /**
     * adds the specified border segment of a borderPolygon to another polygon
     *
     * @param polygon       segment source borderPolygon
     * @param index         segment index
     * @param targetPolygon polygon for appending the segment
     * @return a vector 2 object containing the added length x for inner and y for outer length
     */
    private static Vector2 addSegment(FloatArray polygon, int index, FloatArray targetPolygon) {
        float x1, y1, x2, y2;
        int i = index * 12;

        //x for inner, y for outer
        Vector2 addedLength = new Vector2(0, 0);

        x1 = polygon.get(i + 8);
        y1 = polygon.get(i + 9);

        x2 = polygon.get(i + 10);
        y2 = polygon.get(i + 11);

        addedLength.x += Vector2.len(x1 - x2, y1 - y2);

        x1 = polygon.get(i);
        y1 = polygon.get(i + 1);

        x2 = polygon.get(i + 2);
        y2 = polygon.get(i + 3);

        addedLength.y += Vector2.len(x2 - x1, y2 - y1);

        for (int j = 0; j < 12; j++) {
            targetPolygon.add(polygon.get(i + j));
        }

        return addedLength;
    }

    private static float calculateInnerLength(FloatArray polygon) {
        float x1, y1, x2, y2, length = 0;
        for (int i = 0; i < polygon.size - 2; i += 12) {
            x1 = polygon.get(i + 8);
            y1 = polygon.get(i + 9);

            x2 = polygon.get(i + 10);
            y2 = polygon.get(i + 11);

            length += Vector2.len(x1 - x2, y1 - y2);
        }

        return length;
    }

    private static float calculateOuterLength(FloatArray polygon) {
        float x1, y1, x2, y2, length = 0;
        for (int i = 0; i < polygon.size - 2; i += 12) {
            x1 = polygon.get(i);
            y1 = polygon.get(i + 1);

            x2 = polygon.get(i + 2);
            y2 = polygon.get(i + 3);

            length += Vector2.len(x2 - x1, y2 - y1);
        }

        return length;
    }
}
