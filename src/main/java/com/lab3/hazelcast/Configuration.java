package com.lab3.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

public class Configuration {
    public static Config getLocalConfig() {
        Config cfg = new Config();

        MapConfig mapCfg = new MapConfig();
        mapCfg.setName("customer");
        mapCfg.setReadBackupData(false);

        //Uncomment next line for map update leak
        //mapCfg.setBackupCount(0);

        cfg.addMapConfig(mapCfg);

        return cfg;
    }
}
