package de.verygame.core.system.component.collision;

import com.badlogic.gdx.math.Vector2;
import de.verygame.core.system.component.RectTransform;
import de.verygame.util.CollisionUtils;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

/**
 * @author Rico Schrage
 */
public class CollideUtils {

    public static final Vec2 ZERO_VECTOR = new Vec2(0, 0);

    private CollideUtils() {
        //utility class
    }

    /**
     * Checks if a rectangle collides with a polygon
     *
     * @param rt1 {@link RectTransform}
     * @param b   {@link BoxCollider}
     * @param rt2 {@link RectTransform}
     * @param p   {@link PolygonCollider}
     * @return true when colliding else false
     */
    public static boolean checkRectanglePolygonCollision(RectTransform rt1, BoxCollider b, RectTransform rt2, PolygonCollider p) {
        return checkRectanglePolygonCollision(rt1, b, rt2, p, 0, 0, 0, 0);
    }

    /**
     * Checks if a rectangle collides with a polygon
     *
     * @param rt1        {@link RectTransform}
     * @param b          {@link BoxCollider}
     * @param rt2        {@link RectTransform}
     * @param p          {@link PolygonCollider}
     * @param xOffsetOne x offset of <code>rt1</code>
     * @param yOffsetOne y offset of <code>rt1</code>
     * @param xOffsetTwo x offset of <code>rt2</code>
     * @param yOffsetTwo y offset of <code>rt2</code>
     * @return true when colliding else false
     */
    public static boolean checkRectanglePolygonCollision(RectTransform rt1, BoxCollider b, RectTransform rt2, PolygonCollider p,
                                                         float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkRectanglePolygonCollision(rt1.getX() + xOffsetOne, rt1.getY() + yOffsetOne, b.getWidth() * rt1.getWidthScale(), b.getHeight() * rt1.getHeightScale(),
                rt2.getX() + xOffsetTwo, rt2.getY() + yOffsetTwo, rt2.getWidthScale(), rt2.getHeightScale(), p.getVertices());
    }

    /**
     * Checks if a Polygon collides with a Circle
     *
     * @param rt1 {@link RectTransform}
     * @param c   {@link CircleCollider}
     * @param rt2 {@link RectTransform}
     * @param p   {@link PolygonCollider}
     * @return true when they collide otherwise false
     */
    public static boolean checkCirclePolygonCollision(RectTransform rt1, CircleCollider c, RectTransform rt2, PolygonCollider p) {
        return checkCirclePolygonCollision(rt1, c, rt2, p, 0, 0, 0, 0);
    }

    /**
     * Checks if a Polygon collides with a Circle
     *
     * @param rt1        {@link RectTransform}
     * @param c          {@link CircleCollider}
     * @param rt2        {@link RectTransform}
     * @param p          {@link PolygonCollider}
     * @param xOffsetOne x offset of rt1
     * @param yOffsetOne y offset of rt1
     * @param xOffsetTwo x offset of rt2
     * @param yOffsetTwo y offset of rt2
     * @return true when they collide otherwise false
     */
    public static boolean checkCirclePolygonCollision(RectTransform rt1, CircleCollider c, RectTransform rt2, PolygonCollider p,
                                                      float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkCirclePolygonCollision(rt1.getX() + xOffsetOne, rt1.getY() + yOffsetOne, c.getRadius() * rt1.getWidthScale(),
                rt2.getX() + xOffsetTwo, rt2.getY() + yOffsetTwo, rt2.getWidthScale(), rt2.getHeightScale(), p.getVertices());
    }

    /**
     * Checks if two polygons collide.
     *
     * @param t1 {@link RectTransform}
     * @param p1 {@link PolygonCollider}
     * @param t2 {@link RectTransform}
     * @param p2 {@link PolygonCollider}
     * @return true when they collide otherwise false.
     */
    public static boolean checkPolygonPolygonCollision(final RectTransform t1, final PolygonCollider p1, final RectTransform t2, final PolygonCollider p2) {
        return checkPolygonPolygonCollision(t1, p1, t2, p2, 0, 0, 0, 0);
    }

