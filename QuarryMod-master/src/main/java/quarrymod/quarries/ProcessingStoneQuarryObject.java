package quarrymod.quarries;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.Inventory;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.inventory.container.object.CraftingStationContainer;
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

public class ProcessingStoneQuarryObject extends GameObject implements SettlementWorkstationObject {
    public GameTexture texture;

    public ProcessingStoneQuarryObject() {
        super(new Rectangle(32, 32));
        this.drawDmg = false;
        this.isLightTransparent = true;
        this.roomProperties.add("metalwork");
        this.lightHue = 50.0F;
        this.lightSat = 0.2F;
    }

    public int getLightLevel(Level level, int x, int y) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, x, y);
        if (stoneQuarryObjectEntity != null && stoneQuarryObjectEntity.isFuelRunning())
            return 100;
        return 0;
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        if (GameRandom.globalRandom.nextInt(10) == 0) {
            ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, x, y);
            if (stoneQuarryObjectEntity != null && stoneQuarryObjectEntity.isFuelRunning()) {
                int startHeight = 16 + GameRandom.globalRandom.nextInt(16);
                level.entityManager
                        .addParticle((x * 32 + GameRandom.globalRandom
                                .getIntBetween(8, 24)), (y * 32 + 32), Particle.GType.COSMETIC)

                        .smokeColor()
                        .heightMoves(startHeight, (startHeight + 20))
                        .lifeTime(1000);
            }
        }
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/forge");
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
        final TextureDrawOptions flame;
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        byte rotation = level.getObjectRotation(tileX, tileY);
        boolean isFueled = false;
        ProcessingStoneQuarryObjectEntity objectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (objectEntity != null)
            isFueled = objectEntity.isFuelRunning();
        int spriteHeight = this.texture.getHeight() - 32;
        final TextureDrawOptions options = this.texture.initDraw().sprite(rotation % 4, 0, 32, spriteHeight).light(light).pos(drawX, drawY - spriteHeight - 32);
        if (isFueled && rotation == 2) {
            int spriteX = (int)(level.getWorldEntity().getWorldTime() % 1200L / 300L);
            flame = this.texture.initDraw().sprite(spriteX, spriteHeight / 32, 32).light(light).pos(drawX, drawY);
        } else {
            flame = null;
        }
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                options.draw();
                if (flame != null)
                    flame.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int spriteHeight = this.texture.getHeight() - 32;
        this.texture.initDraw().sprite(rotation % 4, 0, 32, spriteHeight).alpha(alpha).draw(drawX, drawY - spriteHeight - 32);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new ProcessingStoneQuarryObjectEntity(level, x, y);
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

    public ProcessingStoneQuarryObjectEntity getStoneQuarryObjectEntity(Level level, int tileX, int tileY) {
        ObjectEntity objectEntity = level.entityManager.getObjectEntity(tileX, tileY);
        if (objectEntity instanceof ProcessingStoneQuarryObjectEntity)
            return (ProcessingStoneQuarryObjectEntity)objectEntity;
        return null;
    }

    public Stream<Recipe> streamSettlementRecipes(Level level, int tileX, int tileY) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null)
            return Recipes.streamRecipes(stoneQuarryObjectEntity.techs);
        return Stream.empty();
    }

    public boolean isProcessingInventory(Level level, int tileX, int tileY) {
        return true;
    }

    public boolean canCurrentlyCraft(Level level, int tileX, int tileY, Recipe recipe) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null)
            return ((stoneQuarryObjectEntity.getExpectedResults()).crafts < 10 && (stoneQuarryObjectEntity
                    .isFuelRunning() || stoneQuarryObjectEntity.canUseFuel()));
        return false;
    }

    public int getMaxCraftsAtOnce(Level level, int tileX, int tileY, Recipe recipe) {
        return 5;
    }

    public InventoryRange getProcessingInputRange(Level level, int tileX, int tileY) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null)
            return stoneQuarryObjectEntity.getInputInventoryRange();
        return null;
    }

    public InventoryRange getProcessingOutputRange(Level level, int tileX, int tileY) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null)
            return stoneQuarryObjectEntity.getOutputInventoryRange();
        return null;
    }

    public ArrayList<InventoryItem> getCurrentAndFutureProcessingOutputs(Level level, int tileX, int tileY) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null)
            return (stoneQuarryObjectEntity.getCurrentAndExpectedResults()).items;
        return new ArrayList<>();
    }

    public ItemCategoriesFilter getDefaultFuelFilters(Level level, int tileX, int tileY) {
        return new ItemCategoriesFilter(5, 10, true);
    }

    public InventoryRange getFuelInventoryRange(Level level, int tileX, int tileY) {
        ProcessingStoneQuarryObjectEntity stoneQuarryObjectEntity = getStoneQuarryObjectEntity(level, tileX, tileY);
        if (stoneQuarryObjectEntity != null) {
            Inventory inventory = stoneQuarryObjectEntity.getInventory();
            if (inventory != null && stoneQuarryObjectEntity.fuelSlots > 0)
                return new InventoryRange(inventory, 0, stoneQuarryObjectEntity.fuelSlots - 1);
        }
        return null;
    }
}
