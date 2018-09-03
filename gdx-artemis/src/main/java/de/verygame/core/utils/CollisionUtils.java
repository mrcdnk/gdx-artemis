package de.verygame.core.utils;

import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.RectTransform;

public class CollisionUtils {


    public static boolean facesTowards(RectTransform r1, Movement m1, RectTransform r2) {

       return de.verygame.util.CollisionUtils.movesTowards(r1.getX(), r1.getY(), r1.getX() + r1.getWidth(true), r1.getY() + r1.getHeight(true), m1.getXVelocity(), m1.getYVelocity(),
               r2.getX(), r2.getY(), r2.getX() + r2.getWidth(true), r2.getY() + r2.getHeight(true));
    }
}
