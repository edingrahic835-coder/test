
package com.medicalmod.registry;

import com.medicalmod.MedicalMod;
import com.medicalmod.items.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER =
        DeferredRegister.create(ForgeRegistries.ITEMS, MedicalMod.MODID);

    public static final RegistryObject<Item> ULTRASOUND =
        REGISTER.register("ultrasound", () -> new UltrasoundItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EKG =
        REGISTER.register("ekg", () -> new EKGItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HEART_MONITOR =
        REGISTER.register("heart_monitor", () -> new HeartMonitorItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DEFIBRILLATOR =
        REGISTER.register("defibrillator", () -> new DefibrillatorItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ULTRASOUND_GEL =
        REGISTER.register("ultrasound_gel", () -> new GelItem(new Item.Properties().stacksTo(1).durability(40)));
    public static final RegistryObject<Item> OXYGEN_MASK =
        REGISTER.register("oxygen_mask", () -> new OxygenMaskItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> STETH_PINK_SILVER =
        REGISTER.register("stethoscope_pink_silver", () -> new StethoscopeItem(new Item.Properties().stacksTo(1), "pink", "silver"));
    public static final RegistryObject<Item> STETH_PORSCHE_PINK_ROSE =
        REGISTER.register("stethoscope_porsche_rose", () -> new StethoscopeItem(new Item.Properties().stacksTo(1), "porsche", "rosegold"));
    // additional variants omitted for brevity
}
