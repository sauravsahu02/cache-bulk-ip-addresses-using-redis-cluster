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
    new JedisCluster(hostAndPortNodes)
  }
}
