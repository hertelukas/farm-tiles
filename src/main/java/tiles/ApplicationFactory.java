package tiles;

import tiles.config.Config;
import tiles.text.MenuText;

//Creation of items with application lifetime
public class ApplicationFactory {

    private Config config;

    public void build() {
        config = new Config();
    }

    public Config getConfig() {
        return config;
    }
}
