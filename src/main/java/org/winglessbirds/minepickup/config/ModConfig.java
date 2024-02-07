package org.winglessbirds.minepickup.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.List;

@Config(name = "minepickup")
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class ModConfig implements ConfigData {

    public enum PickupModeEnum {
        ALL, FIRST_ONLY, NOTHING
    }

    @Comment("Enables or disables the mod completely")
    public boolean modEnabled = true;

    @Comment("Controls what to pick up upon breaking a block that drops multiple items (usually containers).\nALL picks up everything,\nFIRST_ONLY only picks up the broken block itself, not its other drops,\nand NOTHING picks up nothing.")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public PickupModeEnum pickupMode = PickupModeEnum.FIRST_ONLY;

    @Comment("Never automatically pick up listed drops, format: namespace:item (minecraft:redstone)")
    public List<String> itemBlacklist = new ArrayList<>();
}
