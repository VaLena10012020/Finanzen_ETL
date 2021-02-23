package com.valena.S3Redis

import com.github.sebruck.EmbeddedRedis
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll
import redis.embedded.RedisServer


// Test ignored until embedded RedisTimeSeries found
class RedisConnectorTest extends AnyFunSuite with EmbeddedRedis with BeforeAndAfterAll {

  var redis: RedisServer = null
  var redisPort: Int = 6379

  override def beforeAll(): Unit = {
    redis = startRedis(port = redisPort) // A random free port is chosen
  }

  ignore("Write Map to redis") {
    val FileContent: Map[String, Any] = Map("test" -> 123, "date" -> 456)
    val redisConnector: RedisConnector = new RedisConnector
    redisConnector.parseMapToRedis(FileContent)
    assert(redisConnector.RedisConnector.get("test").toString.toInt == 123)
  }

  override def afterAll(): Unit  = {
    stopRedis(redis)
  }

}
