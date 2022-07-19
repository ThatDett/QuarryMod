package quarrymod.quarries;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.Inventory;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.inventory.container.object.CraftingStationContainer;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.itemFilter.ItemCategoriesFilter;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.ObjectHoverHitbox;
import necesse.level.maps.Level;
import necesse.level.maps.levelData.settlementData.SettlementWorkstationObject;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProcessingIvyQuarryObject extends GameObject implements SettlementWorkstationObject {
    public GameTexture texture;

    public ProcessingIvyQuarryObject() {
        super(new Rectangle(32, 32));
        toolType = ToolType.PICKAXE;
        this.drawDmg = false;
        this.isLightTransparent = false;
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/ivyquarry");
    }

    public Rectangle getCollision(Level level, int x, int y, int rotation) {
        if (rotation % 2 == 0)
            return new Rectangle(x * 32 + 2, y * 32 + 6, 28, 20);
        return new Rectangle(x * 32 + 6, y * 32 + 2, 20, 28);
    }

    public List<ObjectHoverHitbox> getHoverHitboxes(Level level, int tileX, int tileY) {
        List<ObjectHoverHitbox> list = super.getHoverHitboxes(level, tileX, tileY);
        list.add(new ObjectHoverHitbox(tileX, tileY, 0, -16, 32, 16));
        return list;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        TextureDrawOptions options = texture.initDraw().light(light).pos(drawX, drawY - texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            @Override
            public int getSortY() {
                return 16;
            }

            @Override
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        texture.initDraw().alpha(alpha).draw(drawX, drawY - texture.getHeight() + 32);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new ProcessingIvyQuarryObjectEntity(level, x, y);
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "opentip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }

    public void interact(Level level, int x, int y, PlayerMob player) {
        if (level.isServerLevel())
            CraftingStationContainer.openAndSendContainer(ContainerRegistry.FUELED_PROCESSING_STATION_CONTAINER, player.getServerClient(), level, x, y);
    }

    public ProcessingIvyQuarryObjectEntity getIvyQuarryObjectEntity(Level level, int tileX, int tileY) {
        ObjectEntity objectEntity = level.entityManager.getObjectEntity(tileX, tileY);
        if (objectEntity instanceof ProcessingIvyQuarryObjectEntity)
            return (ProcessingIvyQuarryObjectEntity)objectEntity;
        return null;
    }

    public Stream<Recipe> streamSettlementRecipes(Level level, int tileX, int tileY) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null)
            return Recipes.streamRecipes(ivyQuarryObjectEntity.techs);
        return Stream.empty();
    }

    public boolean isProcessingInventory(Level level, int tileX, int tileY) {
        return true;
    }

    public boolean canCurrentlyCraft(Level level, int tileX, int tileY, Recipe recipe) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null)
            return ((ivyQuarryObjectEntity.getExpectedResults()).crafts < 10 && (ivyQuarryObjectEntity
                    .isFuelRunning() || ivyQuarryObjectEntity.canUseFuel()));
        return false;
    }

    public int getMaxCraftsAtOnce(Level level, int tileX, int tileY, Recipe recipe) {
        return 5;
    }

    public InventoryRange getProcessingInputRange(Level level, int tileX, int tileY) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null)
            return ivyQuarryObjectEntity.getInputInventoryRange();
        return null;
    }

    public InventoryRange getProcessingOutputRange(Level level, int tileX, int tileY) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null)
            return ivyQuarryObjectEntity.getOutputInventoryRange();
        return null;
    }

    public ArrayList<InventoryItem> getCurrentAndFutureProcessingOutputs(Level level, int tileX, int tileY) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null)
            return (ivyQuarryObjectEntity.getCurrentAndExpectedResults()).items;
        return new ArrayList<>();
    }

    public ItemCategoriesFilter getDefaultFuelFilters(Level level, int tileX, int tileY) {
        return new ItemCategoriesFilter(5, 10, true);
    }

    public InventoryRange getFuelInventoryRange(Level level, int tileX, int tileY) {
        ProcessingIvyQuarryObjectEntity ivyQuarryObjectEntity = getIvyQuarryObjectEntity(level, tileX, tileY);
        if (ivyQuarryObjectEntity != null) {
            Inventory inventory = ivyQuarryObjectEntity.getInventory();
            if (inventory != null && ivyQuarryObjectEntity.fuelSlots > 0)
                return new InventoryRange(inventory, 0, ivyQuarryObjectEntity.fuelSlots - 1);
        }
        return null;
    }
}
