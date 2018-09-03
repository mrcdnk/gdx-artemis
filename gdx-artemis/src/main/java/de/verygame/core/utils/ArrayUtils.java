package de.verygame.core.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayUtils {

    private ArrayUtils(){
        //static only
    }

    public static Vector2[] removeDuplicates(Vector2[] vertices) {
        Deque<Vector2> stack = new ArrayDeque<>();

        for (Vector2 v : vertices) {
            if (!stack.contains(v)) {
                stack.push(v);
            }
        }

        Vector2[] reducedVertices = new Vector2[stack.size()];
        return stack.toArray(reducedVertices);
    }

    /**
     * Removes duplicate vertices from a sorted FloatArray
     */
    public static void removeDuplicates(FloatArray vertices) {
        for (int i = 0; i < vertices.size - 2; i += 2) {
            if (MathUtils.isEqual(vertices.get(i), vertices.get(i + 2)) && MathUtils.isEqual(vertices.get(i + 1), vertices.get(i + 3))) {
                vertices.removeRange(i + 2, i + 3);
            }
        }
    }
}
