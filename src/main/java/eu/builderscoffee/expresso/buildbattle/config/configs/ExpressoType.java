package eu.builderscoffee.expresso.buildbattle.config.configs;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import eu.builderscoffee.expresso.buildbattle.config.ConfigTemplate;
import lombok.val;

public class ExpressoType extends ConfigTemplate {

    public ExpressoType() {
        super("expresso");
    }

    @Override
    public ServerManagerResponse request(ServerManagerRequest request, ServerManagerResponse response) {
        System.out.println(">> Request " + this.getClass().getSimpleName());
        val expressoString = request.getData();
        ExpressoBukkit.getBbGame().setExpressoGameType(ExpressoBukkit.getBbGame().getExpressoManager().fetchExpressoByName(expressoString));
        ExpressoBukkit.getBbGame().configureGameType(BuildBattleInstanceType.EXPRESSO);
        return redirect(ExpressoPlayTime.class, response);
    }

    @Override
    public ServerManagerResponse response(ServerManagerResponse response) {
        System.out.println(">> Response " + this.getClass().getSimpleName());
        switch (ExpressoBukkit.getBbGame().getBbGameTypes()) {
            case CLASSIC:
            case TOURNAMENT:
                return redirect(ExpressoType.class, response);
            case EXPRESSO:
                val expressoList = ExpressoBukkit.getBbGame().getExpressoManager().getExpressoGameTypes();
                val pageItemsAction = new ServerManagerResponse.PageItems();
                pageItemsAction.setType(type);
                expressoList
                        .forEach(expresso -> {
                            pageItemsAction.addItem(new ItemBuilder(expresso.getIcon().getType(), 1, expresso.getIcon().getDurability()).setName("§a" + expresso.getName()).addLoreLine(expresso.getDescription()).build(), expresso.getName());
                        });
                // Add Action to response
                response.getActions().add(pageItemsAction);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ExpressoBukkit.getBbGame().getBbGameTypes());
        }
        return response;
    }
}
