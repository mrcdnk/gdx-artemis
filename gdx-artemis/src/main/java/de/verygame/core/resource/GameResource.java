package de.verygame.core.resource;

import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceType;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 28.03.2016.
 */
public enum GameResource implements Resource {
    GAME_ATLAS("game","game.atlas", ResourceType.TEX_ATLAS),
    MAIN_HUD("hud", "hud.xml", ResourceType.XML),
    GAME_SKIN("game_skin", "game_skin.json", ResourceType.SKIN),
    GAME_OVER_XML("game_over", "gameover.xml", ResourceType.XML),
    PAUSE_XML("pause", "pause.xml", ResourceType.XML),
    OBSTACLE_XML("shapes", "shapes.xml", ResourceType.XML),
    SEQUENCE_XML("sequences", "spawnSequences.xml", ResourceType.XML);

    private String name;
    private String filePath;
    private ResourceType type;

    GameResource(String name, String filePath, ResourceType type) {
        this.name = name;
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public ResourceType getType() {
        return type;
    }
}
