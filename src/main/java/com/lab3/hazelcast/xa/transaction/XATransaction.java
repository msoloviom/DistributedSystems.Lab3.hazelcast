package com.lab3.hazelcast.xa.transaction;/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.core.TransactionalSet;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;

public class XATransaction {

    public static void main(String[] args) {

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        HazelcastInstance hazelcastInstance2 = Hazelcast.newHazelcastInstance();

        TransactionOptions options = new TransactionOptions()
                .setTransactionType(TransactionOptions.TransactionType.TWO_PHASE);

        TransactionContext context = hazelcastInstance.newTransactionContext(options);
        context.beginTransaction();

        TransactionalMap map = context.getMap( "customers" );
        TransactionalSet set = context.getSet( "products" );

        try {
            map.put( 1, "Nick" );

            //Uncomment next line to produce rollback
            //hazelcastInstance.shutdown();

            set.add( "phone" );

            context.commitTransaction();
            System.out.println("Transaction was successfully committed");
        } catch ( Throwable t ) {
            System.out.println("Rolling back transaction...");
            context.rollbackTransaction();
        }

        System.out.println("Element that was put into map: " +
                hazelcastInstance2.getMap("customers").get(1));
        System.out.println("New set size: " + hazelcastInstance2.getSet("products").size());
        Hazelcast.shutdownAll();
    }
}
