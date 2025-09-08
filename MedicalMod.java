
package com.medicalmod;

import com.medicalmod.registry.ModItems;
import com.medicalmod.registry.ModCreativeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MedicalMod.MODID)
public class MedicalMod {
    public static final String MODID = "medicalmod";
    public MedicalMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.REGISTER.register(bus);
        ModCreativeTab.TABS.register(bus);
    }
}
