package de.verygame.core.powerup;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import de.verygame.core.entities.DirectionChangerBuilder;
import de.verygame.core.entities.PowerUpBuilder;
import de.verygame.core.entities.SpeedUpBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Marco on 19.01.2017.
 * <p>
 * Used to define PowerUps with their spawn probability
 *
 * @author Marco Deneke
 */
public enum PowerUpType {

    SPEED_UP(SpeedUpBuilder.class, 1),
    DIRECTION_CHANGER(DirectionChangerBuilder.class, 1);

    public final static float RADIUS = ((Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / (4 * Gdx.graphics.getDensity())) * 0.08f;
    public final static float SPACING = 1.01f; //multiplied onto collision check radius before spawning
    public final static float INTERVAL = 1.5f; //in seconds

    final Class<? extends PowerUpBuilder> powerUpBuilder;
    final int spawnProbability;

    PowerUpType(Class<? extends PowerUpBuilder> powerUpBuilder, int spawnProbability) {
        this.powerUpBuilder = powerUpBuilder;
        this.spawnProbability = spawnProbability;
    }

    public PowerUpBuilder getPowerUpBuilder(World world) {
        try {
            return powerUpBuilder.getConstructor(World.class).newInstance(world);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PowerUpBuilder getRandomPowerUpBuilder(World world, PowerUpType[] blackList) {

        int probabilitySum = 0;

        ArrayList<PowerUpType> actual = new ArrayList<>(values().length);

        for (PowerUpType p : values()) {
            boolean blacklisted = false;
            for (PowerUpType p2 : blackList) {
                if (p.equals(p2)) {
                    blacklisted = true;
                    break;
                }
            }

            if(!blacklisted){
                probabilitySum += p.spawnProbability;
                actual.add(p);
            }
        }

        int rand = MathUtils.random(probabilitySum);
        int counter = 0;

        for(PowerUpType p : actual){

            counter += p.spawnProbability;

            if(rand < counter){
                return p.getPowerUpBuilder(world);
            }
        }

        return actual.get(actual.size()-1).getPowerUpBuilder(world);
    }

}
