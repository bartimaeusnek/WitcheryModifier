package com.github.bartimaeusnek.witcherymodifier.backend;

import com.emoniph.witchery.brewing.WitcheryBrewRegistry;
import com.emoniph.witchery.brewing.action.BrewAction;
import com.emoniph.witchery.brewing.action.BrewActionRitualRecipe;
import com.emoniph.witchery.crafting.BrazierRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.KettleRecipes;
import com.emoniph.witchery.crafting.SpinningRecipes;
import com.emoniph.witchery.ritual.*;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class Accessor {

    private Accessor() {
    }

    public static boolean compareStacks(ItemStack one, ItemStack two, boolean compareNBT){
        return one != null && two != null && ( one.equals(two) || (one.getItem().equals(two.getItem()) && one.getItemDamage() == two.getItemDamage() && (!compareNBT || one.getTagCompound().equals(two.getTagCompound()))));
    }

    public static boolean compareStacks(ItemStack one, ItemStack two){
        return compareStacks(one, two, false);
    }

    public static class KettleRecipesAccessor {
        
        public static ArrayList<KettleRecipes.KettleRecipe> getAllKettleRecipes(){
            return KettleRecipes.instance().recipes;
        }

        public static List<KettleRecipes.KettleRecipe> getKettleRecipesByOutput(ItemStack stack){
            return getAllKettleRecipes().stream().filter(s -> compareStacks(stack,s.output,true)).collect(Collectors.toList());
        }

        public static boolean addKettleRecipe(KettleRecipes.KettleRecipe recipe){
            return getAllKettleRecipes().add(recipe);
        }

        public static boolean removeKettleRecipe(KettleRecipes.KettleRecipe recipe){
            return getAllKettleRecipes().remove(recipe);
        }

        /**
         *
         * @param output
         * @param hatBonus
         * @param familiarType
         * @param powerRequired
         * @param color
         * @param dimID if == 32767 it will enable it specifically for EVERY dim
         * @param inBook
         * @param inputs
         */
        public static void addKettleRecipe(ItemStack output, int hatBonus, FamiliarTypes familiarType, float powerRequired, int color, int dimID, boolean inBook, ItemStack... inputs){
            if (dimID == Short.MAX_VALUE)
                for (int i = -Short.MAX_VALUE; i < Short.MAX_VALUE; i++) {
                    KettleRecipes.instance().addRecipe(output, hatBonus, familiarType.getNum(), powerRequired, color, i, i == 0, inputs);
                }
            else
                KettleRecipes.instance().addRecipe(output, hatBonus, familiarType.getNum(), powerRequired, color, dimID, inBook, inputs);
        }
        
    }

    public static class SpinningRecipesAccessor {

        public static ArrayList<SpinningRecipes.SpinningRecipe> getAllSpinningRecipes(){
            return SpinningRecipes.instance().recipes;
        }

        public static List<SpinningRecipes.SpinningRecipe> getSpinningRecipesByOutput(ItemStack stack){
            return getAllSpinningRecipes().stream().filter(s -> compareStacks(stack,s.result,true)).collect(Collectors.toList());
        }

        public static void addSpinningRecipe(SpinningRecipes.SpinningRecipe recipe){
            getAllSpinningRecipes().add(recipe);
        }

        public static boolean removeSpinningRecipe(SpinningRecipes.SpinningRecipe recipe){
            return getAllSpinningRecipes().remove(recipe);
        }

        public static void addSpinningRecipe(ItemStack output, ItemStack fibres, ItemStack... modifiers){
            SpinningRecipes.instance().addRecipe(output, fibres, modifiers);
        }

    }

    public static class RitualAccessor{

        public static List<RiteRegistry.Ritual> getAllRituals(){
            return RiteRegistry.instance().getRituals();
        }

        public static List<RiteRegistry.Ritual> getRitualByName(String UnlocalizedName){
           return getAllRituals().stream().filter(ritual -> ritual.getUnlocalizedName().equals(UnlocalizedName)).collect(Collectors.toList());
        }

        public static void addRitual(int ritualID, int bookIndex, Rite rite, Sacrifice initialSacrifice, EnumSet<RitualTraits> traits, Circle... circles){
            RiteRegistry.addRecipe(ritualID, bookIndex, rite, initialSacrifice, traits, circles);
        }

        public static void addRitual(int ritualID, int bookIndex, Rite rite, Sacrifice initialSacrifice, EnumSet<RitualTraits> traits, int numRitualGlyphs, int numOtherwhereGlyphs, int numInfernalGlyphs){
            RiteRegistry.addRecipe(ritualID, bookIndex, rite, initialSacrifice, traits, new Circle(numRitualGlyphs, numOtherwhereGlyphs, numInfernalGlyphs));
        }
        
        public static void removeRitual(RiteRegistry.Ritual ritual){
            getAllRituals().remove(ritual);
        }
        
    }

    public static class BrazierAccessor{
    
        public static List<BrazierRecipes.BrazierRecipe> getAllBrazierRecipes(){
            return BrazierRecipes.instance().recipes;
        }

        public static List<BrazierRecipes.BrazierRecipe> getBrazierRecipesByName(String unlocalizizedName){
            return getAllBrazierRecipes().stream().filter(s -> s.unlocalizedName.equals(unlocalizizedName)).collect(Collectors.toList());
        }

        public static boolean removeBrazierRecipe(BrazierRecipes.BrazierRecipe recipe){
            return getAllBrazierRecipes().remove(recipe);
        }

    }

    public static class CauldronAccessor{
        
        public static List<BrewActionRitualRecipe> getAllCauldronRecipes(){
            return WitcheryBrewRegistry.INSTANCE.getRecipes();
        }

        public static List<BrewActionRitualRecipe> getCauldronRecipesByOutput(ItemStack stack){
            return getAllCauldronRecipes().stream().filter(s -> compareStacks(stack,s.ITEM_KEY.toStack(),true)).collect(Collectors.toList());
        }

        public static boolean removeCauldronRecipe(BrewActionRitualRecipe recipe){
            return getAllCauldronRecipes().remove(recipe);
        }

        public static boolean addCauldronRecipe(BrewActionRitualRecipe recipe){
            return getAllCauldronRecipes().add(recipe);
        }

    }

    public static class DistilleryAccessor{

        public static List<DistilleryRecipes.DistilleryRecipe> getAllDistilleryRecipes(){
            return DistilleryRecipes.instance().recipes;
        }

        public static List<DistilleryRecipes.DistilleryRecipe> getDistilleryRecipesByFirstOutput(ItemStack stack){
            return getAllDistilleryRecipes().stream().filter(s -> compareStacks(stack,s.outputs[0],true)).collect(Collectors.toList());
        }

        public static boolean removeDistilleryRecipe(DistilleryRecipes.DistilleryRecipe recipe){
            return getAllDistilleryRecipes().remove(recipe);
        }

        public static boolean addDistilleryRecipe(DistilleryRecipes.DistilleryRecipe recipe){
            return getAllDistilleryRecipes().add(recipe);
        }

        public static void addDistilleryRecipe(ItemStack input1, ItemStack input2, int jars, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4){
            DistilleryRecipes.instance().addRecipe(input1, input2, jars, output1, output2, output3, output4);
        }

    }

    public enum FamiliarTypes {
        NONE(0), CAT(1), TOAD(2), OWL(3);
        int num;
        FamiliarTypes(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }
    }

}
