package com.lab3.hazelcast.distr.map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import static com.lab3.hazelcast.Configuration.getLocalConfig;

public class HazelcastApplication1 {
	public static void main(String[] args) throws InterruptedException {

		HazelcastInstance instance1 = Hazelcast.newHazelcastInstance(getLocalConfig());

		IMap<Integer, String> customers = instance1.getMap("customers");
		customers.put(1, "Joe");

		System.out.println("New customer with key 1: " + instance1.getMap("customers").get(1));

		/* Uncomment for LOCK testing: start instance1, and after it instance3
		* -> see that instance3 cannot put new value with "1" key
		* Stop instance1
		* -> see that instance3 have successfully updated mup with key "1" and new value "Nick"
		*/

		//instance1.getMap("customers").lock(1);

	}
}
