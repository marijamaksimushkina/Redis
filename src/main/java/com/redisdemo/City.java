package com.redisdemo;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class City {
    public static void main(String[] args) {

        Jedis client = new Jedis("localhost", 6379);
        City cites = new City(client);
        cites.start();

    }
    private Jedis client;

    public City(Jedis client) {
        this.client = client;
    }

    public void start(){
        init();
        showCheapest();
        showExpensive();
        removeKey();
    }

    private void init (){
        client.zadd("City",21021, "London");
        client.zadd("City",57544, "Melbourne");
        client.zadd("City",24315, "Paris");
        client.zadd("City",25862, "Rome");
        client.zadd("City",26492, "Berlin");
        client.zadd("City",26924, "Barcelona");
        client.zadd("City",27476, "Amsterdam");
        client.zadd("City",34129, "Madrid");
        client.zadd("City",55164, "Sydney");
        client.zadd("City",56853, "New York");
    }

    private void showCheapest(){
        Set<String> city = (Set<String>) client.zrange("City", 0, 2);
        System.out.println("Самые дешевые направления: " + city);
    }

    private void showExpensive(){
        Set<String> city = (Set<String>) client.zrevrange("City", 0, 2);
        System.out.println("Самые дорогие направления: " + city);
        System.out.println();
    }

    private void removeKey() {
        client.del("City");
    }

}
