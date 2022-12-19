package com.cache.redis
import redis.clients.jedis.{HostAndPort, Jedis, JedisCluster, JedisPool}

import java.util
import scala.util.{Failure, Success, Try}

object CacheBlackListedIPAddresses {
  var pool = new JedisPool("localhost", 6379)

  def getJedisResource : Jedis = {
    Try(pool.getResource) match {
      case Success(jedis) => jedis
      case Failure(exception) =>
        println("Failed to find resource in pool")
        throw exception
    }
  }

  def getJedisCluster : JedisCluster = {
    val hostAndPortNodes = new util.HashSet[HostAndPort]
    hostAndPortNodes.add(new HostAndPort("localhost", 7000))
    hostAndPortNodes.add(new HostAndPort("localhost", 7001))
    // Running of 3 master nodes is MANDATORY in redis cluster.
    // Example:% redis-cli -p 7000 cluster nodes
    //498c75cadd638f9db8f8d0ac01f45388c89734ae ::1:7001@17001 master - 0 1671456426160 2 connected 5962-10922
    //12b5e88b45b0ba580ffd4d835f069da2694d0512 ::1:7002@17002 master - 0 1671456425554 3 connected 11422-16383
    //7f493f6218a98cd888e2fb4712fd987396dcc3dc ::1:7000@17000 myself,master - 0 1671456424000 7 connected 0-5961 10923-11421
    //0e29128020ff704deadd4e0e663e4c50215b946b ::1:7004@17004 slave,fail 7f493f6218a98cd888e2fb4712fd987396dcc3dc 1671456198159 1671456195621 7 disconnected
    //cb0ba3cbabff59d0852a5dfce27732cb07aef782 ::1:7005@17005 slave,fail 498c75cadd638f9db8f8d0ac01f45388c89734ae 1671456198159 1671456195621 2 disconnected
    //4f51bd2c900cd9536b7b44074fa6d9ae0d3f803c ::1:7003@17003 slave,fail 12b5e88b45b0ba580ffd4d835f069da2694d0512 1671456197551 1671456195621 3 disconnected
    new JedisCluster(hostAndPortNodes)
  }
}
