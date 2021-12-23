package me.aarow.astatine.menus;

import lombok.RequiredArgsConstructor;
import me.aarow.astatine.Meetup;
import me.aarow.astatine.adapters.paginated.buttons.GUIButton;
import me.aarow.astatine.adapters.paginated.types.PaginatedGUI;
import me.aarow.astatine.utilities.ItemBuilder;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.text.StringUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class AlivePlayersMenu {

    private Player player;

    private String name = Meetup.getInstance().getMenus().getString("ALIVE-PLAYERS.NAME");

    private Material material = Material.valueOf(Meetup.getInstance().getMenus().getString("ALIVE-PLAYERS.BUTTON.MATERIAL"));

    private String itemName = Meetup.getInstance().getMenus().getString("ALIVE-PLAYERS.BUTTON.NAME");

    public void open(){

        PaginatedGUI gui = new PaginatedGUI(name);

        List<Player> alivePlayers = Meetup.getInstance().getGameManager().getAlivePlayers();

        alivePlayers.forEach(alivePlayer -> {

            List<String> lore = Utility.replaceList(Meetup.getInstance().getMenus().getStringList("ALIVE-PLAYERS.BUTTON.LORE"), Meetup.getInstance().getReplaceManager().getPlayerReplace(alivePlayer));

            GUIButton button = new GUIButton(new ItemBuilder(material).setName(itemName.replace("<player>", alivePlayer.getName())).setLore(lore).toItemStack());

            gui.addButton(button);
        });

        player.openInventory(gui.getInventory());
    }
}
