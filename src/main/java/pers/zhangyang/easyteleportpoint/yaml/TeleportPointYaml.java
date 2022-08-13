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


            Location location=getLocation("teleportPoint."+s+".location");
            if (location==null){
                continue;
            }
            ItemStack button=getButton("teleportPoint."+s+".button");
            if (button==null){
                continue;
            }
            Double cost= SettingYaml.INSTANCE.getNonnegativeDouble("teleportPoint."+s+".cost");
            if (cost!=null&&cost<0){
                cost=null;
            }
            TeleportPoint teleportPoint=new TeleportPoint(location,button,cost);
            teleportPointList.add(teleportPoint);

        }
        return teleportPointList;
    }

}
