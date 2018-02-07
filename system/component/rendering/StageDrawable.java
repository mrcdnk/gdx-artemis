package de.verygame.square.game.artemis.system.component.rendering;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.verygame.square.game.artemis.system.component.RectTransform;

/**
 * @author Rico Schrage
 */
public class StageDrawable extends Drawable {

    private final Stage stage;

    public StageDrawable(RectTransform rectTransform, Stage stage) {
        super(rectTransform);

        this.stage = stage;
    }

    @Override
    void onDraw(PolygonSpriteBatch batch, float offsetX, float offsetY) {
        stage.getRoot().setPosition(offsetX, offsetY);
        stage.draw();
    }

}
