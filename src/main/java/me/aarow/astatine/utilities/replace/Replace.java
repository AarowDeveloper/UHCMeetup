package me.aarow.astatine.utilities.replace;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Replace {

    private String replace;
    private String replaceFor;

    public String getReplace() {
        return replace;
    }

    public String getReplaceFor() {
        return replaceFor;
    }
}
