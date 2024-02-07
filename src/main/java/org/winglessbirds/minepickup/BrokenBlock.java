package org.winglessbirds.minepickup;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.winglessbirds.minepickup.config.ModConfig;

import java.util.List;
import java.util.ListIterator;

public class BrokenBlock {
    private static final int timeToPickUp = 1;
    private final World world; // The world that the block breaking occurred within
    private final PlayerEntity owner;
    public int timeSince;
    public BlockPos pos;

    public BrokenBlock(World world, PlayerEntity owner, BlockPos pos) {
        this.world = world;
        this.owner = owner;
        timeSince = 0;
        this.pos = pos;
    }

    public boolean tick() { // returns TRUE if the block needs to be forgotten about, FALSE if needs to be kept ticking
        timeSince++;
        if (timeSince - 1 >= timeToPickUp) {
            List<ItemEntity> drops = world.getEntitiesByClass(ItemEntity.class, new Box(pos), EntityPredicates.VALID_ENTITY);

            if (drops.isEmpty()) {
                return true;
            }

            if (drops.size() > 1 && Minepickup.CFG.pickupMode.equals(ModConfig.PickupModeEnum.NOTHING)) {
                return true;
            }

            for (String s : Minepickup.CFG.itemBlacklist) { // checks if the first drop is within the blacklist, if it is, stops block ticking immediately, preventing further pickups
                if (s.equalsIgnoreCase(Registries.ITEM.getId(drops.get(drops.size() - 1).getStack().getItem()).toString())) { // is it okay to ignore case? I've never seen an uppercase letter in minecraft's namespaced ids
                    return true;
                }
            }

            for (ListIterator<ItemEntity> i = drops.listIterator(drops.size()); i.hasPrevious(); ) {
                ItemEntity item = i.previous();
                //Minepickup.LOG.debug("Got: " + Registries.ITEM.getId(item.getStack().getItem()));

                if (item.age >= timeToPickUp + 1) {
                    Minepickup.LOG.debug("Could not pick up item! Item type: " + Registries.ITEM.getId(item.getStack().getItem()) + "item age: " + item.age + ", time to pick up: " + timeToPickUp);
                    continue;
                }

                ItemStack stack = item.getStack();
                owner.getInventory().insertStack(stack);
                ((ServerWorld) world).getChunkManager().sendToNearbyPlayers(item, new ItemPickupAnimationS2CPacket(item.getId(), owner.getId(), stack.getCount())); // from LivingEntity#snedPickup
                item.discard();

                if (Minepickup.CFG.pickupMode.equals(ModConfig.PickupModeEnum.FIRST_ONLY)) {
                    return true;
                }
            }

            // picks up items in reverse order (if you break a chest, first it will pick up all items from it, and only then the chest itself)
            /*drops.forEach(item -> {

                if (item.age >= timeToPickUp + 1) {
                    Minepickup.LOG.info("Could not pick up item! Item age: " + item.age + ", time to pick up: " + timeToPickUp);
                    return;
                }

                ItemStack stack = item.getStack();
                owner.getInventory().insertStack(stack);
                ((ServerWorld) world).getChunkManager().sendToNearbyPlayers(item, new ItemPickupAnimationS2CPacket(item.getId(), owner.getId(), stack.getCount())); // from LivingEntity#snedPickup
                item.discard();
            });*/
            return true;
        }
        return false;
    }
}
