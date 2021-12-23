package me.aarow.astatine.managers.impl;

import lombok.Getter;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.utilities.menu.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class MenuManager extends Manager {

    private Map<UUID, Menu> currentOpenedMenu = new HashMap<>();
    private Map<UUID, Menu> lastOpenedMenu = new HashMap<>();
}
