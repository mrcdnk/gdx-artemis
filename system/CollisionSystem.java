package de.verygame.square.game.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntIntMap;

import org.jbox2d.callbacks.TreeCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.broadphase.DynamicTree;
import org.jbox2d.common.Vec2;

import java.util.HashMap;

import de.verygame.square.core.event.EventHandler;
import de.verygame.square.game.artemis.system.component.RectTransform;
import de.verygame.square.game.artemis.system.component.collision.CircleCollider;
import de.verygame.square.game.artemis.system.component.collision.CollideUtils;
import de.verygame.square.game.artemis.system.component.collision.CollisionData;
import de.verygame.square.game.artemis.system.component.collision.PolygonCollider;
import de.verygame.square.game.artemis.system.component.collision.callback.CollisionCallback;
import de.verygame.square.game.artemis.system.component.collision.BoxCollider;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingData;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 06.01.2016.
 *         <p>
 *         Handles the broadphase and collision events
 */
public class CollisionSystem extends IteratingSystem {

    @Wire
    protected EventHandler eventHandler;

    protected ComponentMapper<CollisionData> collisionDataMapper;
    protected ComponentMapper<BoxCollider> boxColliderMapper;
    protected ComponentMapper<CircleCollider> circleColliderMapper;
    protected ComponentMapper<PolygonCollider> polygonColliderMapper;
    protected ComponentMapper<RectTransform> rectTransformMapper;
    protected ComponentMapper<WrappingData> wrappingDataMapper;

    private EntitySubscription wrappingEntities;

    protected final HashMap<Integer, IntArray> entityToProxyMap = new HashMap<>();
    protected final IntIntMap proxyToCloneIndexMap = new IntIntMap();
    protected final IntIntMap cloneIndexToProxyMap = new IntIntMap();

    protected DynamicTree broadTree = new DynamicTree();

    private int currentEntity = -1;
    private int currentProxy = -1;

    private RectTransform checkRectTransform;
    private CircleCollider checkCircleCollider;

    protected TreeCallback callback = new TreeCallback() {
        @Override
        public boolean treeCallback(int proxyId) {

            int entity = (int) broadTree.getUserData(proxyId);

            if (currentEntity != entity && checkCollision(currentEntity, entity, currentProxy, proxyId)) {

                CollisionCallback cc = collisionDataMapper.get(entity).getCollisionCallback();
                cc.setSelf(entity);
                cc.collideWith(currentEntity, world, eventHandler);

                cc = collisionDataMapper.get(currentEntity).getCollisionCallback();
                cc.setSelf(currentEntity);
                cc.collideWith(entity, world, eventHandler);
                return false;
            }
            return true;
        }
    };

    private boolean collisionCheckResult = false;

    private TreeCallback checkCallback = new TreeCallback() {
        @Override
        public boolean treeCallback(int proxyId) {

            int entity = (int) broadTree.getUserData(proxyId);

            collisionCheckResult = checkCollision(checkRectTransform, checkCircleCollider, entity);

            return false;
        }
    };

