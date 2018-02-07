package de.verygame.square.game.artemis.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

import de.verygame.square.game.artemis.system.component.rendering.RGBADrawable;
import de.verygame.square.game.artemis.system.component.rendering.RenderData;
import de.verygame.square.game.artemis.system.component.wrapping.WrappingData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.03.2016.
 */
public class RenderSystem extends BaseEntitySystem {

    ComponentMapper<RenderData> renderDataMapper;
    ComponentMapper<WrappingData> wrappingDataMapper;

    private static final int ARRAY_GROWTH = 20;

    private IntArray layeredEntities;
    private IntArray dirtyEntities;

    private int dirtyCount = 0;

    private PolygonSpriteBatch batch;

    /**
     * Creates a new EntityProcessingSystem.
     */
    public RenderSystem(PolygonSpriteBatch polygonSpriteBatch, int size) {
        super(Aspect.all(RenderData.class));

        this.layeredEntities = new IntArray(size);
        this.batch = polygonSpriteBatch;
        this.dirtyEntities = new IntArray(size / 2);
    }

    @Override
    protected void initialize() {
        this.setEnabled(false);
    }

    @Override
    public void processSystem() {

        RenderData data;
        Array<RGBADrawable> behaviourDrawables;

        for (int i = 0; i < layeredEntities.size; i++) {

            data = renderDataMapper.get(layeredEntities.get(i));
            behaviourDrawables = data.getBehaviourDrawables();

            data.getDrawable().draw(batch);

            for (RGBADrawable drawable : behaviourDrawables) {
                drawable.draw(batch);
            }

            //draw clones for wrapping if available
            if (wrappingDataMapper.has(layeredEntities.get(i))) {
                WrappingData wrappingData = wrappingDataMapper.get(layeredEntities.get(i));
                FloatArray array = wrappingData.getClonePositions();
                for (int j = 0; j < WrappingData.FIXED_ARRAY_SIZE; j += 2) {
                    float xOffset = array.get(j);
                    float yOffset = array.get(j + 1);

                    if (Float.isInfinite(xOffset) || Float.isInfinite(yOffset)) {
                        continue;
                    }

                    data.getDrawable().draw(batch, xOffset, yOffset);
                    for (RGBADrawable drawable : behaviourDrawables) {
                        drawable.draw(batch, xOffset, yOffset);
                    }
                }
            }

            if (data.isDirty()) {
                queueReinsert(layeredEntities.removeIndex(i));
            }
        }
        reinsert();
    }

    @Override
    protected void inserted(int e) {
        if (layeredEntities.size == layeredEntities.items.length - 1) {
            layeredEntities.ensureCapacity(layeredEntities.size + ARRAY_GROWTH);
        }

        RenderData data = renderDataMapper.get(e);

        for (int i = 0; i < layeredEntities.size + 1; i++) {
            if (i == layeredEntities.size) {
                layeredEntities.add(e);
                break;
            }

            RenderData temp = renderDataMapper.get(layeredEntities.get(i));

            if (temp.getLayerIndex() > data.getLayerIndex()) {
                layeredEntities.insert(i, e);
                return;
            }
        }
    }

    @Override
    protected void removed(int e) {
        layeredEntities.removeValue(e);
    }

    private void reinsert() {
        for (int i = 0; i < dirtyCount; i++) {
            inserted(dirtyEntities.get(i));
        }
        dirtyCount = 0;
    }

    private void queueReinsert(int e) {
        if (dirtyCount == dirtyEntities.size - 1) {
            dirtyEntities.ensureCapacity(layeredEntities.size + ARRAY_GROWTH);
        }
        dirtyEntities.set(dirtyCount, e);
        dirtyCount++;
    }

    public PolygonSpriteBatch getBatch() {
        return this.batch;
    }

}
