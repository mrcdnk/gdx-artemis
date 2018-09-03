package de.verygame.core;

import com.artemis.Component;
import com.artemis.World;
import de.verygame.surface.resource.ResourceHandler;

import java.util.HashMap;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 15.02.2017.
 */
public abstract class EntityBuilder {

    protected HashMap<Class<? extends Component>, Component> componentHashMap;
    protected World currentWorld;

    protected static ResourceHandler resourceHandler;
    private int currentEntity;
    private final  Class<? extends Component>[] composition;


    @SafeVarargs
    public EntityBuilder(World world, Class<? extends Component>... composition) {
        this.currentWorld = world;
        this.composition = composition;

        componentHashMap = new HashMap<>(composition.length);
    }

    /**
     * Sets the resourceHandler exposed to the builders
     * @param resourceHandler see {@link ResourceHandler}
     */
    public static void setResourceHandler(ResourceHandler resourceHandler) {
        EntityBuilder.resourceHandler = resourceHandler;
    }

    /**
     * builds the entity inside the injected world
     * @return the new entity id
     */
    public int build() {
        currentEntity = currentWorld.create();

        fetch(currentEntity);
        define();

        componentHashMap.clear();

        return currentEntity;
    }

    /**
     * pre fetches all components of the specified entity
     * @param e target entity
     */
    private void fetch(int e) {
        for (Class<? extends Component> comp : composition) {
            componentHashMap.put(comp, currentWorld.getMapper(comp).create(e));
        }
    }

    protected abstract void define();

    /**
     * Returns the component for the entity, creates new component if it does not exist.
     * @param component the class of the component
     * @param <A> Generic Type of the Component
     * @return The component of the current entity
     */
    protected <A extends Component> A getComponent(Class<A> component) {
        Component c;
        if (componentHashMap.containsKey(component)) {
            c = componentHashMap.get(component);
        }
        else {
            c = currentWorld.getMapper(component).create(currentEntity);
        }

        return (A) c;
    }

    /**
     * Component getter for the world
     * @param component target component class
     * @param entity target entity id
     * @param <A> the component type
     * @return the component
     */
    protected <A extends Component> A getComponent(Class<A> component, int entity){
        return (A) currentWorld.getMapper(component).get(entity);
    }

    /**
     * Checks if entity has component
     * @param component component to check
     * @param entity target entity id
     * @param <A> the component type
     * @return true if it has the component, else false
     */
    protected <A extends Component> boolean hasComponent(Class<A> component, int entity){
        return currentWorld.getMapper(component).has(entity);
    }
}