    /**
     * Creates a new EntityProcessingSystem.
     */
    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Aspect.all(CollisionData.class).one(BoxCollider.class, PolygonCollider.class, CircleCollider.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initialize() {
        super.initialize();
        wrappingEntities = world.getAspectSubscriptionManager().get(Aspect.all(CollisionData.class, WrappingData.class).one(BoxCollider.class, PolygonCollider.class, CircleCollider.class));
    }

    @Override
    protected void process(int e) {
        currentEntity = e;

        CollisionData collisionData = collisionDataMapper.get(e);
        AABB globalAABB = getGlobalAABB(e);

        if (wrappingDataMapper.has(e)) {
            processWrappingEntities(e, collisionData, globalAABB);
        }

        if (collisionData.isActive()) {
            currentProxy = entityToProxyMap.get(e).first();
            broadTree.query(callback, globalAABB);
        }
    }

    @Override
    protected void inserted(int e) {
        int proxy = broadTree.createProxy(getGlobalAABB(e), e);
        IntArray proxyArray = new IntArray();
        proxyArray.add(proxy);

        entityToProxyMap.put(e, proxyArray);
        proxyToCloneIndexMap.put(proxy, -1);
    }

    @Override
    protected void removed(int e) {
        IntArray proxyArray = entityToProxyMap.get(e);
        for (int i = 0; i < proxyArray.size; ++i) {
            int proxyId = proxyArray.get(i);
            broadTree.destroyProxy(proxyId);
            proxyToCloneIndexMap.remove(proxyId, 0);
        }

        entityToProxyMap.remove(e);
    }

    /**
     * Processes entities with wrapping components.
     *
     * @param e             entity
     * @param collisionData collision data of the entity
     * @param globalAABB    aabb of the currentEntity entity
     */
    private void processWrappingEntities(int e, CollisionData collisionData, AABB globalAABB) {
        WrappingData wrappingData = wrappingDataMapper.get(e);
        FloatArray wrappingArray = wrappingData.getClonePositions();

        if (wrappingData.isDirty()) {
            IntArray proxyArray = entityToProxyMap.get(e);
            for (int i = 1; i < proxyArray.size; ++i) {
                int proxyId = proxyArray.get(i);
                broadTree.destroyProxy(proxyId);
                proxyArray.removeIndex(i);
                proxyToCloneIndexMap.remove(proxyId, 0);
                cloneIndexToProxyMap.remove(proxyId, 0);
            }

            for (int i = 0; i < WrappingData.FIXED_ARRAY_SIZE; i += 2) {
                float offsetX = wrappingArray.get(i);
                float offsetY = wrappingArray.get(i + 1);

                if (Float.isInfinite(offsetX)) {
                    continue;
                }

                AABB cloneAABB = new AABB(globalAABB);
                cloneAABB.lowerBound.addLocal(offsetX, offsetY);
                cloneAABB.upperBound.addLocal(offsetX, offsetY);

                int proxyId = broadTree.createProxy(cloneAABB, e);
                proxyArray.add(proxyId);
                proxyToCloneIndexMap.put(proxyId, i);
                cloneIndexToProxyMap.put(i, proxyId);
            }

            wrappingData.setDirty(false);
        }

        if (collisionData.isActive()) {
            for (int i = 0; i < WrappingData.FIXED_ARRAY_SIZE; i += 2) {
                float offsetX = wrappingArray.get(i);
                float offsetY = wrappingArray.get(i + 1);

                if (Float.isInfinite(offsetX)) {
                    continue;
                }

                AABB cloneAABB = new AABB(globalAABB);
                cloneAABB.lowerBound.addLocal(offsetX, offsetY);
                cloneAABB.upperBound.addLocal(offsetX, offsetY);

                currentProxy = cloneIndexToProxyMap.get(i, 0);
                broadTree.query(callback, cloneAABB);
            }
        }
    }

    /**
     * Returns if the given position contains another collision entity within a circle
     * @param x bottom left x coordinate
     * @param y bottom left y coordinate
     * @param radius the radius of the circle
     * @return true if the space is already occupied
     */
    public boolean testCollision(float x, float y, float radius){
        //remove dependency on world and entity id by using Integer.Max as identifier

        checkRectTransform = new RectTransform();

        checkRectTransform.setX(x);
        checkRectTransform.setY(y);
        checkRectTransform.setWidth(radius*2);
        checkRectTransform.setHeight(radius*2);

        checkCircleCollider = new CircleCollider();
        checkCircleCollider.setRadius(radius);
        AABB aabb = CollideUtils.buildLocalAABB(checkRectTransform.getWidth() * checkRectTransform.getWidthScale(), checkRectTransform.getHeight() * checkRectTransform.getHeightScale());

        aabb.lowerBound.addLocal(x, y);
        aabb.upperBound.addLocal(x, y);

        broadTree.query(checkCallback, aabb);

        /*if(!collisionCheckResult) {

            IntBag entities = new IntBag();
            wrappingEntities.getActiveEntityIds().toIntBag(entities);

            collisionCheckResult = false;

            for (int i = 0; i < entities.size(); i++) {
                if (collisionCheckResult) {
                    break;
                }
                collisionCheckResult = collisionCheckResult || checkCollision(checkRectTransform, checkCircleCollider, entities.get(i));
            }
        }*/

        return collisionCheckResult;
    }

    /**
     * Moves the AABB inside the DynamicTree
     *
     * @param e  Entity
     * @param dx x offset
     * @param dy y offset
     */
    public void move(int e, float dx, float dy) {
        Vec2 displacement = new Vec2(dx, dy);
        AABB aabb = getGlobalAABB(e);

        AABB clone = new AABB(aabb);

        broadTree.moveProxy(entityToProxyMap.get(e).first(), clone, displacement);

        if (wrappingDataMapper.has(e)) {
            WrappingData data = wrappingDataMapper.get(e);
            FloatArray offsets = data.getClonePositions();

            IntArray proxyArray = entityToProxyMap.get(e);

            for (int i = 1; i < proxyArray.size; ++i) {
                clone = new AABB(aabb);
                clone.lowerBound.addLocal(offsets.get(proxyToCloneIndexMap.get(proxyArray.get(i), 0)), offsets.get(proxyToCloneIndexMap.get(proxyArray.get(i), 0) + 1));
                clone.upperBound.addLocal(offsets.get(proxyToCloneIndexMap.get(proxyArray.get(i), 0)), offsets.get(proxyToCloneIndexMap.get(proxyArray.get(i), 0) + 1));
                broadTree.moveProxy(proxyArray.get(i), clone, displacement);
            }
        }
    }

    /**
     * Returns the global AABB {@link AABB} of an Entity.
     *
     * @param e Entity
     * @return global AABB
     */
    protected AABB getGlobalAABB(int e) {

        RectTransform transform = rectTransformMapper.get(e);
        CollisionData collisionData = collisionDataMapper.get(e);

        float x = transform.getX();
        float y = transform.getY();

        AABB aabb = new AABB(collisionData.getAABB());

        aabb.lowerBound.addLocal(x, y);
        aabb.upperBound.addLocal(x, y);

        return aabb;
    }

    private boolean checkCollision(RectTransform rectTransform, CircleCollider circleCollider, int otherEntity){

        RectTransform rt = rectTransformMapper.get(otherEntity);
        BoxCollider bc = boxColliderMapper.get(otherEntity);
        CircleCollider cc = circleColliderMapper.get(otherEntity);
        PolygonCollider pc = polygonColliderMapper.get(otherEntity);

        if (bc != null) {
            return CollideUtils.checkRectangleCircleCollision(rt, bc, rectTransform, circleCollider);
        }
        else if (cc != null) {
            return CollideUtils.checkCircleCircleCollision(rt, cc, rectTransform, circleCollider);
        }
        else {
            return pc != null && CollideUtils.checkCirclePolygonCollision(rectTransform, circleCollider, rt, pc);
        }

    }

    /**
     * Checks the collision between the two given entities.
     *
     * @param current    first entity
     * @param other      second entity
     * @param otherProxy proxy which collided
     * @return true if the entities collide, false otherwise
     */
    protected boolean checkCollision(int current, int other, int currentProxy, int otherProxy) {

        float xOtherOffset = 0;
        float yOtherOffset = 0;
        float xCurrentOffset = 0;
        float yCurrentOffset = 0;

        if (wrappingDataMapper.has(other)) {
            WrappingData data = wrappingDataMapper.get(other);
            FloatArray cloneArray = data.getClonePositions();
            int startIndex = proxyToCloneIndexMap.get(otherProxy, 0);
            if (startIndex != -1) {
                xOtherOffset = cloneArray.get(startIndex);
                yOtherOffset = cloneArray.get(startIndex + 1);
            }
        }
        if (wrappingDataMapper.has(current)) {
            WrappingData data = wrappingDataMapper.get(current);
            FloatArray cloneArray = data.getClonePositions();
            int startIndex = proxyToCloneIndexMap.get(currentProxy, 0);
            if (startIndex != -1) {
                xCurrentOffset = cloneArray.get(startIndex);
                yCurrentOffset = cloneArray.get(startIndex + 1);
            }
        }

        CollisionData cd1 = collisionDataMapper.get(current);
        CollisionData cd2 = collisionDataMapper.get(other);

        if (cd1 != null && !cd1.isActive()) {
            return false;
        }
        else if (cd2 != null && !cd2.isActive()) {
            return false;
        }

        RectTransform rt1 = rectTransformMapper.get(current);
        RectTransform rt2 = rectTransformMapper.get(other);

        BoxCollider b1 = boxColliderMapper.get(current);
        BoxCollider b2 = boxColliderMapper.get(other);

        CircleCollider c1 = circleColliderMapper.get(current);
        CircleCollider c2 = circleColliderMapper.get(other);

        PolygonCollider p1 = polygonColliderMapper.get(current);
        PolygonCollider p2 = polygonColliderMapper.get(other);

        if (b1 != null) {
            if (b2 != null) {
                return CollideUtils.checkRectangleRectangleCollision(rt1, b1, rt2, b2, xCurrentOffset, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
            else if (c2 != null) {
                return CollideUtils.checkRectangleCircleCollision(rt1, b1, rt2, c2, 0, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
            else {
                return CollideUtils.checkRectanglePolygonCollision(rt1, b1, rt2, p2, 0, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
        }
        else if (c1 != null) {
            if (b2 != null) {
                return CollideUtils.checkRectangleCircleCollision(rt2, b2, rt1, c1, xOtherOffset, yOtherOffset, xCurrentOffset, yCurrentOffset);
            }
            else if (c2 != null) {
                return CollideUtils.checkCircleCircleCollision(rt1, c1, rt2, c2, xCurrentOffset, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
            else {
                return CollideUtils.checkCirclePolygonCollision(rt1, c1, rt2, p2, xCurrentOffset, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
        }
        else {
            if (b2 != null) {
                return CollideUtils.checkRectanglePolygonCollision(rt2, b2, rt1, p1, xOtherOffset, yOtherOffset, xCurrentOffset, yCurrentOffset);
            }
            else if (c2 != null) {
                return CollideUtils.checkCirclePolygonCollision(rt2, c2, rt1, p1, xOtherOffset, yOtherOffset, xCurrentOffset, yCurrentOffset);
            }
            else {
                return CollideUtils.checkPolygonPolygonCollision(rt1, p1, rt2, p2, xCurrentOffset, yCurrentOffset, xOtherOffset, yOtherOffset);
            }
        }
    }
}