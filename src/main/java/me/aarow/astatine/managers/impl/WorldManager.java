package me.aarow.astatine.managers.impl;

import me.aarow.astatine.managers.Manager;
import org.bukkit.*;

import java.io.File;
import java.util.stream.Collectors;

public class WorldManager extends Manager {

    public void create(String name, boolean notStatic){
        if(notStatic && Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList()).contains(name)){
            World world = Bukkit.getWorld(name);
            File folder = world.getWorldFolder();
            delete(folder);
        }
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);

        worldCreator.createWorld();

        World world = Bukkit.getWorld(name);
    }

    public boolean delete(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    delete(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }
}
