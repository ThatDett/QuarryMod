package quarrymod.quarries;

import necesse.entity.objectEntity.AnyLogFueledProcessingTechInventoryObjectEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.Level;
import static quarrymod.QuarryMod.STONEQUARRY;

public class ProcessingStoneQuarryObjectEntity extends AnyLogFueledProcessingTechInventoryObjectEntity {
    public static int logFuelTime = 40000;

    public static int recipeProcessTime = 8000;

    public ProcessingStoneQuarryObjectEntity(Level level, int x, int y) {
        super(level, "stonequarry", x, y, 2, 2, false, false, true, new Tech[] {STONEQUARRY});
    }

    public int getFuelTime(InventoryItem item) {
        return logFuelTime;
    }

    public int getProcessTime(Recipe recipe) {
        return recipeProcessTime;
    }
}

