package quarrymod.quarries;

import necesse.entity.objectEntity.AnyLogFueledProcessingTechInventoryObjectEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.Level;

import static quarrymod.QuarryMod.TUNGSTENQUARRY;

public class ProcessingTungstenQuarryObjectEntity extends AnyLogFueledProcessingTechInventoryObjectEntity {
    public static int logFuelTime = 40000;

    public static int recipeProcessTime = 8000;

    public ProcessingTungstenQuarryObjectEntity(Level level, int x, int y) {
        super(level, "tungstenquarry", x, y, 2, 2, false, false, true, new Tech[] {TUNGSTENQUARRY});
    }

    public int getFuelTime(InventoryItem item) {
        return logFuelTime;
    }

    public int getProcessTime(Recipe recipe) {
        return recipeProcessTime;
    }
}

