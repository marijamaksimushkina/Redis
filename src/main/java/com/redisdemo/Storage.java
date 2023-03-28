package com.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

public class Storage {
    private RedissonClient redissonClient;

    private final static String KEY = "dates";
    private final static Double SCORE_INC = 0.1;

    private RScoredSortedSet users;

    public void init() {
        Config config = new Config();
        config
                .useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(Exc.getMessage());
        }
        users = redissonClient.getScoredSortedSet(KEY, StringCodec.INSTANCE);
    }

    public void donate(String elem) {
        Double firstScore = users.firstScore();
        Double currentScore = users.getScore(elem);
        System.out.println("> Пользователь " + elem + " оплатил платную услугу");
        users.addScore(elem, -currentScore + firstScore - SCORE_INC);
    }

    public void listUsers() {
        for(Object user : users){
            System.out.println("- На главной странице показываем пользователя " + user);
            users.addScoreAsync(user, SCORE_INC);
        }
    }
}
