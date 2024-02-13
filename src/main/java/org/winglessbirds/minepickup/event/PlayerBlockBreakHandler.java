package org.winglessbirds.minepickup.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.winglessbirds.minepickup.Minepickup;
import org.winglessbirds.minepickup.PlayerWatcher;

public class PlayerBlockBreakHandler implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {

        if (world.isClient()) {
            return;
        }

        for (String blockName : Minepickup.CFG.blockBlacklist) {
            if (blockName.equalsIgnoreCase(Registries.BLOCK.getId(state.getBlock()).toString())) { // is it okay to ignore case? I've never seen an uppercase letter in minecraft's namespaced ids
                return;
            }
        }

        PlayerWatcher.findWatcher(player).addBlock(world, player, pos);
    }
}
