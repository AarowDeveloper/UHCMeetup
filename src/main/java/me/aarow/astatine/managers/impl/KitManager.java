package me.aarow.astatine.managers.impl;

import lombok.Getter;
import me.aarow.astatine.data.kits.Kit;
import me.aarow.astatine.managers.Manager;
import me.aarow.astatine.utilities.KitUtility;

import java.util.List;

public class KitManager extends Manager {

    @Getter private List<Kit> kits;

    public KitManager(){
        kits = KitUtility.loadKits();
    }

    public Kit getByID(int id){
        return kits.stream().filter(kit -> kit.getId() == id).findFirst().orElse(null);
    }

    public int getNextID(){
        for(int x = 0; x < Integer.MAX_VALUE; x++){
            if(!KitUtility.hasIndex(kits, x)) return x + 1;
        }
        return -1;
    }
}
