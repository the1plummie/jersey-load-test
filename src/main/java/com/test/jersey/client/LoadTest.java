package com.test.jersey.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;


/**
 * Created by shouguoli on 7/10/16.
 */
public class LoadTest {

    private static final Logger logger = LoggerFactory.getLogger(LoadTest.class);
    private static final String URL = "http://130.211.12.207/api/system/healthcheck";

    static class Worker extends Thread {
        static MyStats<Long> stat = new MyStats<>(5000);
        Client client;

        public Worker(int id, Client client) {
            super("worker extending thread id: " + id);
            logger.info("my thread created: " + this);
            this.client = client;
            start();
        }

        public void run() {
            while(true) {
                long start = System.currentTimeMillis();
                makeRequest(client, URL);
                stat.addVal(System.currentTimeMillis() - start);
            }
        }
    }

    static String makeRequest(Client client, String url) {
        Response response = client.target(url).request().get();
        if (response.getStatus() != 200) {
            logger.info("bad response... status: " + response.getStatus());
            return null;
        }
        return response.readEntity(String.class);
    }


    public static void main(String[] args) {

        logger.info("args: " + Arrays.toString(args));

        if (args.length < 2) {
            logger.error("need 2 arguments");
            return;
        }

        int threads = Integer.parseInt(args[0]);
        int conns = Integer.parseInt(args[1]);

        Client client = RestClient.newClient();

        String resp = makeRequest(client, URL);
        logger.info("response: " + resp);

        for (int i=0; i<threads; i++) {
            new Worker(i, client);
        }
    }

}
