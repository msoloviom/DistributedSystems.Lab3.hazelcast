package com.lab3.hazelcast.rep.map;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class FillMapMember {

    public static void main(String[] args) {
        Config cfg = new Config();
        MapConfig mapCfg = new MapConfig();
        mapCfg.setName("map");
        mapCfg.setReadBackupData(false);
        // Setting backup count to 0 to be sure that copy
        // of replicated map will be stored on another node anyway
        mapCfg.setBackupCount(0);
        cfg.addMapConfig(mapCfg);

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
        Map<String, String> map = hz.getReplicatedMap("map");

        map.put("1", "Tokyo");
        map.put("2", "Paris");
        map.put("3", "New York");
        System.out.println("Finished loading map");

        HazelcastInstance hz2 = Hazelcast.newHazelcastInstance();

        System.out.println("First instance was shutdown");
        hz.shutdown();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Reading map content from another node...");
        Map<String, String> map2 = hz2.getReplicatedMap("map");
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
