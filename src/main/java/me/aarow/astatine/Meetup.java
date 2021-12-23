package me.aarow.astatine;

import lombok.Getter;
import me.aarow.astatine.adapters.paginated.types.PaginatedGUI;
import me.aarow.astatine.adapters.scoreboard.Assemble;
import me.aarow.astatine.commands.impl.*;
import me.aarow.astatine.backend.MongoManager;
import me.aarow.astatine.listener.*;
import me.aarow.astatine.managers.impl.*;
import me.aarow.astatine.providers.ScoreboardProvider;
import me.aarow.astatine.scenario.ScenarioManager;
import me.aarow.astatine.tasks.GlobalDataSyncTask;
import me.aarow.astatine.utilities.ConfigFile;
import me.aarow.astatine.utilities.Utility;
import me.aarow.astatine.utilities.other.BungeeCordUtility;
import me.aarow.astatine.utilities.text.Color;
import org.bukkit.plugin.java.JavaPlugin;

public class Meetup extends JavaPlugin {

    @Getter private static Meetup instance;

    @Getter private ConfigFile settings, messages, menus, items, scoreboard, kits, discord;

    @Getter private MongoManager mongoManager;
    @Getter private ProfileManager profileManager;
    @Getter private GameManager gameManager;
    @Getter private BorderManager borderManager;
    @Getter private WorldManager worldManager;
    @Getter private ScenarioManager scenarioManager;
    @Getter private ReplaceManager replaceManager;
    @Getter private MenuManager menuManager;
    @Getter private KitManager kitManager;
    @Getter private RestartManager restartManager;

    @Override
    public void onEnable(){
        instance = this;

        settings = new ConfigFile(this, "settings.yml");
        messages = new ConfigFile(this, "messages.yml");
        menus = new ConfigFile(this, "menus.yml");
        items = new ConfigFile(this, "items.yml");
        scoreboard = new ConfigFile(this, "scoreboard.yml");
        kits = new ConfigFile(this, "kits.yml");
        discord = new ConfigFile(this, "discord.yml");

        mongoManager = new MongoManager();
        profileManager = new ProfileManager();

        worldManager = new WorldManager();

        worldManager.create(settings.getString("WORLD"), true);
        worldManager.create(settings.getString("LOBBY-WORLD"), false);

        gameManager = new GameManager();
        borderManager = new BorderManager();

        scenarioManager = new ScenarioManager();
        replaceManager = new ReplaceManager();
        menuManager = new MenuManager();
        kitManager = new KitManager();

        restartManager = new RestartManager();

        gameManager.switchGameState();

        registerCommands();
        registerListeners();

        Utility.getOnlinePlayers().forEach(player -> {
            player.kickPlayer(Color.translate("&cReload is not good for server :("));
        });

        Assemble assemble = new Assemble(this, new ScoreboardProvider());
        assemble.setTicks(1);

        new GlobalDataSyncTask().runTaskTimer(Meetup.getInstance(), 20L, 20L);

        BungeeCordUtility.registerBungeecord();
    }

    private void registerCommands(){
        new VoteCommand();
        new LeaderboardCommand();
        new StatsCommand();
        new KitCommand();
        new StaffCommand();
    }

    private void registerListeners(){
        new PlayerListeners();
        new MenuListener();
        new InteractListener();
        new KitMenuListener();
        new DeathMessagesListener();
        new DeathListener();
        new BlockedCommandsListeners();
    }
}
