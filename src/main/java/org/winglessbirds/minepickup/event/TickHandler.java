package org.winglessbirds.minepickup.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import org.winglessbirds.minepickup.PlayerWatcher;

public class TickHandler implements ServerTickEvents.EndWorldTick {

    @Override
    public void onEndTick(ServerWorld world) {
        for (PlayerWatcher watcher : PlayerWatcher.instances) {
            watcher.tick();
        }
    }
}
