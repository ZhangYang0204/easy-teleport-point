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
