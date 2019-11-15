package com.github.bartimaeusnek.witcherymodifier;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.*;
import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.RitualAccessor.*;
import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.KettleRecipesAccessor.*;
import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.FamiliarTypes.*;
import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.SpinningRecipesAccessor.*;
import static com.github.bartimaeusnek.witcherymodifier.backend.Accessor.BrazierAccessor.*;

import static java.lang.Short.MAX_VALUE;


@Mod(   modid = WitcheryModifier.MODID,
        version = WitcheryModifier.VERSION,
        name = WitcheryModifier.MODNAME,
        dependencies = "after:witchery;"
)
public class WitcheryModifier {

    public static final String MODID = "witcherymodifier";
    public static final String MODNAME = "Witchery Modifier";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event){
        if (!Loader.isModLoaded("witchery"))
            return;
        addKettleRecipe(new ItemStack(Items.golden_apple,1,0),0, NONE, 0, 0, 0, true, new ItemStack(Items.apple), new ItemStack(Items.gold_ingot,8));
    }
}
