package com.perkelle.dev.staffchatbungee.messagequeue;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.perkelle.dev.staffchatbungee.ConfigManager;
import com.perkelle.dev.staffchatbungee.StaffChatBungee;
import com.perkelle.dev.staffchatbungee.utils.MessageFormatter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;
import java.util.function.Consumer;

public class RedisMessageQueue implements IMessageQueue {

    private Gson gson = new Gson();

    private JedisPool pool;

    @Override
    public void connect() {
        JedisPoolConfig config = new JedisPoolConfig();

        // Set threads - we only need 2 (one for sub, one for pub)
        config.setMaxTotal(2);
        config.setMaxIdle(2);

        String host = ConfigManager.getConfig().getString("redis.host", "127.0.0.1");
        int port = ConfigManager.getConfig().getInt("redis.port", 6379);
        String password = ConfigManager.getConfig().getString("redis.password");
        pool = new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, password);

        subscribe();
    }

    @Override
    public void publishMessage(StaffChatMessage message) {
        String json = gson.toJson(message);

        ProxyServer.getInstance().getScheduler().runAsync(StaffChatBungee.getInstance(), () -> {
            try(Jedis jedis = pool.getResource()) {
                jedis.publish("staffchatbungee:messages", json);
            } catch(JedisException ex) {
                StaffChatBungee.getInstance().getLogger().warning("Error while publishing message:");
                ex.printStackTrace();
            }
        });
    }

    @Override
    public Consumer<StaffChatMessage> getMessageConsumer() {
        return result -> {
            Map<String, String> placeholders = ImmutableMap.<String, String> builder()
                    .put("%server%", result.getServerName())
                    .put("%player%", result.getFrom())
                    .put("%message%", result.getMessage())
                    .build();

            String message = MessageFormatter.formatStaffChatMessage(placeholders);

            ProxyServer.getInstance().getPlayers().stream()
                    .filter(p -> p.hasPermission("staffchat.receive") || p.hasPermission("staffchat.*"))
                    .forEach(p -> p.sendMessage(TextComponent.fromLegacyText(message)));
        };
    }

    private void subscribe() {
        ProxyServer.getInstance().getScheduler().runAsync(StaffChatBungee.getInstance(), () -> {
            while(true) {
                try(Jedis jedis = pool.getResource()) {
                    jedis.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            // Deserialize message
                            StaffChatMessage pojo = gson.fromJson(message, StaffChatMessage.class);
                            getMessageConsumer().accept(pojo);
                        }
                    }, "staffchatbungee:messages");
                } catch(JedisException ex) {
                    StaffChatBungee.getInstance().getLogger().warning("Error while reading message, reconnecting in 5 seconds. Did you lose connection to the Redis server?");
                    ex.printStackTrace();

                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        });
    }
}
