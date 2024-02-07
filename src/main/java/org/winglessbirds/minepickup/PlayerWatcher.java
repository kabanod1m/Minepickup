package org.winglessbirds.minepickup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class PlayerWatcher {

    public static List<PlayerWatcher> instances = new Vector<>();

    public static PlayerWatcher findWatcher(PlayerEntity player) throws NoSuchElementException {
        for (PlayerWatcher watcher : instances) {
            if (watcher.player.equals(player)) {
                return watcher;
            }
        }
        throw new NoSuchElementException("There is no such player watched!");
    }

    public PlayerEntity player;
    public Deque<BrokenBlock> brokenBlocks;

    public PlayerWatcher(PlayerEntity player) {
        this.player = player;
        brokenBlocks = new ArrayDeque<>();
    }

    public void addBlock (World world, PlayerEntity owner, BlockPos pos) {
        if (Minepickup.CFG.modEnabled) {
            brokenBlocks.add(new BrokenBlock(world, owner, pos));
        }
    }

    public void tick() {
        /*for (Iterator<BrokenBlock> i = brokenBlocks.iterator(); i.hasNext(); ) {
            if (i.next().tick()) {
                i.remove();
            }
        }*/
        brokenBlocks.removeIf(BrokenBlock::tick);
    }
}
