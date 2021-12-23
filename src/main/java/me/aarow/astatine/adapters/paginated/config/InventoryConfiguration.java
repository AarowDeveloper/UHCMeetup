package me.aarow.astatine.adapters.paginated.config;

public abstract class InventoryConfiguration {

    // The chat prefix for messages.
    private String chatPrefix = "&c&lGUI  &c";
    //private int size;

    public InventoryConfiguration(String chatPrefix){
        this.chatPrefix = chatPrefix;
    }

    public InventoryConfiguration(){

    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public void setChatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
    }

}
