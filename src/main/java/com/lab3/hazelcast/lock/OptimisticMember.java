package com.lab3.hazelcast.lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.io.Serializable;

/*
 * In Hazelcast, you can apply the optimistic locking strategy with
 * the map's replace method. This method compares values in object
 * or data forms depending on the in-memory format configuration.
 * If the values are equal, it replaces the old value with the new one
 *
 * Optimistic locking is better for mostly read-only systems.
 * It has a performance boost over pessimistic locking.
 */

public class OptimisticMember {

    public static void main(String[] args) throws Exception {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IMap<String, Value> map = hz.getMap("map");
        String key = "1";
        map.put(key, new Value());
        System.out.println("Starting");
        for (int i = 0; i < 1000; i++) {
            if (i % 10 == 0) {
                System.out.println("At: " + i);
            }
            for (; ; ) {
                Value oldValue = map.get(key);
                Value newValue = new Value(oldValue);
                Thread.sleep(10);
                newValue.amount++;
                if (map.replace(key, oldValue, newValue)) {
                    break;
                }
            }
        }
        System.out.println("Finished! Result = " + map.get(key).amount);

        Hazelcast.shutdownAll();
    }

    static final class Value implements Serializable {

        private int amount;

        private Value() {
        }

        private Value(Value that) {
            this.amount = that.amount;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Value)) {
                return false;
            }
            Value that = (Value) o;
            return that.amount == this.amount;
        }

        @Override
        public int hashCode() {
            return amount;
        }
    }
}
