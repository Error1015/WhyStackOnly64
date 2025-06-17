package org.error1015.wso16reborn;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue notOnlyStackOf16 = BUILDER.define("not_only_stack_of_16", false);
    public static final ModConfigSpec.IntValue size = BUILDER.defineInRange("size", 64, 1, 99);

    public static final ModConfigSpec.BooleanValue potions = BUILDER.define("potions", false);
    public static final ModConfigSpec.BooleanValue food = BUILDER.define("food", false);

    static {
        SPEC = BUILDER.build();
    }
}