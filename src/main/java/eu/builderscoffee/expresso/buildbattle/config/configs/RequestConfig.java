package eu.builderscoffee.expresso.buildbattle.config.configs;

import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import eu.builderscoffee.expresso.buildbattle.config.ConfigTemplate;
import eu.builderscoffee.expresso.buildbattle.config.configs.game.GameConfig;
import eu.builderscoffee.expresso.buildbattle.config.configs.game.GameSettings;
import eu.builderscoffee.expresso.buildbattle.config.configs.game.Start;
import org.bukkit.Bukkit;

import java.util.Objects;

public class RequestConfig extends ConfigTemplate {
    public RequestConfig() {
        super("request_config");
    }

    @Override
    public ServerManagerResponse request(ServerManagerRequest request, ServerManagerResponse response) {
        System.out.println(">> Request " + this.getClass().getSimpleName());
        // Le type d'instance à déja été définis ?
        if (Objects.isNull(ExpressoBukkit.getBbGame()) || ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
            return redirect(BuildbattleInstanceType.class, response);
            // L'instance définie est expresso mais le type d'expresso n'est pas défini lui
        } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.EXPRESSO) && Objects.isNull(ExpressoBukkit.getBbGame().getExpressoGameType())) {
            return redirect(ExpressoType.class, response);
            // L'instance définie est classic mais le type n'est pas défini lui
        } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.CLASSIC) && Objects.isNull(ExpressoBukkit.getBbGame().getClassicGameType())) {
            return redirect(GameConfig.class, response);
            // L'instance définie est tournois mais le type n'est pas défini lui
        } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.TOURNAMENT) && Objects.isNull(ExpressoBukkit.getBbGame().getTournamentGameType())) {
            return redirect(BuildbattleInstanceType.class, response);
        } else if (Objects.isNull(ExpressoBukkit.getBbGame().getInstancePhases())) {
            // Le thème de la partie n'est pas définie
            return redirect(ExpressoPlayTime.class, response);
        } else if (Objects.isNull(ExpressoBukkit.getBbGame().getBbGameManager().getThemes())) {
            return redirect(ExpressoTheme.class, response);
            // La map n'est pas générée
        } else if (Objects.isNull(Bukkit.getWorld(ExpressoBukkit.getSettings().getPlotWorldName()))) {
            return redirect(PlotConfig.class, response);
            // La partie n'est pas démarrer ou est en pause
        } else if (!ExpressoBukkit.getBbGame().isReady() || ExpressoBukkit.getBbGame().isPaused()) {
            return redirect(Start.class, response);
            // La partie est démarrer
        } else {
            return redirect(GameSettings.class, response);
        }
    }

    @Override
    public ServerManagerResponse response(ServerManagerResponse response) {
        System.out.println(">> Response " + this.getClass().getSimpleName());
        return null;
    }
}
