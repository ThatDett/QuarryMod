package quarrymod.quarries;

import necesse.entity.objectEntity.AnyLogFueledProcessingTechInventoryObjectEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.Level;

import static quarrymod.QuarryMod.QUARTZQUARRY;
import static quarrymod.QuarryMod.STONEQUARRY;

public class ProcessingQuartzQuarryObjectEntity extends AnyLogFueledProcessingTechInventoryObjectEntity {
    public static int logFuelTime = 40000;

    public static int recipeProcessTime = 8000;

    public ProcessingQuartzQuarryObjectEntity(Level level, int x, int y) {
        super(level, "quartzquarry", x, y, 2, 2, false, false, true, new Tech[] {QUARTZQUARRY});
    }

    public int getFuelTime(InventoryItem item) {
        return logFuelTime;
    }

    public int getProcessTime(Recipe recipe) {
        return recipeProcessTime;
    }
}

