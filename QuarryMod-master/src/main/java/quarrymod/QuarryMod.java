package quarrymod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import necesse.engine.GlobalData;
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
    public static Tech COALQUARRY;
    public int stonecost = 100;
    public int coppercost = 25;
    public int ironcost = 25;
    public int goldcost = 25;
    public int ivycost = 25;
    public int quartzcost = 40;
    public int tungstencost = 25;
    public int glacialcost = 25;
    public int ancientfossilcost = 25;

    public int coalcost = 25;

    public void init() {
        System.out.println("Hello world from my quarry mod!");

        setValues();

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
        ObjectRegistry.registerObject("coalquarry", new ProcessingCoalQuarryObject(), 100, true);



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
        COALQUARRY = RecipeTechRegistry.registerTech("COALQUARRY");


    }

    public void postInit() {

        boolean coalExists = ItemRegistry.itemExists("coal");
        System.out.println("Coal Exists? " + coalExists);

        // Stone quarry object recipe, crafted at workstation for 100 stone
        Recipes.registerModRecipe(new Recipe(
                "stonequarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("stone", stonecost)
                }
        ).showAfter("forge"));

        Recipes.registerModRecipe(new Recipe(
                "copperquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("copperbar", coppercost)
                }
        ).showAfter("stonequarry"));

        Recipes.registerModRecipe(new Recipe(
                "ironquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ironbar", ironcost)
                }
        ).showAfter("copperquarry"));

        Recipes.registerModRecipe(new Recipe(
                "goldquarry",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("goldbar", goldcost)
                }
        ).showAfter("ironquarry"));

        if (coalExists)
        {
            Recipes.registerModRecipe(new Recipe(
            "coalquarry",
            1,
                    RecipeTechRegistry.DEMONIC,
                    new Ingredient[]{
                            new Ingredient("coal", coalcost)
                    }
            ).showAfter("goldquarry"));
        }

        Recipes.registerModRecipe(new Recipe(
                "ivyquarry",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("ivybar", ivycost)
                }
        ).showAfter(coalExists ? "coalquarry" : "goldquarry"));

        Recipes.registerModRecipe(new Recipe(
                "quartzquarry",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("quartz", quartzcost)
                }
        ).showAfter("ivyquarry"));

        Recipes.registerModRecipe(new Recipe(
                "tungstenquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("tungstenbar", tungstencost)
                }
        ).showAfter("quartzquarry"));

        Recipes.registerModRecipe(new Recipe(
                "glacialquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("glacialbar", glacialcost)
                }
        ).showAfter("tungstenquarry"));

        Recipes.registerModRecipe(new Recipe(
                "ancientfossilquarry",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("ancientfossilbar", ancientfossilcost)
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

        if (coalExists)
        {
            Recipes.registerModRecipe(new Recipe(
                    "coal",
                    1,
                    COALQUARRY,
                    new Ingredient[]{}
            ));
        }
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

    public void setValues()
    {
        BufferedReader reader;
        String filename = GlobalData.cfgPath() + "/mods/QuarryMod/config.cfg";

        try {
            File file = new File(filename);
            if (!file.exists())
            {
                if (!file.getParentFile().mkdirs()) throw new IOException("Error creating directory: " + file.getParentFile().toPath());
                if (!file.createNewFile()) throw new IOException("Error creating file: " + file.toPath());
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
                    writer.write("stone=100\n" +
                            "copper=25\n" +
                            "iron=25\n" +
                            "gold=25\n" +
                            "ivy=25\n" +
                            "quartz=40\n" +
                            "tungsten=25\n" +
                            "glacial=25\n" +
                            "ancientfossil=25\n" +
                            "coal=25");
                }
            }
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line!=null)
            {
                String tempLine = line.substring(line.indexOf("=")+1);
                int x = Integer.parseInt(tempLine);
                if (line.contains("stone"))
                {
                    stonecost = x;
                }
                else if (line.contains("copper"))
                {
                    coppercost = x;
                }
                else if (line.contains("iron"))
                {
                    ironcost = x;
                }
                else if (line.contains("gold"))
                {
                    goldcost = x;
                }
                else if (line.contains("ivy"))
                {
                    ivycost = x;
                }
                else if (line.contains("quartz"))
                {
                    quartzcost = x;
                }
                else if (line.contains("tungsten"))
                {
                    tungstencost = x;
                }
                else if (line.contains("glacial"))
                {
                    glacialcost = x;
                }
                else if (line.contains("ancientfossil"))
                {
                    ancientfossilcost = x;
                }
                else if (line.contains("coal"))
                {
                    coalcost = x;
                }
                line=reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
             e.printStackTrace();
        }
    }

}
