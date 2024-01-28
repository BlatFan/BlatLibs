import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for representing a BossBar in Bukkit/Spigot for Minecraft.
 */
public interface BossBar {

    /**
     * Gets the list of players who can see the BossBar.
     *
     * @return List of viewers
     */
    List<Player> getViewers();

    /**
     * Adds a player to the list of viewers for the BossBar.
     *
     * @param player Player to add
     */
    void addViewer(Player player);

    /**
     * Removes a player from the list of viewers for the BossBar.
     *
     * @param player Player to remove
     */
    void removeViewer(Player player);

    /**
     * Gets the color of the BossBar.
     *
     * @return Color of the BossBar
     */
    BossBarAPI.Color getColor();

    /**
     * Sets the color of the BossBar.
     *
     * @param color New color for the BossBar
     */
    void setColor(BossBarAPI.Color color);

    /**
     * Gets the style of the BossBar.
     *
     * @return Style of the BossBar
     */
    BossBarAPI.Style getStyle();

    /**
     * Sets the style of the BossBar.
     *
     * @param style New style for the BossBar
     */
    void setStyle(BossBarAPI.Style style);

    /**
     * Modifies a property of the BossBar.
     *
     * @param property Property to modify
     * @param flag     Whether to enable or disable the property
     */
    void setProperty(BossBarAPI.Property property, boolean flag);

    /**
     * Gets the message displayed on the BossBar.
     *
     * @return Message on the BossBar
     */
    String getMessage();

    /**
     * Sets the visibility of the BossBar.
     *
     * @param visible Whether the BossBar is visible or not
     */
    void setVisible(boolean visible);

    /**
     * Checks if the BossBar is currently visible.
     *
     * @return True if visible, false otherwise
     */
    boolean isVisible();

    /**
     * Gets the progress of the BossBar (0.0 - 1.0).
     *
     * @return Progress of the BossBar
     */
    float getProgress();

    /**
     * Sets the progress of the BossBar (0.0 - 1.0).
     *
     * @param progress New progress value
     */
    void setProgress(float progress);

    // Deprecated methods (to be reviewed and potentially removed)

    @Deprecated
    float getMaxHealth();

    @Deprecated
    void setHealth(float percentage);

    @Deprecated
    float getHealth();

    @Deprecated
    void setMessage(String message);

    @Deprecated
    Player getReceiver();

    @Deprecated
    Location getLocation();

    @Deprecated
    void updateMovement();
}
