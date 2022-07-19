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
    public static Tech COPPERQUARRY;
    public static Tech IRONQUARRY;
    public static Tech GOLDQUARRY;
    public static Tech IVYQUARRY;

    public static Tech QUARTZQUARRY;
    public static Tech ANCIENTFOSSILQUARRY;
    public static Tech TUNGSTENQUARRY;
    public static Tech GLACIALQUARRY;

    public void init() {
        System.out.println("Hello world from my quarry mod!");

        // Register our objects
        ObjectRegistry.registerObject("stonequarry", new ProcessingStoneQuarryObject(), 10, true);
        ObjectRegistry.registerObject("copperquarry", new ProcessingCopperQuarryObject(), 20, true);
        ObjectRegistry.registerObject("ironquarry", new ProcessingIronQuarryObject(), 30, true);
        ObjectRegistry.registerObject("goldquarry", new ProcessingGoldQuarryObject(), 50, true);
        ObjectRegistry.registerObject("ivyquarry", new ProcessingIvyQuarryObject(), 100, true);
        ObjectRegistry.registerObject("quartzquarry", new ProcessingQuartzQuarryObject(), 100, true);
        ObjectRegistry.registerObject("tungstenquarry", new ProcessingTungstenQuarryObject(), 200, true);
        ObjectRegistry.registerObject("glacialquarry", new ProcessingGlacialQuarryObject(), 200, true);
        ObjectRegistry.registerObject("ancientfossilquarry", new ProcessingAncientFossilQuarryObject(), 200, true);

        // Add recipe tech for stone quarry
        STONEQUARRY = RecipeTechRegistry.registerTech("STONEQUARRY");
        COPPERQUARRY = RecipeTechRegistry.registerTech("COPPERQUARRY");
        IRONQUARRY = RecipeTechRegistry.registerTech("IRONQUARRY");
        GOLDQUARRY = RecipeTechRegistry.registerTech("GOLDQUARRY");
        IVYQUARRY = RecipeTechRegistry.registerTech("IVYQUARRY");
        QUARTZQUARRY = RecipeTechRegistry.registerTech("QUARTZQUARRY");
        TUNGSTENQUARRY = RecipeTechRegistry.registerTech("TUNGSTENQUARRY");
        GLACIALQUARRY = RecipeTechRegistry.registerTech("GLACIALQUARRY");
        ANCIENTFOSSILQUARRY = RecipeTechRegistry.registerTech("ANCIENTFOSSILQUARRY");

    }

    public void postInit() {

        // Stone quarry object recipe, crafted at workstation for 100 stone
        Recipes.registerModRecipe(new Recipe(
                "stonequarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("stone", 100)
                }
        ).showAfter("forge"));

        Recipes.registerModRecipe(new Recipe(
                "copperquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("copperbar", 25)
                }
        ).showAfter("stonequarry"));

        Recipes.registerModRecipe(new Recipe(
                "ironquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", 25)
                }
        ).showAfter("copperquarry"));

        Recipes.registerModRecipe(new Recipe(
                "goldquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("goldbar", 25)
                }
        ).showAfter("ironquarry"));

        Recipes.registerModRecipe(new Recipe(
                "ivyquarry",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("ivybar", 25)
                }
        ).showAfter("goldquarry"));

        Recipes.registerModRecipe(new Recipe(
                "quartzquarry",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("quartz", 40)
                }
        ).showAfter("ivyquarry"));

        Recipes.registerModRecipe(new Recipe(
                "tungstenquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("tungstenbar", 25)
                }
        ).showAfter("quartzquarry"));

        Recipes.registerModRecipe(new Recipe(
                "glacialquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("glacialbar", 25)
                }
        ).showAfter("tungstenquarry"));

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", 25)
                }
        ).showAfter("glacialquarry"));




        // Recipe for making stone from the quarry
        Recipes.registerModRecipe(new Recipe(
                "stone",
                2,
                STONEQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "copperore",
                1,
                COPPERQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "ironore",
                1,
                IRONQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "goldore",
                1,
               GOLDQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "ivyore",
                1,
                IVYQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "quartz",
                1,
                QUARTZQUARRY,
                new Ingredient[]{}
        ));
        Recipes.registerModRecipe(new Recipe(
                "tungstenore",
                1,
                TUNGSTENQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "glacialore",
                1,
                GLACIALQUARRY,
                new Ingredient[]{}
        ));

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilore",
                1,
                ANCIENTFOSSILQUARRY,
                new Ingredient[]{}
        ));

    }

}
