package com.lab3.hazelcast.distr.map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import static com.lab3.hazelcast.Configuration.getLocalConfig;

public class HazelcastApplication2 {

	public static void main(String[] args) {
		HazelcastInstance instance2 = Hazelcast.newHazelcastInstance(getLocalConfig());
		IMap<Object, Object> customers = instance2.getMap("customers");
		customers.put(2, "Nick");
		System.out.println("Old customer with key 1: " + customers.get(1));
		System.out.println("New customer with key 2: " + customers.get(2));


/*		instance2.getCluster().addMembershipListener(new MembershipListener() {
			@Override
			public void memberAdded(MembershipEvent membershipEvent) {
				System.out.println("Customer with key 1: "+ instance2.getMap("customers").get(1));
				System.out.println("Cluster size: " + instance2.getCluster().getMembers().size());
			}

			@Override
			public void memberRemoved(MembershipEvent membershipEvent) {
				System.out.println("Customer with key 1: "+ instance2.getMap("customers").get(1));
				System.out.println("Cluster size: " + instance2.getCluster().getMembers().size());
			}

			@Override
			public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
				System.out.println("Customer with key 1: "+ instance2.getMap("customers").get(1));
				System.out.println("Cluster size: " + instance2.getCluster().getMembers().size());
			}
		});*/
	}
}
