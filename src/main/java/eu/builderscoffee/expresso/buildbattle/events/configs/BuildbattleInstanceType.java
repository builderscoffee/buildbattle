package eu.builderscoffee.expresso.buildbattle.events.configs;

import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import lombok.val;

public class BuildbattleInstanceType extends ConfigTemplate {


    public BuildbattleInstanceType() {
        super("type");
    }

    @Override
    public ServerManagerRequest request(ServerManagerRequest request) {
        val type = BuildBattleInstanceType.valueOf(request.getData());
        ExpressoBukkit.setBbGame(new BuildBattle().setBbGameTypes(type));
        return request;
    }

    @Override
    public ServerManagerResponse response(ServerManagerResponse response) {
        val itemsAction = new ServerManagerResponse.Items();

        itemsAction.setType(type);
        int column = 2;
        for (BuildBattleInstanceType bbit : BuildBattleInstanceType.values()) {
            if (bbit.equals(BuildBattleInstanceType.NONE)) continue;
            itemsAction.addItem(2, column, bbit.getIcon(), bbit.name());
            column += 2;
        }

        // Add Action to response
        response.getActions().add(itemsAction);
        return response;
    }
}
