package me.damoebe;

public class Properties {
    private String lastOpenProject;
    private boolean darkMode;

    public Properties(String lastOpenProject, boolean darkMode) {
        this.lastOpenProject = lastOpenProject;
        this.darkMode = darkMode;
    }

    public String getLastOpenProject() {
        return lastOpenProject;
    }

    public void setLastOpenProject(String lastOpenProject) {
        this.lastOpenProject = lastOpenProject;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
