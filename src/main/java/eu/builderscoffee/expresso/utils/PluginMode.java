package eu.builderscoffee.expresso.utils;

import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import lombok.Getter;
import lombok.Setter;

public class PluginMode {

    SettingsConfiguration settingsConfiguration;
    @Getter @Setter
    PluginState pluginState;

    public PluginMode(PluginState state) {
        this.pluginState = state;
    }

    /***
     * Lis la valeur en configuration
     */
    public void readPluginState() {
        String state = settingsConfiguration.getPluginMode();
        if(state.equals(PluginState.DEVELOPMENT.name)) {
            new PluginMode(PluginState.DEVELOPMENT);
        } else if(state.equals(PluginState.PRODUCTION.name)) {
            new PluginMode(PluginState.PRODUCTION);
        }
    }

    /***
     * Imprimer une informations
     * @param debugInfo
     */
    public void printDebugInfo(String debugInfo) {
        if(pluginState.equals(PluginState.DEVELOPMENT)) {
            Log.get().info("[Debug Info] " + debugInfo);
        }
    }

    /***
     * Imprimer un avertisement
     * @param debugWarn
     */
    public void printDebugWarn(String debugWarn) {
        if(pluginState.equals(PluginState.DEVELOPMENT)) {
            Log.get().info("[Debug Warn] " + debugWarn);
        }
    }

    /***
     * Imprimer une erreur
     * @param debugError
     */
    public void printDebugError(String debugError) {
        if(pluginState.equals(PluginState.DEVELOPMENT)) {
            Log.get().info("[Debug Error] " + debugError);
        }
    }

    public enum PluginState {
        PRODUCTION(0,"PROD"),
        DEVELOPMENT(1,"DEV");

        int id;
        String name;

        PluginState(int id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
