package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.UI.IGUIBase;
import dk.grp1.tanks.common.data.World;

import java.util.HashSet;

public class UIPart implements IEntityPart {

    private HashSet<IGUIBase> uiElementsList;

    public UIPart() {
        uiElementsList = new HashSet<>();
    }

    public void addUIElement(IGUIBase element){
        uiElementsList.add(element);
    }

    public void removeUIElement(IGUIBase element){
        uiElementsList.remove(element);
    }

    public Boolean containsUI(IGUIBase uiElement){
        return uiElementsList.contains(uiElement);
    }


    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }
}


