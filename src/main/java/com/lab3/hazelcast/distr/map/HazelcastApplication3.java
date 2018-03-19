package com.lab3.hazelcast.distr.map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Map;

import static com.lab3.hazelcast.Configuration.getLocalConfig;

public class HazelcastApplication3 {

	public static void main(String[] args) {

		HazelcastInstance instance3 = Hazelcast.newHazelcastInstance(getLocalConfig());
		IMap<Integer, String> mapCustomers = instance3.getMap("customers");

		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		Map<Integer, String> customers = hazelcastInstance.getReplicatedMap("rep_customers");
		customers.put(1, "Joe Smith" );
		customers.put(2, "Ali Selam" );
		customers.put(3, "Avi Noyan" );

		System.out.println("Customers in replicated map: " + customers.values());
	}
}
