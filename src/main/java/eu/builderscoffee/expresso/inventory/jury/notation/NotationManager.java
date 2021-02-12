package eu.builderscoffee.expresso.inventory.jury.notation;

import com.intellectualcrafters.plot.object.Plot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NotationManager {
    private HashMap<Plot, Set<Notation>> allNotation;

    public NotationManager() {
        this.allNotation = new HashMap<>();
    }

    public void addNotationInPlot(Plot plot,Notation note){
        if(allNotation.containsValue(plot)){
            allNotation.get(plot).add(note);
        }else{
            Set tem = new HashSet();
            tem.add(note);
            allNotation.put(plot, tem);
        }
    }
    public Set getNotationsByPlot(Plot plot){
        return allNotation.get(plot);
    }
    /*public Set getNotationsByJury(Plot plot){
        return allNotation.get(plot);
    }





     */
}
