
package com.medicalmod.registry;

import com.medicalmod.MedicalMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(ForgeRegistries.CREATIVE_MODE_TABS, MedicalMod.MODID);

    public static final RegistryObject<CreativeModeTab> MEDICAL_TAB = TABS.register("medical_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.medical_tab"))
            .icon(() -> new ItemStack(ModItems.ULTRASOUND.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.ULTRASOUND.get());
                output.accept(ModItems.EKG.get());
                output.accept(ModItems.HEART_MONITOR.get());
                output.accept(ModItems.DEFIBRILLATOR.get());
                output.accept(ModItems.ULTRASOUND_GEL.get());
                output.accept(ModItems.OXYGEN_MASK.get());
                output.accept(ModItems.STETH_PINK_SILVER.get());
            })
            .build());
}
