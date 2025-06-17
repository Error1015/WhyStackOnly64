package org.error1015.wso16reborn;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@Mod(Wso16reborn.MODID)
public class Wso16reborn {
    public static final String MODID = "wso64";

    public Wso16reborn(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::setComponentsForItem);
    }

    public void setComponentsForItem(ModifyDefaultComponentsEvent event) {
        if (Config.notOnlyStackOf16.get()) {
            event.modifyMatching(this::matchAll, this::setMaxSize);
        } else {
            event.modifyMatching(this::match16, this::setMaxSize);
        }
    }

    /**
     * 最大堆叠不为1
     * 是食物且最大堆叠为1
     * 是药水
     * 是桶
     */
    private boolean matchAll(Item item) {
        var stack = item.getDefaultInstance();
        var maxSize = item.components().get(DataComponents.MAX_STACK_SIZE);

        boolean maxSizeNoOne = maxSize != null && maxSize != 1;
        boolean isFood = Config.food.get() && stack.getFoodProperties(null) != null;
        boolean isPotion = Config.potions.get() && item instanceof PotionItem;

        if (item instanceof TieredItem) {
            return false;
        }

        return maxSizeNoOne || isFood || isPotion;
    }

    private boolean match16(Item item) {
        return item.getDefaultInstance().getMaxStackSize() == 16;
    }

    private void setMaxSize(DataComponentPatch.Builder builder) {
        builder.set(DataComponents.MAX_STACK_SIZE, Config.size.get());
    }

}