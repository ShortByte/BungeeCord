package net.md_5.bungee.api.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Event called to represent a player resource pack response.
 */
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class ResourcePackResponseEvent extends Event
{

    /**
     * The player involved with this event.
     */
    private final ProxiedPlayer player;
    /**
     * The result id.
     */
    private final int resultId;
    /**
     * The result.
     */
    private final Result result;

    public enum Result
    {

        SUCCESSFULLY_DOWNLOADED, DECLINED, FAILED_TO_DOWNLOAD, ACCEPTED, INVALID_URL, FAILED_TO_RELOAD, DISCARDED;
    }

    public ResourcePackResponseEvent(ProxiedPlayer player, int resultId)
    {
        this.player = player;
        this.resultId = resultId;
        this.result = Result.values()[resultId];
    }
}
