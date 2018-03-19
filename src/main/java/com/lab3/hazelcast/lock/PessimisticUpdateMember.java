package com.lab3.hazelcast.lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.io.Serializable;

/* One way to solve the race issue is by using pessimistic locking
 * -> lock the map entry until you are finished with it
 *
 * Pessimistic locking is good if there are lots of updates on the same key.
 * It is more robust than optimistic locking from the perspective of data consistency.
 */
public class PessimisticUpdateMember {

    public static void main(String[] args) throws Exception {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IMap<String, Value> map = hz.getMap("map");
        String key = "1";
        map.put(key, new Value());
        System.out.println("Starting");
        for (int i = 0; i < 1000; i++) {
            map.lock(key);
            try {
                Value value = map.get(key);
                Thread.sleep(10);
                value.amount++;
                map.put(key, value);
            } finally {
                map.unlock(key);
            }
        }
        System.out.println("Finished! Result = " + map.get(key).amount);

        Hazelcast.shutdownAll();
    }

    static final class Value implements Serializable {

        private int amount;
    }
}
