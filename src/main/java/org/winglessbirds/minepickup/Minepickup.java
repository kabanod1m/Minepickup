package org.winglessbirds.minepickup;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winglessbirds.minepickup.config.ModConfig;
import org.winglessbirds.minepickup.event.PlayerBlockBreakHandler;
import org.winglessbirds.minepickup.event.PlayerJoinHandler;
import org.winglessbirds.minepickup.event.PlayerLeaveHandler;
import org.winglessbirds.minepickup.event.TickHandler;

public class Minepickup implements ModInitializer {

    public static final String MODID = "minepickup";
    public static final Logger LOG = LoggerFactory.getLogger(MODID);
    public static ModConfig CFG = new ModConfig();

    @Override
    public void onInitialize() {
        //LOG.info("TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST");
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        CFG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        PlayerBlockBreakEvents.AFTER.register(new PlayerBlockBreakHandler());
        ServerPlayConnectionEvents.JOIN.register(new PlayerJoinHandler());
        ServerPlayConnectionEvents.DISCONNECT.register(new PlayerLeaveHandler());
        ServerTickEvents.END_WORLD_TICK.register(new TickHandler());
    }
}
