package org.error1015.wso16reborn;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Collections;
import java.util.List;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue IsMatchAll = BUILDER.define("not_only_stack_of_16", false);
    public static final ModConfigSpec.IntValue size = BUILDER.defineInRange("size", 64, 1, 99);

    public static final ModConfigSpec.BooleanValue potions = BUILDER.define("potions", false);
    public static final ModConfigSpec.BooleanValue food = BUILDER.define("food", false);

    // 存储的字符串格式需要满足: modid:namespace
    public static final ModConfigSpec.ConfigValue<List<? extends String>> blackNameList = BUILDER
            .comment("Require modid:item_name","Such as [blackNameList = [\"minecraft:diamond\", \"minecraft:dirt\", ...]]")
            .defineList(
                "blackNameList",
                Collections.emptyList(),
                s -> s instanceof String);

    static {
        SPEC = BUILDER.build();
    }
}