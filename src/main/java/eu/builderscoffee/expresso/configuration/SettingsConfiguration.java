package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("settings")
public class SettingsConfiguration {

    String expressoAllPermission = "expresso.*";
}
