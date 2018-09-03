package de.verygame.core.resource;

import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceType;

/**
 * @author Rico Schrage
 */
public enum UiResource implements Resource {
    LOADING_SKIN("lskin", "loading.json", ResourceType.SKIN),
    LOADING_XML("loading", "loading.xml", ResourceType.XML),

    UI_SKIN("skin", "ui.json", ResourceType.SKIN),
    UI_ATLAS("ui", "ui.atlas", ResourceType.TEX_ATLAS),
    BACKGROUND_XML("background", "background.xml", ResourceType.XML),
    GAMEOVER_XML("gameover", "gameover.xml", ResourceType.XML),
    MAINMENU_XML("mainmenu", "mainmenu.xml", ResourceType.XML),
    LVL_SELECTION("lvl-selection", "lvl-selection.xml", ResourceType.XML),
    OPTIONS_XML("options", "options.xml", ResourceType.XML);

    private String uiResourceName;
    private String uiResourceFilePath;
    private ResourceType uiResourceType;

    UiResource(String name, String filePath, ResourceType type) {
        this.uiResourceName = name;
        this.uiResourceFilePath = filePath;
        this.uiResourceType = type;
    }

    @Override
    public String getFileName() {
        return uiResourceName;
    }

    @Override
    public String getFilePath() {
        return uiResourceFilePath;
    }

    @Override
    public ResourceType getType() {
        return uiResourceType;
    }
}
