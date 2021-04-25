package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.common.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("messages")
public final class MessageConfiguration {

    // Global
    String global_prefix = "§6§lBuilders Coffee §8>> ";

    // Command
    String command_must_be_player = "§cVous devez être un joueur pour executer cette commande";
    String command_bad_syntaxe = "§cMauvaise syntaxe de la commande";

    // Game Command
    String game_cant_edit_type = "§cVous ne pouvez changer le type d'une partie en cours";
    String game_is_to_stop = "§cVous venez de stopper la partie";
    String game_not_going_to_start = "§cVous ne pouvez pas stopper une partie qui n'est pas démarrée";

    // Expresso
    String expresso_competitor_join = "§f%player% §aa rejoins la compétition";
    String expresoo_competitor_leave = "§f%player% §ca quitté la compétition";

    // Team
    String team_info_header = "§7§m-------§6 Info de l''équipe §7§m-------§6";
    String team_info_leader = "§6Leader:§f ";
    String team_info_members = "§aMembres :§f";
    String team_player_join = "§7[§6Team§7]§f Le joueur§a %target% §fa rejoint votre équipe";
    String team_target_join = "§7[§6Team§7]§f Vous avez rejoins l''équipe de %target% !";
    String team_target_already_in_a_team = "[Equipe] Le joueur %target% est déja dans l'équipe";
    String team_already_in_a_team = "§cVous êtes déja dans une équipe";
    String team_limit_reached = "§7[§6Team§7]§f §cVous avez atteint la limite de membres dans votre équipe( %limit% )";
    String team_no_team = "§7[§6Team§7]§a Vous n'avez pas d'équipe de créer !";
    String team_info_no_team = "§cVous n''avez aucune team !";
}
