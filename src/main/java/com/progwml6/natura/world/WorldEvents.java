package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents
{
    @SubscribeEvent
    public void interactEvent(EntityInteract event)
    {
        if (event.getTarget() instanceof EntityCow || event.getTarget() instanceof EntitySheep)
        {
            ItemStack equipped = event.getEntityPlayer().getHeldItem(event.getHand());
            EntityAnimal creature = (EntityAnimal) event.getTarget();

            if (equipped != null && equipped == NaturaCommons.barley && creature.getGrowingAge() == 0 && !creature.isInLove())
            {
                EntityPlayer player = event.getEntityPlayer();

                if (!player.capabilities.isCreativeMode)
                {
                    equipped.shrink(1);

                    if (equipped.getCount() <= 0)
                    {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                }

                creature.setInLove(event.getEntityPlayer());
            }
        }
    }

    @SubscribeEvent
    public void onLivingJoin(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityCow || event.getEntity() instanceof EntitySheep)
        {
            ((EntityLiving) event.getEntity()).tasks.addTask(3, new EntityAITempt((EntityCreature) event.getEntity(), 0.25F, NaturaCommons.materials, false));
        }

        if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId))
        {
            if (event.getEntity() instanceof EntityChicken)
            {
                ((EntityLiving) event.getEntity()).tasks.addTask(3, new EntityAITempt((EntityCreature) event.getEntity(), 0.25F, NaturaOverworld.overworldSeeds, false));
            }
        }
    }
}
