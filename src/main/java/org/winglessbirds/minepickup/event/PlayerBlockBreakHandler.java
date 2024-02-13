package org.winglessbirds.minepickup.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.winglessbirds.minepickup.PlayerWatcher;

public class PlayerBlockBreakHandler implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {

        if (world.isClient()) {
            return;
        }

        PlayerWatcher.findWatcher(player).addBlock(world, player, pos);
    }
}
