package pers.zhangyang.easyteleportpoint.domain;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Gamer {
    private Player player;

    private Long lastTeleportPointTime;

    @Nullable
    public Long getLastTeleportPointTime() {
        return lastTeleportPointTime;
    }

    public Gamer(Player player){
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setLastTeleportPointTime(Long lastTeleportPointTime) {
        this.lastTeleportPointTime = lastTeleportPointTime;
    }
}
