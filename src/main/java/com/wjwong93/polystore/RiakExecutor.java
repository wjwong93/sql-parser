package com.wjwong93.polystore;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.*;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;
import com.wjwong93.polystore.dbExecutor.KeyValueDBExecutor;
import com.wjwong93.polystore.query.KeyValueQuery;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RiakExecutor implements KeyValueDBExecutor {
    private final int port;
    private final String remoteAddress;
    private final Namespace namespace;

    public RiakExecutor(String remoteAddress, int port, String namespace) {
        this.remoteAddress = remoteAddress;
        this.port = port;
        this.namespace = new Namespace(namespace);
    }
    @Override
    public void executeCreateQuery(KeyValueQuery query) {
        // Same as Update Query
        executeUpdateQuery(query);
    }

    @Override
    public void executeReadQuery(KeyValueQuery query, Connection connection) {
        try (TableBuilder tableBuilder = new TableBuilder(connection)) {
            RiakClient client = RiakClient.newClient(port, remoteAddress);

            tableBuilder.setTableName(query.getTableId());
            tableBuilder.setColumnNames("key", "value");

            List<String[]> keyvalues = query.getKeyValues();
            int rowCount = 0;
            if (keyvalues == null) {
                // GET ALL
                ListKeys listKeys = new ListKeys.Builder(namespace).build();
                ListKeys.Response keyLocations = client.execute(listKeys);
                for (Location location : keyLocations) {
                    String key = location.getKeyAsString();
                    FetchValue fetchValue = new FetchValue.Builder(location).build();
                    String value = client.execute(fetchValue).getValue(String.class);
                    tableBuilder.insertRow(key, value);
                    rowCount++;
                }
            } else {
                for (String[] kv : keyvalues) {
                    String key = kv[0];
                    Location location = new Location(namespace, key);
                    FetchValue fetchValue = new FetchValue.Builder(location).build();
                    String value = client.execute(fetchValue).getValue(String.class);
                    if (value != null) {
                        tableBuilder.insertRow(key, value);
                        rowCount++;
                    }
                }
            }

            if (rowCount > 0) tableBuilder.build();
            client.shutdown();
        } catch (SQLException | ExecutionException | InterruptedException | UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeUpdateQuery(KeyValueQuery query) {
        try {
            RiakClient client = RiakClient.newClient(port, remoteAddress);
            List<String[]> keyvalues = query.getKeyValues();
            for (String[] kv : keyvalues) {
                RiakObject valueObject = new RiakObject()
                        .setContentType("text/plain")
                        .setValue(BinaryValue.create(kv[1]));
                Location location = new Location(namespace, kv[0]);
                StoreValue store = new StoreValue.Builder(valueObject)
                        .withLocation(location)
                        .build();
                client.execute(store);
            }
            client.shutdown();
        } catch (ExecutionException | InterruptedException | UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeDeleteQuery(KeyValueQuery query) {
        try {
            RiakClient client = RiakClient.newClient(port, remoteAddress);
            List<String[]> keyvalues = query.getKeyValues();
            if (keyvalues == null) {
                // DELETE ALL
                ListKeys listKeys = new ListKeys.Builder(namespace).build();
                ListKeys.Response keyLocations = client.execute(listKeys);
                for (Location location : keyLocations) {
                    DeleteValue delete = new DeleteValue.Builder(location).build();
                    client.execute(delete);
                }
            } else {
                for (String[] kv : keyvalues) {
                    String key = kv[0];
                    Location location = new Location(namespace, key);
                    DeleteValue delete = new DeleteValue.Builder(location).build();
                    client.execute(delete);
                }
            }
            client.shutdown();
        } catch (ExecutionException | InterruptedException | UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }
}
