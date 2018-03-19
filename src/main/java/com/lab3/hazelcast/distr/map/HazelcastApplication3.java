package com.lab3.hazelcast.distr.map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import static com.lab3.hazelcast.Configuration.getLocalConfig;

public class HazelcastApplication3 {

	public static void main(String[] args) {

		HazelcastInstance instance3 = Hazelcast.newHazelcastInstance(getLocalConfig());
		IMap<Integer, String> mapCustomers = instance3.getMap("customers");
	}
}
