
package com.medicalmod.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Random;

/**
 * Server-side medical simulation loop:
 * - manages monitored entities' SpO2 and HR
 * - triggers random cardiac arrests if SpO2 falls to 0
 * - CPR gives chance to revive; defib also uses chance influenced by CPR/O2
 */
@Mod.EventBusSubscriber(modid = "medicalmod")
public class MedicalEvents {
    private static final Random RAND = new Random();

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        Player player = event.getPlayer();
        if (player == null || player.level.isClientSide) return;
        // Start CPR if hand empty and interacting with living entity
        if (player.getMainHandItem().isEmpty() && event.getTarget() instanceof LivingEntity le) {
            var tag = le.getPersistentData();
            tag.putBoolean("medical.cpr", true);
            tag.putInt("medical.cpr_timer", 200);
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("msg.medicalmod.cpr.start"), true);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        for (ServerLevel level : event.getServer().getAllLevels()) {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, level.getWorldBorder().getBoundingBox())) {
                var tag = e.getPersistentData();
                if (!tag.getBoolean("medical.monitored")) continue;
                int sp = tag.contains("medical.spO2") ? tag.getInt("medical.spO2") : 98;
                // oxygen mask raises SpO2 over time
                if (tag.getBoolean("medical.oxygen_mask")) sp = Math.min(100, sp + 1);
                else if (RAND.nextInt(40) == 0) sp = Math.max(0, sp - 1);
                tag.putInt("medical.spO2", sp);

                // random cardiac arrest when SpO2 extremely low
                if (sp == 0 && RAND.nextInt(20) == 0) {
                    tag.putBoolean("medical.down", true);
                    tag.putInt("medical.hr", 0);
                }

                // CPR handling: reduce timer, occasionally revive
                if (tag.getBoolean("medical.cpr")) {
                    int t = tag.getInt("medical.cpr_timer");
                    if (t > 0) tag.putInt("medical.cpr_timer", t - 1);
                    else tag.putBoolean("medical.cpr", false);

                    if (tag.getBoolean("medical.down") && RAND.nextInt(60) == 0) {
                        tag.putBoolean("medical.down", false);
                        tag.putInt("medical.hr", 60 + RAND.nextInt(40));
                        tag.putInt("medical.spO2", 80 + RAND.nextInt(20));
                    }
                }

                // HR wander when not down
                int hr = tag.contains("medical.hr") ? tag.getInt("medical.hr") : 70 + RAND.nextInt(10) - 5;
                if (!tag.getBoolean("medical.down") && RAND.nextInt(200) == 0) {
                    hr = Math.max(30, Math.min(160, hr + (RAND.nextInt(2)==0?-10:10)));
                }
                tag.putInt("medical.hr", hr);
            }
        }
    }
}
