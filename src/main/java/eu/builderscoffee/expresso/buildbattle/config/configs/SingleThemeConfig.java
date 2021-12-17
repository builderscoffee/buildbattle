package eu.builderscoffee.expresso.buildbattle.config.configs;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.api.common.data.DataManager;
import eu.builderscoffee.api.common.data.tables.BuildbattleThemeEntity;
import eu.builderscoffee.api.common.data.tables.Profil;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.config.ConfigTemplate;
import lombok.val;
import org.bukkit.Material;

public class SingleThemeConfig extends ConfigTemplate {

    public SingleThemeConfig() {
        super("theme");
    }

    @Override
    public ServerManagerResponse request(ServerManagerRequest request, ServerManagerResponse response) {
        System.out.println(">> Request " + this.getClass().getSimpleName());
        ExpressoBukkit.getBbGame().getBbGameManager().setThemes(request.getData());
        return redirect(PlotConfig.class, response);
    }

    @Override
    public ServerManagerResponse response(ServerManagerResponse response) {
        System.out.println(">> Response " + this.getClass().getSimpleName());
        val pageItemsAction = new ServerManagerResponse.PageItems();
        pageItemsAction.setType(type);
        // Get Themes form database
        val data = DataManager.getBuildbattleThemeStore().select(BuildbattleThemeEntity.class).get();

        // Paginate the themes
        data.forEach(theme -> pageItemsAction.addItem(new ItemBuilder(Material.MAP).setName("§a" + theme.getNames().stream().filter(translation -> translation.getLanguage().equals(Profil.Languages.FR)).findAny().get().getName()).build(), theme.getNames().stream().filter(translation -> translation.getLanguage().equals(Profil.Languages.FR)).findAny().get().getName()));

        // Add Action to response
        response.getActions().add(pageItemsAction);

        // Add return item
        addPreviousConfigItem(response, PhasesConfig.class);
        return response;
    }
}
