package de.verygame.core.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import de.verygame.core.entities.CleanerBuilder;
import de.verygame.core.entities.PowerUpBuilder;
import de.verygame.core.powerup.PowerUpType;
import de.verygame.core.system.component.PlayerData;
import de.verygame.square.game.Level;
import de.verygame.square.game.artemis.entities.CleanerBuilder;
import de.verygame.square.game.artemis.entities.PowerUpBuilder;
import de.verygame.square.game.artemis.system.component.PlayerData;
import de.verygame.square.game.spawnsequence.SpawnSequence;
import de.verygame.square.game.spawnsequence.powerup.PowerUpType;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 16.10.2016.
 */

public class SpawnSystem extends BaseEntitySystem {

    private static final float CLEAR_TIME = 3.5f; //seconds
    private static final float CLEAR_DELAY = 2f; //delay before clear entity starts spawning
    public static final float FADE_TIME = 3f;
    private static final int POWER_UP_SPAWN_ATTEMPTS = 15;

    private State obstacleSpawnState = State.INIT;
    private State levelState = State.INIT;

    private float clearDelay = CLEAR_DELAY;
    private float clearProgress = -FADE_TIME;
    private float powerUpTimer = PowerUpType.INTERVAL;

    private CleanerBuilder cleanerBuilder;

    private final CollisionSystem collisionSystem;
    private PowerUpType[] blacklist = new PowerUpType[0];

    private Level currentLevel;
    private SpawnSequence currentSequence;

    private int player = -1;
    private int clearEntity;

    public SpawnSystem(CollisionSystem collisionSystem) {
        super(Aspect.all(PlayerData.class));

        this.collisionSystem = collisionSystem;
    }

    public void setLevel(Level level) {
        this.currentLevel = level;
    }

    @Override
    protected void initialize() {
        super.initialize();

        cleanerBuilder = new CleanerBuilder(world, CLEAR_TIME, FADE_TIME);
    }

    @Override
    protected void inserted(int e) {
        player = e;
    }

    @Override
    protected void removed(int e) {
        player = -1;
    }

    @Override
    protected void processSystem() {
        float delta = Gdx.graphics.getRawDeltaTime();

        processLevel(delta);
        if (obstacleSpawnState != State.TRANS && levelState != State.END) {
            processPowerUps(delta);
        }

    }

    private void processLevel(float delta) {
        if (levelState == State.INIT) {
            currentSequence = currentLevel.next();
            levelState = State.LEVEL;
        }

        if (levelState == State.LEVEL && processCurrentSequence(delta) == State.INIT) {
            if (currentLevel.currentProgress() < 1f) {
                currentSequence = currentLevel.next();
            } else {
                levelState = State.END;
            }
        }

        if (levelState == State.END) {
            currentLevel.reset();
            //Level ended call UI

        }
    }

    private State processCurrentSequence(float delta) {
        if (obstacleSpawnState == State.INIT) {
            blacklist = currentSequence.getPowerUpBlacklist();

            currentSequence.reset();
            currentSequence.setWorld(this.world);
            currentSequence.initializeObstacleBuilder(player, FADE_TIME);
            obstacleSpawnState = State.SEQUENCE;
        }

        if (obstacleSpawnState == State.SEQUENCE) {
            float progress = currentSequence.step(delta);

            if (progress >= 1f) {
                this.obstacleSpawnState = State.TRANS;
            }

        }

        if (obstacleSpawnState == State.TRANS) {
            float cProgress = clearStage(delta);

            if (cProgress >= 1f) {
                this.obstacleSpawnState = State.INIT;
                world.delete(clearEntity);
                clearProgress = -FADE_TIME;
                clearDelay = CLEAR_DELAY;
            }
        }

        return obstacleSpawnState;
    }


    private void processPowerUps(float delta) {

        powerUpTimer -= delta;

        int attempt = 0;
        float x;
        float y;
        if (powerUpTimer <= 0) {
            while (attempt < POWER_UP_SPAWN_ATTEMPTS) {
                x = MathUtils.random(0, Gdx.graphics.getWidth() - 2 * (PowerUpType.RADIUS * PowerUpType.SPACING));
                y = MathUtils.random(0, Gdx.graphics.getHeight() - 2 * (PowerUpType.RADIUS * PowerUpType.SPACING));

                if (!collisionSystem.testCollision(x, y, PowerUpType.RADIUS * PowerUpType.SPACING)) {

                    PowerUpBuilder builder = PowerUpType.getRandomPowerUpBuilder(world, blacklist);

                    builder.setPosition(x, y);

                    builder.build();

                    break;
                }

                attempt++;
            }
            powerUpTimer = PowerUpType.INTERVAL; //possibly add random variation
        }

    }

    private float clearStage(float delta) {

        if (clearDelay >= 0f) {
            clearDelay -= delta;
            return 0f;
        }

        if (clearProgress == -FADE_TIME) {
            cleanerBuilder.setDirection(currentSequence.getCleanerDirection());
            clearEntity = cleanerBuilder.build();
        }

        clearProgress += delta;

        return clearProgress / CLEAR_TIME;
    }


    public void reset() {
        powerUpTimer = PowerUpType.INTERVAL;
        currentLevel.reset();
        levelState = State.INIT;
        obstacleSpawnState = State.INIT;
        clearProgress = -FADE_TIME;
    }

    private enum State {
        UNDEFINED, INIT, TRANS, SEQUENCE, END, LEVEL
    }
}
