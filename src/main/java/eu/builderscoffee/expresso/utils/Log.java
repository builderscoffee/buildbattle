package eu.builderscoffee.expresso.utils;

import eu.builderscoffee.expresso.Main;

public class Log {
    private static Log instance;

    static {
        Log.instance = new Log();
    }

    public static Log get() {
        return Log.instance;
    }

    public void info(final String msg) {
        Main.getInstance().getLogger().info(msg);
    }

    public void severe(final String msg) {
        Main.getInstance().getLogger().severe(msg);
    }

    public void warning(final String msg) {
        Main.getInstance().getLogger().warning(msg);
    }
}
