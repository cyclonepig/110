package com.tongdao.core.redis;//package com.tongdao.cyg.base.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableCaching // 启用缓存，这个注解很重要；
//@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
//public class RedisCacheConfig extends CachingConfigurerSupport {
//
//    @Value("${spring.redis.port}")
//    private Integer port;
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    /**
//     * 生成key的策略
//     *
//     * @return
//     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    /**
//     * 管理缓存
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @SuppressWarnings("rawtypes")
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        //设置缓存过期时间
//         rcm.setDefaultExpiration(60);//秒
//        //设置value的过期时间
//        Map<String,Integer> map=new HashMap<String, Integer>();
//        map.put("test",60L);
//        rcm.setExpires(map);
//        return rcm;
//    }
//
//    /**
//     * RedisTemplate配置
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);//如果key是String 需要配置一下StringSerializer,不然key会乱码 /XX/XX
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//        redisConnectionFactory.setHostName(host);
//        redisConnectionFactory.setPort(port);
//        return redisConnectionFactory;
//    }
//
//}
//
