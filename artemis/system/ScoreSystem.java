package de.verygame.square.game.artemis.system;

import com.artemis.BaseSystem;

/**
 * @author Rico Schrage
 */
public class ScoreSystem extends BaseSystem {

    private final Hud hud;

    public ScoreSystem(Hud hud) {
        this.hud = hud;
    }

    @Override
    protected void processSystem() {
        hud.incrementScore(world.delta);
    }
}
