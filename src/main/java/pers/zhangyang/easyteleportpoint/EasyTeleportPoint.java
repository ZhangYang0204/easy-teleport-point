package pers.zhangyang.easyteleportpoint;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyTeleportPoint extends EasyPlugin {
    @Override
    public void onOpen() {
        new Metrics(this,16101);
    }

    @Override
    public void onClose() {

    }
}
