package de.verygame.core.resource;

import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceUnit;
import de.verygame.surface.resource.ResourceUnitType;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 28.03.2016.
 */
public enum GameResourceUnit implements ResourceUnit {
    SQUARE("SquarePhiSeiten", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION),
    POWER_UP_CIRCLE("PowerUpCircle", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION),
    TEMP_POWER_UP_CIRCLE("TempPowerUpCircle", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION),
    SPEEDO("Speedo", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION),
    ARROW("Arrow", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION),
    DIALOG_PANEL("panel", GameResource.GAME_ATLAS, ResourceUnitType.TEXTURE_REGION);

    private String id;
    private transient Resource parent;
    private ResourceUnitType type;

    GameResourceUnit(String id, Resource parent, ResourceUnitType type) {
        this.id = id;
        this.parent = parent;
        this.type = type;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public ResourceUnitType getUnitType() {
        return type;
    }

    @Override
    public Resource getParent() {
        return parent;
    }
}
