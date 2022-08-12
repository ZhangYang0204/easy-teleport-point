package pers.zhangyang.easyteleportpoint.yaml;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.YamlBase;
import pers.zhangyang.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easylibrary.exception.UnsupportedMinecraftVersionException;
import pers.zhangyang.easylibrary.util.ItemStackUtil;
import pers.zhangyang.easylibrary.util.VersionUtil;
import pers.zhangyang.easyteleportpoint.domain.TeleportPoint;

import java.util.ArrayList;
import java.util.List;

public class TeleportPointYaml extends YamlBase {
    public static final TeleportPointYaml INSTANCE = new TeleportPointYaml();

    private TeleportPointYaml() {
        super("teleportPoint.yml");
    }


    public List<TeleportPoint> listTeleportPoint(){
        List<TeleportPoint> teleportPointList=new ArrayList<>();

        ConfigurationSection configurationSection=yamlConfiguration.getConfigurationSection("teleportPoint");
        if (configurationSection==null){
            return teleportPointList;
        }
        for (String s:configurationSection.getKeys(false)){

            String worldName=getString("teleportPoint."+s+".location.worldName");
            Double x=getDouble("teleportPoint."+s+".location.x");
            Double y=getDouble("teleportPoint."+s+".location.y");
            Double z=getDouble("teleportPoint."+s+".location.z");
            Double yawD=getDouble("teleportPoint."+s+".location.yaw");
            Double pitchD=getDouble("teleportPoint."+s+".location.pitch");
            if (worldName==null||x==null||y==null||z==null||yawD==null||pitchD==null){
                continue;
            }
            World world= Bukkit.getWorld(worldName);
            if (world==null){
                continue;
            }
            float yaw=yawD.floatValue();
            float pitch=pitchD.floatValue();
            Location location=new Location(world,x,y,z,yaw,pitch);
            ItemStack button=getButton("teleportPoint."+s+".button");
            if (button==null){
                continue;
            }
            Double cost= getDouble("teleportPoint."+s+".cost");
            if (cost!=null&&cost<0){
                cost=null;
            }

            TeleportPoint teleportPoint=new TeleportPoint(location,button,cost);
            teleportPointList.add(teleportPoint);

        }
        return teleportPointList;
    }
    @Nullable
    private ItemStack getButton(@NotNull String path) {
        String materialName = getStringDefault(path + ".materialName");
        Material material = Material.matchMaterial(materialName);

        if (material == null) {
            return null;
        }
        if (material.equals(Material.AIR)) {
            return new ItemStack(Material.AIR);
        }
        String displayName = getString(path + ".displayName");
        List<String> lore = getStringList(path + ".lore");
        int amount = getIntegerDefault(path + ".amount");
        List<String> itemFlagName = getStringListDefault(path + ".itemFlag");
        List<ItemFlag> itemFlagList = new ArrayList<>();
        Integer customModelData = getInteger(path + ".amount");
        for (String s : itemFlagName) {
            try {
                itemFlagList.add(ItemFlag.valueOf(s));
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil
                .getMinecraftMiddleVersion() < 13) {
            try {
                return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount);
            } catch (NotApplicableException e) {
                return new ItemStack(material);
            }
        } else {
            try {
                return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount, customModelData);
            } catch (NotApplicableException e) {
                return new ItemStack(material);
            } catch (UnsupportedMinecraftVersionException e) {
                //不会发生的
                e.printStackTrace();
                return null;
            }
        }
    }

}
