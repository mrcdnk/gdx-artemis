package de.verygame.core.resource;


import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceUnit;
import de.verygame.surface.resource.ResourceUnitType;

/**
 * @author Rico Schrage
 */
public enum UiResourceUnit implements ResourceUnit {
    POWER_BLURED("PowerUpCircleBlured", UiResource.UI_ATLAS, ResourceUnitType.TEXTURE_REGION),
    SQUARE_BLURRED("SquarePhiSeitenBlured", UiResource.UI_ATLAS, ResourceUnitType.TEXTURE_REGION),
    OBSTACLE_BLURED("ObstacleBlured", UiResource.UI_ATLAS, ResourceUnitType.TEXTURE_REGION),
    CIRCLE("circle", UiResource.UI_ATLAS, ResourceUnitType.TEXTURE_REGION);

    private String uiResourceId;
    private transient Resource uiResourceParent;
    private ResourceUnitType uiResourceType;

    private UiResourceUnit(String id, Resource parent, ResourceUnitType type) {
        this.uiResourceId = id;
        this.uiResourceParent = parent;
        this.uiResourceType = type;
    }

    @Override
    public String getIdentifier() {
        return uiResourceId;
    }

    @Override
    public ResourceUnitType getUnitType() {
        return uiResourceType;
    }

    @Override
    public Resource getParent() {
        return uiResourceParent;
    }
}