package net.samagames.tools;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * ItemUtils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class ItemUtils
{
    /**
     * Format a given object into a formatted
     * string
     *
     * @param stack Stack
     *
     * @return Formatted string
     */
    public static String stackToStr(ItemStack stack)
    {
        return stack.getType().name() + ", " + stack.getDurability();
    }

    /**
     * Format a given formatted string into
     * a object {@link ItemStack}
     *
     * @param string Formatted string
     *
     * @return Stack
     */
    public static ItemStack strToStack(String string)
    {
        String[] data = string.split(", ");
        return new ItemStack(Material.matchMaterial(data[0]), 1, Short.valueOf(data[1]));
    }

    /**
     * Get the given player's username head
     *
     * @param player Player's username
     *
     * @return Player's head
     */
    public static ItemStack getPlayerHead(String player)
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player);
        head.setItemMeta(meta);

        return head;
    }

    public static ItemStack getCustomHead(String url)
    {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();

        if (propertyMap == null)
            throw new IllegalStateException("Profile doesn't contain a property map");

        byte[] encodedData = url.getBytes();
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();

        try
        {
            Reflection.setValue(headMeta, "profile", profile);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        return head;
    }
}
