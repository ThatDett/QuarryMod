package quarrymod.quarries;

import necesse.entity.objectEntity.AnyLogFueledProcessingTechInventoryObjectEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.Level;

import static quarrymod.QuarryMod.IVYQUARRY;

public class ProcessingIvyQuarryObjectEntity extends AnyLogFueledProcessingTechInventoryObjectEntity {
    public static int logFuelTime = 30000;

    public static int recipeProcessTime = 12000;

    public ProcessingIvyQuarryObjectEntity(Level level, int x, int y) {
        super(level, "ivyquarry", x, y, 2, 2, false, false, true, new Tech[] {IVYQUARRY});
    }

    public int getFuelTime(InventoryItem item) {
        return logFuelTime;
    }

    public int getProcessTime(Recipe recipe) {
        return recipeProcessTime;
    }
}

