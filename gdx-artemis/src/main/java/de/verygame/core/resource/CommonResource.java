package de.verygame.core.resource;

import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceType;

/**
 * @author Rico Schrage
 */
public enum CommonResource implements Resource {
    COMMON_ATLAS("nearest", "nearest.atlas", ResourceType.TEX_ATLAS),
    DEFAULT_FONT("default-font", "Quicksand-Regular.otf", ResourceType.FONT),
    TYPE_ONE_FONT("typeone", "typeone.ttf", ResourceType.FONT),
    LANGUAGE_BUNDLE("lang", "strings", ResourceType.LANG);

    private String resourceName;
    private String path;
    private ResourceType resourceType;

    CommonResource(String name, String filePath, ResourceType type) {
        this.resourceName = name;
        this.path = filePath;
        this.resourceType = type;
    }

    @Override
    public String getFileName() {
        return resourceName;
    }

    @Override
    public String getFilePath() {
        return path;
    }

    @Override
    public ResourceType getType() {
        return resourceType;
    }
}
