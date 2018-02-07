package de.verygame.square.game.artemis.entities;

import com.artemis.World;
import com.badlogic.gdx.Gdx;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.EntityBuilder;
import de.verygame.square.game.EntityType;
import de.verygame.square.game.resource.GameResourceUnit;
import de.verygame.square.game.artemis.system.component.Movement;
import de.verygame.square.game.artemis.system.component.PlayerData;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.TypeContainer;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.collision.BoxCollider;
import de.verygame.square.game.artemis.system.component.collision.CollideUtils;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.collision.callback.CollisionCallback;
import de.verygame.square.game.artemis.system.component.rendering.BasicTexture;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 19.02.2017.
 */

public class SquareBuilder extends EntityBuilder {

    public SquareBuilder(World world){
        super(world, RectTransform.class, BoxCollider.class, RenderData.class, CollisionData.class, Movement.class, PlayerData.class, BehaviourData.class, WrappingData.class, TypeContainer.class);
    }

    @Override
    protected void define() {

        TypeContainer typeContainer = getComponent(TypeContainer.class);
        typeContainer.setEntityType(EntityType.SQUARE);

        RectTransform rectTransform = getComponent(RectTransform.class);
        rectTransform.setX(Gdx.graphics.getWidth() / 2f);
        rectTransform.setY(Gdx.graphics.getHeight() / 2f);

        rectTransform.setWidth(((Gdx.graphics.getWidth()+Gdx.graphics.getHeight())/(2*Gdx.graphics.getDensity()))*0.13f);
        rectTransform.setHeight(((Gdx.graphics.getWidth()+Gdx.graphics.getHeight())/(2*Gdx.graphics.getDensity()))*0.13f);

        BoxCollider boxCollider = getComponent(BoxCollider.class);
        boxCollider.setWidth(rectTransform.getWidth());
        boxCollider.setHeight(rectTransform.getHeight());

        RenderData renderData = getComponent(RenderData.class);

        renderData.setDrawable(new BasicTexture(resourceHandler.getRegion(GameResourceUnit.SQUARE), rectTransform));
        renderData.setLayerIndex(1);

        CollisionData collisionData = getComponent(CollisionData.class);
        collisionData.setAABB(CollideUtils.buildLocalAABB(boxCollider.getWidth() * rectTransform.getWidthScale(), boxCollider.getHeight() * rectTransform.getHeightScale()));

        collisionData.setCollisionCallback(new CollisionCallback() {
            @Override
            public void collideWith(int eid, World world, EventHandler eventHandler) {
                //currently no collision behaviour effects other entities
            }
        });


        Movement movement = getComponent(Movement.class);

        movement.setXVelocity(65);
    }
}
