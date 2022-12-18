package com.cache.redis
import org.scalatest.FunSuite

class CacheBlackListedIPAddressesTest extends FunSuite {
  test("Add IP to redis set and check its existence - using sadd and sismember") {
    val jedis = CacheBlackListedIPAddresses.getJedisCluster
    val IPAddress01 = "197.12.12.3"
    val IPAddress02 = "197.12.12.10"
    jedis.set("ip", IPAddress01)
//    jedis.sadd("ipAddress_blocked", IPAddress01)
//    jedis.sadd("ipAddress_blocked", IPAddress02)
//    assert(jedis.sismember("ipAddress_blocked", IPAddress01))
//    assert(jedis.sismember("ipAddress_blocked", IPAddress02))
  }
}
