package com.cache.redis
import org.scalatest.FunSuite
import redis.clients.jedis.JedisCluster

import java.util

class CacheBlackListedIPAddressesTest extends FunSuite {
  val jedis: JedisCluster = CacheBlackListedIPAddresses.getJedisCluster
  test("Add IP to redis set and check its existence - using sadd and sismember") {
    val IPAddress01 = "197.12.12.3"
    val IPAddress02 = "197.12.12.10"
    jedis.set("ip", IPAddress01)
    assert(jedis.get("ip") == IPAddress01)

    jedis.sadd("ipAddress_blocked", IPAddress01)
    jedis.sadd("ipAddress_blocked", IPAddress02)

    assert(jedis.sismember("ipAddress_blocked", IPAddress01))
    assert(jedis.sismember("ipAddress_blocked", IPAddress02))
    assert(!jedis.sismember("ipAddress_blocked", IPAddress02 + "0000"))
  }
  test ("Redis data types check - String") {
    jedis.set("redis", "remote data structure server")
    jedis.set("year", "2022")
    jedis.incrBy("year", 1)
    assert(jedis.exists("redis"))
    assert(jedis.get("redis").equals("remote data structure server"))
    assert(jedis.get("year") == "2023")
  }
  test ("Redis data types check - Hashes") {
    val empDetailsMap = new util.HashMap[String, String]
    empDetailsMap.put("firstName", "Bob")
    empDetailsMap.put("lastName", "Jen")
    jedis.hmset("employee01", empDetailsMap)
    assert(jedis.hmget("employee01", "firstName").get(0).equals("Bob"))
    assert(jedis.hmget("employee01", "lastName").get(0).equals("Jen"))
    assert(jedis.hexists("employee01", "lastName"))
    assert(!jedis.hexists("employee01", "age"))
    assert(jedis.hgetAll("employee01").size() == 2)
  }
  test ("Redis data types check - Lists") {
    // List as FIFO
    jedis.lpush("work:queue:ids", "101")
    jedis.lpush("work:queue:ids", "102")
    jedis.lrange("work:queue:ids", 0, -1) //Array("101", "102"))
    assert(jedis.rpop("work:queue:ids") == "101")
    assert(jedis.rpop("work:queue:ids") == "102")
    // List as LIFO
    jedis.lpush("work:queue:ids", "101")
    jedis.lpush("work:queue:ids", "102")
    assert(jedis.lpop("work:queue:ids") == "102")
    assert(jedis.lpop("work:queue:ids") == "101")
  }
}
