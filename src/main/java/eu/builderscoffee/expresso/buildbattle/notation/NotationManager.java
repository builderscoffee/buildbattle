package eu.builderscoffee.expresso.buildbattle.notation;

import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NotationManager {
    private final HashMap<Plot, Set<Notation>> allNotation;

    public NotationManager() {
        this.allNotation = new HashMap<>();
    }

    public void addNotationInPlot(Plot plot, Notation note) {
        if (getNotationsByPlot(plot) != null) {
            allNotation.get(plot).add(note);
        } else {
            Set tem = new HashSet();
            tem.add(note);
            allNotation.put(plot, tem);
        }
    }

    public Set getNotationsByPlot(Plot plot) {
        return allNotation.get(plot);
    }

    public boolean playerHasNote(Plot plot, Player pl) {
        Set<Notation> a = getNotationsByPlot(plot);
        if (a != null && !a.isEmpty()) {
            for (Notation note : a) {
                if (note.getUUID() == pl.getUniqueId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
