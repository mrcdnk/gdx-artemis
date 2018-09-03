package de.verygame.core.entities;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.verygame.core.EntityBuilder;
import de.verygame.core.EntityType;
import de.verygame.core.resource.CommonResourceUnit;
import de.verygame.core.resource.GameResourceUnit;
import de.verygame.core.system.component.Movement;
import de.verygame.core.system.component.RectTransform;
import de.verygame.core.system.component.TypeContainer;
import de.verygame.core.system.component.behaviour.BehaviourData;
import de.verygame.core.system.component.behaviour.FadeSpawn;
import de.verygame.core.system.component.behaviour.SelfDestructBehaviour;
import de.verygame.core.utils.CollisionAdapter;
import de.verygame.core.system.component.collision.CollisionData;
import de.verygame.core.system.component.collision.PolygonCollider;
import de.verygame.core.system.component.collision.callback.ObstacleCallback;
import de.verygame.core.system.component.rendering.BasicTexture;
import de.verygame.core.system.component.rendering.PolygonTexture;
import de.verygame.core.system.component.rendering.RGBADrawable;
import de.verygame.core.system.component.rendering.RenderData;
import de.verygame.core.system.component.wrapping.WrappingData;
import de.verygame.square.game.EntityType;
import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.TypeContainer;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.behaviour.FadeSpawn;
import de.verygame.square.game.artemis.system.component.behaviour.SelfDestructBehaviour;
import de.verygame.square.game.artemis.system.component.collision.CollideUtils;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.collision.PolygonCollider;
import de.verygame.square.game.artemis.system.component.collision.callback.ObstacleCallback;
import de.verygame.square.game.artemis.system.component.rendering.BasicTexture;
import de.verygame.square.game.artemis.system.component.rendering.PolygonTexture;
import de.verygame.square.game.artemis.system.component.rendering.RGBADrawable;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingData;
import de.verygame.square.game.resource.GameResourceUnit;
import de.verygame.square.game.spawnsequence.obstacle.ObstacleDescriptor;
import de.verygame.square.resource.CommonResourceUnit;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 05.03.2017.
 */

public class ObstacleBuilder extends EntityBuilder {


    private boolean isWideScreen;

    private ObstacleDescriptor obstacleDescriptor;

    private int player = -1;
    private final float fadeTime;
    private float fadeTimeOffset = 0;

    public ObstacleBuilder(World world, int player, float fadeTime) {
        super(world, TypeContainer.class, RectTransform.class, RenderData.class, PolygonCollider.class, CollisionData.class, BehaviourData.class);
        this.player = player;
        this.fadeTime = fadeTime;
    }

    public void setObstacleDescriptor(ObstacleDescriptor obstacleDescriptor) {
        this.obstacleDescriptor = obstacleDescriptor;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public float getFadeTime() {
        return fadeTime;
    }

    public void offsetFadeTime(float timeOffset) {
        fadeTimeOffset = timeOffset;
    }

    @Override
    protected void define() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float x1;
        float y1;
        float x2;
        float y2;

        //this has to cover the complete Texture !
        Vector2[] vertices = obstacleDescriptor.getVertices();
        short[] triangles = new short[vertices.length];

        for (short i = 0; i < triangles.length; i++) {
            triangles[i] = i;
        }

        x1 = screenWidth * obstacleDescriptor.getBottomLeft().x;
        y1 = screenHeight * obstacleDescriptor.getBottomLeft().y;

        x2 = screenWidth * obstacleDescriptor.getTopRight().x;
        y2 = screenHeight * obstacleDescriptor.getTopRight().y;

        //Rotation for widescreen on hold for now
        /*else {
            x1 = screenWidth * obstacleDescriptor.getBottomLeft().y;
            y1 = screenHeight * (1-obstacleDescriptor.getBottomLeft().x);

            x2 = screenWidth * obstacleDescriptor.getTopRight().y;
            y2 = screenHeight * (1-obstacleDescriptor.getTopRight().x);

            //Only rotate triangles
            if (vertices.length < 3) {
                Vector2[] temp = vertices.clone();
                float smallestY = 0;

                for (int i = 0; i < vertices.length; i++) {
                    //rotate by 90° for horizontal screens
                    //noinspection SuspiciousNameCombination
                    temp[i].set(vertices[i].y, -vertices[i].x);
                    if (temp[i].y < smallestY) {
                        smallestY = temp[i].y;
                    }
                }

                for (int i = 0; i < vertices.length; i++) {
                    //Offset triangle back to default coordinates
                    temp[i].add(0, smallestY);
                }

                vertices = temp;
            }
        }*/

        float obstacleWidth = x2 - x1;
        float obstacleHeight = y2 - y1;

        TypeContainer typeContainer = getComponent(TypeContainer.class);

        typeContainer.setEntityType(EntityType.OBSTACLE);

        RectTransform rectTransform = getComponent(RectTransform.class);

        rectTransform.setX(x1);
        rectTransform.setY(y1);
        rectTransform.setWidth(obstacleWidth, false);
        rectTransform.setHeight(obstacleHeight, false);

        RGBADrawable drawable = new PolygonTexture(resourceHandler.getRegion(CommonResourceUnit.FLAT_BACKGROUND), rectTransform, vertices, triangles);

        RenderData renderData = getComponent(RenderData.class);

        renderData.setDrawable(drawable);
        renderData.setLayerIndex(0);

        Vector2[] scaled = CollisionAdapter.scaleToSize(vertices, rectTransform.getWidth(), rectTransform.getHeight());

        PolygonCollider polygonCollider = getComponent(PolygonCollider.class);

        polygonCollider.setVertices(scaled);

        CollisionData collisionData = getComponent(CollisionData.class);

        collisionData.setAABB(CollisionAdapter.buildAABB(scaled, rectTransform.getWidthScale(), rectTransform.getHeightScale()));
        collisionData.setCollisionCallback(new ObstacleCallback());

        BehaviourData behaviourData = getComponent(BehaviourData.class);

        behaviourData.addBehaviour(new FadeSpawn(fadeTime + fadeTimeOffset, vertices));

        if(obstacleDescriptor.getTimeToLive() > 0){
            behaviourData.addBehaviour(new SelfDestructBehaviour((obstacleDescriptor.getTimeToLive()/1000f)+fadeTime+fadeTimeOffset));
        }

        if (obstacleDescriptor.getWraps()) {
            getComponent(WrappingData.class);
        }

        Vector2 vec2 = obstacleDescriptor.getMovement();

        if (!vec2.isZero()) {
            Movement movement = getComponent(Movement.class);

            //if (isWideScreen) {
            //    vec2.rotate(-90f);
            //}

            drawable.setCenteredDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.ARROW), rectTransform), 0.5f, true).setCenteredRotation(vec2.angle()-90f);

            if (obstacleDescriptor.isRelativeMovement()) {

                Movement playerMovement = getComponent(Movement.class, player);

                //assumes no diagonal movement by player
                float playerVel = Math.abs(playerMovement.getXVelocity() + playerMovement.getYVelocity());

                movement.setXVelocity(vec2.x * playerVel, false);
                movement.setYVelocity(vec2.y * playerVel, false);

            }
            else {
                movement.setXVelocity(vec2.x);
                movement.setYVelocity(vec2.y);
            }
        }

    }
}
