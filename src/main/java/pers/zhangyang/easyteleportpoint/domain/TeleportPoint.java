package pers.zhangyang.easyteleportpoint.domain;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class TeleportPoint {
    private Location location;
    private ItemStack button;
    private Double cost;

    public TeleportPoint( Location location, ItemStack button,Double cost) {
        this.location = location;
        this.button = button;
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getButton() {
        return button;
    }
}