    /**
     * Checks if two polygons collide.
     *
     * @param t1         {@link RectTransform}
     * @param p1         {@link PolygonCollider}
     * @param t2         {@link RectTransform}
     * @param p2         {@link PolygonCollider}
     * @param xOffsetOne x offset of t1
     * @param yOffsetOne y offset of t1
     * @param xOffsetTwo x offset of t2
     * @param yOffsetTwo y offset of t2
     * @return true when they collide otherwise false.
     */
    public static boolean checkPolygonPolygonCollision(final RectTransform t1, final PolygonCollider p1, final RectTransform t2, final PolygonCollider p2,
                                                       float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkPolygonPolygonCollision(t1.getX() + xOffsetOne, t1.getY() + yOffsetOne, t1.getWidthScale(), t1.getHeightScale(), p1.getVertices(),
                t2.getX() + xOffsetTwo, t2.getY() + yOffsetTwo, t2.getWidthScale(), t2.getHeightScale(), p2.getVertices());
    }

    public static boolean checkCircleCircleCollision(final RectTransform t1, final CircleCollider c1, final RectTransform t2, final CircleCollider c2) {
        return checkCircleCircleCollision(t1, c1, t2, c2, 0, 0, 0, 0);
    }

    public static boolean checkCircleCircleCollision(final RectTransform t1, final CircleCollider c1, final RectTransform t2, final CircleCollider c2,
                                                     float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkCircleCircleCollision(t1.getX() + xOffsetOne, t1.getY() + yOffsetOne, c1.getRadius() * t1.getWidthScale(),
                t2.getX() + xOffsetTwo, t2.getY() + yOffsetTwo, c2.getRadius() * t2.getWidthScale());
    }

    /**
     * Checks if a BoxCollider collides with a CircleCollider.
     *
     * @param boxTransform    {@link RectTransform}
     * @param box             {@link BoxCollider}
     * @param circleTransform {@link RectTransform}
     * @param circle          {@link CircleCollider}
     * @return true when they collide else false
     */
    public static boolean checkRectangleCircleCollision(final RectTransform boxTransform, final BoxCollider box, final RectTransform circleTransform, final CircleCollider circle) {
        return checkRectangleCircleCollision(boxTransform, box, circleTransform, circle, 0, 0, 0, 0);
    }

    /**
     * Checks if a BoxCollider collides with a CircleCollider.
     *
     * @param boxTransform    {@link RectTransform}
     * @param box             {@link BoxCollider}
     * @param circleTransform {@link RectTransform}
     * @param circle          {@link CircleCollider}
     * @param xOffsetOne      x offset of <code>boxTransform</code>
     * @param yOffsetOne      y offset of <code>boxTransform</code>
     * @param xOffsetTwo      x offset of <code>circleTransform</code>
     * @param yOffsetTwo      y offset of <code>circleTransform</code>
     * @return true when they collide else false
     */
    public static boolean checkRectangleCircleCollision(final RectTransform boxTransform, final BoxCollider box, final RectTransform circleTransform, final CircleCollider circle,
                                                        float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkRectangleCircleCollision(boxTransform.getX() + xOffsetOne, boxTransform.getY() + yOffsetOne, box.getWidth() * boxTransform.getWidthScale(), box.getHeight() * boxTransform.getHeightScale(),
                circleTransform.getX() + xOffsetTwo, circleTransform.getY() + yOffsetTwo, circle.getRadius() * circleTransform.getWidthScale());
    }

    /**
     * Checks if two rectangles collide, coordinates start at the bottom left
     *
     * @param b1 {@link BoxCollider}
     * @param t1 {@link RectTransform}
     * @param b2 {@link BoxCollider}
     * @param t2 {@link RectTransform}
     * @return True when colliding else false.
     */
    public static boolean checkRectangleRectangleCollision(final RectTransform t1, final BoxCollider b1, final RectTransform t2, final BoxCollider b2) {
        return checkRectangleRectangleCollision(t1, b1, t2, b2, 0, 0, 0, 0);
    }

