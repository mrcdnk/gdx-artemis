package de.verygame.core.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import de.verygame.core.system.component.behaviour.Behaviour;
import de.verygame.core.system.component.behaviour.BehaviourData;
import de.verygame.core.system.component.behaviour.BehaviourListener;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.game.artemis.system.component.behaviour.Behaviour;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourData;
import de.verygame.square.game.artemis.system.component.behaviour.BehaviourListener;

import java.lang.reflect.Field;
import java.util.EnumMap;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 07.04.2016.
 */
public class BehaviourSystem extends IteratingSystem {

    private ComponentMapper<BehaviourData> behaviourDataMapper;

    private EnumMap<Behaviour.BehaviourType, BehaviourListener[]> listenerMap = new EnumMap<>(Behaviour.BehaviourType.class);

    private ResourceHandler resourceHandler;

    public BehaviourSystem(ResourceHandler resourceHandler) {
        super(Aspect.all(BehaviourData.class));
        this.resourceHandler = resourceHandler;
    }

    @Override
    protected void process(int entityId) {
        BehaviourData data = behaviourDataMapper.get(entityId);

        Array<Behaviour> behaviours = data.getBehaviours();

        for (int i = behaviours.size - 1; i >= 0; i--) {

            Behaviour b = behaviours.get(i);

            BehaviourListener[] listeners = listenerMap.get(b.getBehaviourType());

            if (listeners == null) {
                if (b.getCurrentState() == Behaviour.BehaviourState.INITIALIZE) {
                    injectDependencies(b, entityId);
                }

                b.update(world.delta);
                continue;
            }

            switch (b.getCurrentState()) {
                case INITIALIZE:
                    injectDependencies(b, entityId);

                    for (BehaviourListener l : listeners) {
                        l.behaviourStart(b);
                    }

                case PROCESS:
                    for (BehaviourListener l : listeners) {
                        l.behaviourUpdate(b);
                    }
                    break;

                default:
                    break;
            }

            b.update(world.delta);

            if (b.getCurrentState() == Behaviour.BehaviourState.FINISH) {

                for (BehaviourListener l : listeners) {
                    l.behaviourEnd(b);
                }

                behaviours.removeIndex(i);
            }

        }

    }

    public void addListener(Behaviour.BehaviourType event, BehaviourListener... listeners) {
        listenerMap.put(event, listeners);
    }

    public void removeListener(Behaviour.BehaviourType event) {
        listenerMap.remove(event);
    }


    private void injectDependencies(Behaviour b, int entity) {
        Field[] fields = b.getClass().getDeclaredFields();

        final String field = "Field";

        for (final Field f : fields) {

            f.setAccessible(true);
            String fieldName = f.getName();
            if ("artemisWorld".equals(fieldName)) {
                Object value = world;
                try {
                    f.set(b, value);
                } catch (IllegalAccessException e) {
                    Gdx.app.debug(field, e.getMessage(), e);
                }
            } else if ("entity".equals(fieldName)) {
                Object value = entity;
                try {
                    f.set(b, value);
                } catch (IllegalAccessException e) {
                    Gdx.app.debug(field, e.getMessage(), e);
                }
            } else if ("resourceHandler".equals(fieldName)) {
                Object value = resourceHandler;
                try {
                    f.set(b, value);
                } catch (IllegalAccessException e) {
                    Gdx.app.debug(field, e.getMessage(), e);
                }
            }

        }
    }
}
