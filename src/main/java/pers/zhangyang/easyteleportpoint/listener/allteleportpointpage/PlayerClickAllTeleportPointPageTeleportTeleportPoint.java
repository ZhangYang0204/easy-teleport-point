package pers.zhangyang.easyteleportpoint.listener.allteleportpointpage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiSerialButtonHandler;
import pers.zhangyang.easylibrary.other.vault.Vault;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyteleportpoint.domain.AllTeleportPointPage;
import pers.zhangyang.easyteleportpoint.domain.Gamer;
import pers.zhangyang.easyteleportpoint.domain.TeleportPoint;
import pers.zhangyang.easyteleportpoint.manager.GamerManager;

import java.util.List;

@EventListener
public class PlayerClickAllTeleportPointPageTeleportTeleportPoint implements Listener {
    @GuiSerialButtonHandler(guiPage = AllTeleportPointPage.class,from = 0,to = 44)
    public void on(InventoryClickEvent event){
        AllTeleportPointPage allTeleportPointPage= (AllTeleportPointPage) event.getInventory().getHolder();

        int slot=event.getRawSlot();

        assert allTeleportPointPage != null;
        TeleportPoint teleportPoint=allTeleportPointPage.getTeleportPointList().get(slot);

        Player player= (Player) event.getWhoClicked();


        Player onlineOwner=allTeleportPointPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        if (!onlineOwner.isOp()) {
            Integer perm = PermUtil.getMinNumberPerm("EasyTeleportPoint.teleportTeleportPointInterval.", onlineOwner);
            if (perm == null) {
                perm = 0;
            }
            Gamer gamer = GamerManager.INSTANCE.getGamer(onlineOwner);
            if (gamer.getLastTeleportPointTime() != null && System.currentTimeMillis() - gamer.getLastTeleportPointTime()
                    < perm * 1000L) {

                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.tooFast");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
        }

        if (teleportPoint.getCost()!=null) {
            if (Vault.hook()==null){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notHookVault");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
            if (Vault.hook().getBalance(onlineOwner)< teleportPoint.getCost()){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notEnoughVault");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
                Vault.hook().withdrawPlayer(onlineOwner, teleportPoint.getCost());
            }



            player.teleport(teleportPoint.getLocation());
        Gamer gamer = GamerManager.INSTANCE.getGamer(onlineOwner);
            gamer.setLastTeleportPointTime(System.currentTimeMillis());

            allTeleportPointPage.refresh();

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.teleportTeleportPoint");
            MessageUtil.sendMessageTo(player, list);

        }






}
