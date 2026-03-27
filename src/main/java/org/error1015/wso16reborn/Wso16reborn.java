package org.error1015.wso16reborn;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Wso16reborn.MODID)
public class Wso16reborn {
    public static final String MODID = "wso64";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public Wso16reborn(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::setComponentsForItem);
    }

    public void setComponentsForItem(ModifyDefaultComponentsEvent event) {
        if (Config.IsMatchAll.get()) {
            event.modifyMatching(this::matchAll, this::setMaxSize);
        } else {
            event.modifyMatching(this::match16, this::setMaxSize);
        }
    }

    private boolean matchAll(Item item) {
        var stack = item.getDefaultInstance();
        var maxSize = item.components().get(DataComponents.MAX_STACK_SIZE);

        boolean maxSizeNoOne = maxSize != null && maxSize != 1;
        boolean isFood = Config.food.get() && stack.getFoodProperties(null) != null;
        boolean isPotion = Config.potions.get() && item instanceof PotionItem;

        boolean b = maxSizeNoOne || isFood || isPotion;
        String itemKey = BuiltInRegistries.ITEM.getKey(item).toString();

        for (var blackKey : Config.blackNameList.get()) {
            int colonIndex = blackKey.indexOf(':');
            if (colonIndex != -1) {
                    if (itemKey.equals(blackKey)) {
                    return false;   // 物品在黑名单中，排除
                }
            } else {
                LOGGER.warn("{} is not valid content.", blackKey);
            }
        }
        return b;
    }

    private boolean match16(Item item) {
        return item.getDefaultInstance().getMaxStackSize() == 16;
    }

    private void setMaxSize(DataComponentPatch.Builder builder) {
        builder.set(DataComponents.MAX_STACK_SIZE, Config.size.get());
    }

}