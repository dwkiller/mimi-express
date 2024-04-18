package com.mimi.core.common.superpackage.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import redis.clients.jedis.JedisPoolConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig extends CachingConfigurerSupport{
	
	@Autowired
	private RedisConn redisConn;
	
	@Autowired
	private RedisPool redisPool;
	
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	
	}
	
	/**
	* 管理缓存
	*
	* @param redisTemplate
	* @return
	*/
	
//	@SuppressWarnings("rawtypes")
//	@Bean
//	public CacheManager CacheManager(RedisTemplate redisTemplate) {
//		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//		return rcm;
//	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//最大连接数
		jedisPoolConfig.setMaxTotal(redisPool.getMaxIdle());
		//最小空闲连接数
		jedisPoolConfig.setMinIdle(redisPool.getMinIdle());
		//当池内没有可用的连接时，最大等待时间
		jedisPoolConfig.setMaxWaitMillis(redisPool.getMaxWait());
		//------其他属性根据需要自行添加-------------
		return jedisPoolConfig;
	}

	/**
	* redis 数据库连接池
	* @return
	*/
	@Bean
	public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		RedisStandaloneConfiguration redisStandaloneConfiguration =
				new RedisStandaloneConfiguration();
		//设置redis服务器的host或者ip地址
		redisStandaloneConfiguration.setHostName(redisConn.getHost());
		//设置默认使用的数据库
		redisStandaloneConfiguration.setDatabase(0);
		//设置密码
		if(!StringUtils.isEmpty(redisConn.getPassword())){
			redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConn.getPassword()));
		}
		//设置redis的服务的端口号
		redisStandaloneConfiguration.setPort(redisConn.getPort());
		//获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
		//指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
		jpcb.poolConfig(jedisPoolConfig);
		//通过构造器来构造jedis客户端配置
		JedisClientConfiguration jedisClientConfiguration = jpcb.build();
		//单机配置 + 客户端配置 = jedis连接工厂
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	}
	
	/**
	* redisTemplate配置
	*
	* @param redisConnectionFactory
	* @return
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
