package org.winglessbirds.minepickup.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.loot.context.LootContextParameterSet;
//import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.winglessbirds.minepickup.PlayerWatcher;

//import java.util.List;

public class PlayerBlockBreakHandler implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {

        if (world.isClient()) {
            return;
        }

        PlayerWatcher.findWatcher(player).addBlock(world, player, pos);
    }
}