    /**
     * Checks if two rectangles collide, coordinates start at the bottom left
     *
     * @param b1         {@link BoxCollider}
     * @param t1         {@link RectTransform}
     * @param b2         {@link BoxCollider}
     * @param t2         {@link RectTransform}
     * @param xOffsetOne x offset of t1
     * @param yOffsetOne y offset of t1
     * @param xOffsetTwo x offset of t2
     * @param yOffsetTwo x offset of t2
     * @return True when colliding else false.
     */
    public static boolean checkRectangleRectangleCollision(final RectTransform t1, final BoxCollider b1, final RectTransform t2, final BoxCollider b2,
                                                           float xOffsetOne, float yOffsetOne, float xOffsetTwo, float yOffsetTwo) {
        return CollisionUtils.checkRectangleRectangleCollision(t1.getX() + xOffsetOne, t1.getY() + yOffsetOne, b1.getWidth() * t1.getWidthScale(), b1.getHeight() * t1.getHeightScale(),
                t2.getX() + xOffsetTwo, t2.getY() + yOffsetTwo, b2.getWidth() * t2.getWidthScale(), b2.getHeight() * t2.getHeightScale());
    }

    /**
     * Builds a local AABB, lower left bound is always at (0,0);
     *
     * @param vertices of the polygon
     * @return the new AABB
     */
    public static AABB buildLocalAABB(Vector2[] vertices, float xScale, float yScale) {

        Vec2 max = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);

        for (Vector2 vector : vertices) {
            if (vector.x > max.x) {
                max.x = vector.x;
            }
            if (vector.y > max.y) {
                max.y = vector.y;
            }
        }

        max.set(max.x * xScale, max.y * yScale);

        return new AABB(ZERO_VECTOR, max);
    }

    /**
     * Builds an AABB width the given width and height
     *
     * @param width  the width
     * @param height the height
     * @return the new AABB
     */
    public static AABB buildLocalAABB(float width, float height) {

        Vec2 lower = ZERO_VECTOR;
        Vec2 upper = new Vec2(width, height);

        return new AABB(lower, upper);

    }

    /**
     * Builds an AABB for the given polygon
     *
     * @param vertices of the polygon
     * @return the new AABB
     */
    public static AABB buildAABB(Vector2[] vertices, float xScale, float yScale) {

        Vec2 min = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
        Vec2 max = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);

        for (Vector2 vector : vertices) {
            if (vector.x < min.x) {
                min.x = vector.x;
            }
            if (vector.y < min.y) {
                min.y = vector.y;
            }
            if (vector.x > max.x) {
                max.x = vector.x;
            }
            if (vector.y > max.y) {
                max.y = vector.y;
            }
        }

        min.set(min.x * xScale, min.y * yScale);
        max.set(max.x * xScale, max.y * yScale);

        return new AABB(min, max);
    }


    /**
     * Scales a local polygon to fit the given width and height
     *
     * @param polygon the polygon to scale
     * @param width   target width
     * @param height  target height
     * @return a new polygon
     */
    public static Vector2[] scaleToSize(Vector2[] polygon, float width, float height) {

        AABB aabb = buildAABB(polygon, 1, 1);

        float pWidth = aabb.upperBound.x;
        float pHeight = aabb.upperBound.y;

        if (Float.compare(width, pWidth) == 0 && Float.compare(height, pHeight) == 0) {
            return polygon;
        }
        else {
            Vector2[] newPolygon = new Vector2[polygon.length];
            float widthScale = width / pWidth;
            float heightScale = height / pHeight;

            for (int i = 0; i < polygon.length; i++) {
                newPolygon[i] = new Vector2(polygon[i].x * widthScale, polygon[i].y * heightScale);
            }

            return newPolygon;
        }
    }

}
