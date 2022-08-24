package pers.zhangyang.easyteleportpoint.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easylibrary.util.CommandUtil;
import pers.zhangyang.easylibrary.util.PageUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.util.TimeUtil;
import pers.zhangyang.easyteleportpoint.yaml.GuiYaml;
import pers.zhangyang.easyteleportpoint.yaml.TeleportPointYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AllTeleportPointPage extends MultipleGuiPageBase implements BackAble {
    public AllTeleportPointPage( @NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner) {

        super(GuiYaml.INSTANCE.getString("gui.title.allTeleportPointPage"), viewer, backPage, owner,54);
    }
    private List<TeleportPoint> teleportPointList;
    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList==null){
            return;
        }
        CommandUtil.dispatchCommandList(viewer,cmdList);
    }

    @Override
    public int getBackSlot() {
        return 49;
    }

    @Override
    public void send() {

        this.pageIndex=0;
        refresh();
    }

    @Override
    public void refresh() {



        this.inventory.clear();
        List<TeleportPoint> teleportPointList=new ArrayList<>(TeleportPointYaml.INSTANCE.listTeleportPoint());
        this.teleportPointList= PageUtil.page(this.pageIndex,45,teleportPointList );

        for (int i=0;i<45;i++){
            if (i >= teleportPointList.size()) {
                break;
            }
            TeleportPoint ask=teleportPointList.get(i);
            ItemStack itemStack=ask.getButton().clone();

            HashMap<String,String> rep=new HashMap<>();
            World world=ask.getLocation().getWorld();
            if (world==null) {
                rep.put("{world}", "/");
            }else {
                rep.put("{world}", ask.getLocation().getWorld().getName());
            }
            rep.put("{x}", String.valueOf(ask.getLocation().getX()));
            rep.put("{y}", String.valueOf(ask.getLocation().getY()));
            rep.put("{z}", String.valueOf(ask.getLocation().getZ()));

            ReplaceUtil.replaceDisplayName(itemStack,rep);
            ReplaceUtil.replaceLore(itemStack,rep);

            this.inventory.setItem(i,itemStack);
        }




        ItemStack returnPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.allTeleportPointPage.back");
        this.inventory.setItem(49,returnPage);




        if (pageIndex > 0) {
            ItemStack previous = GuiYaml.INSTANCE.getButtonDefault("gui.button.allTeleportPointPage.previousPage");
            inventory.setItem(45, previous);
        }
        int maxIndex = PageUtil.computeMaxPageIndex(teleportPointList.size(), 45);
        if (pageIndex > maxIndex) {
            this.pageIndex = maxIndex;
        }
        if (pageIndex < maxIndex) {
            ItemStack next = GuiYaml.INSTANCE.getButtonDefault("gui.button.allTeleportPointPage.nextPage");
            inventory.setItem(53, next);
        }

        viewer.openInventory(this.inventory);
    }

    @Override
    public int getPreviousPageSlot() {
        return 45;
    }

    @Override
    public int getNextPageSlot() {
        return 53;
    }

    public List<TeleportPoint> getTeleportPointList() {
        return teleportPointList;
    }
}
