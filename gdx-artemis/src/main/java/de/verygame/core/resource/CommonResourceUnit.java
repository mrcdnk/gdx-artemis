package de.verygame.core.resource;

import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceUnit;
import de.verygame.surface.resource.ResourceUnitType;

/**
 * @author Rico Schrage
 */
public enum CommonResourceUnit implements ResourceUnit {
    FLAT_BACKGROUND("white", UiResource.UI_ATLAS, ResourceUnitType.TEXTURE_REGION),
    DEFAULT_BITMAP("default", CommonResource.DEFAULT_FONT, ResourceUnitType.BITMAP_FONT),
    HUD_BITMAP("hudFont", CommonResource.DEFAULT_FONT, ResourceUnitType.BITMAP_FONT);

    private String commonResourceId;
    private transient Resource commonResourceParent;
    private ResourceUnitType commonResourceType;

    CommonResourceUnit(String id, Resource parent, ResourceUnitType type) {
        this.commonResourceId = id;
        this.commonResourceParent = parent;
        this.commonResourceType = type;
    }

    @Override
    public String getIdentifier() {
        return commonResourceId;
    }

    @Override
    public ResourceUnitType getUnitType() {
        return commonResourceType;
    }

    @Override
    public Resource getParent() {
        return commonResourceParent;
    }

}
