package quarrymod.quarries;

import necesse.entity.objectEntity.AnyLogFueledProcessingTechInventoryObjectEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Tech;
import necesse.level.maps.Level;

import static quarrymod.QuarryMod.IRONQUARRY;
import static quarrymod.QuarryMod.MYCELIUMQUARRY;

public class ProcessingMyceliumQuarryObjectEntity extends AnyLogFueledProcessingTechInventoryObjectEntity {
    public static int logFuelTime = 20000;

    public static int recipeProcessTime = 15000;

    public ProcessingMyceliumQuarryObjectEntity(Level level, int x, int y) {
        super(level, "myceliumquarry", x, y, 2, 2, false, false, true, new Tech[] {MYCELIUMQUARRY});
    }

    public int getFuelTime(InventoryItem item) {
        return logFuelTime;
    }

    public int getProcessTime(Recipe recipe) {
        return recipeProcessTime;
    }
}

