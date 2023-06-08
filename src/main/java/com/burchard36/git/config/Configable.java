package com.burchard36.git.config;

public interface Configable {

    /**
     * Loads the values from the cached config into static variables for easy use later
     */
    void loadConfig();

    /**
     * Reloads the cached config file from its file, and reset the static variables in this class
     */
    void reloadConfig();
}
