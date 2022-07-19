package quarrymod;

import necesse.gfx.gameTexture.GameTexture;
import quarrymod.quarries.*;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.inventory.recipe.Tech;

@ModEntry
public class QuarryMod {
    public static Tech STONEQUARRY;

    public void init() {
        System.out.println("Hello world from my quarry mod!");

        // Register our objects
        ObjectRegistry.registerObject("stonequarry", new ProcessingStoneQuarryObject(), 11, true);
        // Add recipe tech for stone quarry
        STONEQUARRY = RecipeTechRegistry.registerTech("STONEQUARRY");
    }

    public void postInit() {

        // Quarry item recipe, crafted at workstation for 5 spruce logs
        Recipes.registerModRecipe(new Recipe(
                "stonequarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("sprucelog", 5)
                }
        ).showAfter("forge")); // Show recipe after forge recipe
        // Recipe for making stone from the quarry
        Recipes.registerModRecipe(new Recipe(
                "stone",
                2,
                STONEQUARRY,
                new Ingredient[]{}
                //new Ingredient("coin", 1)
        ));

    }

}
