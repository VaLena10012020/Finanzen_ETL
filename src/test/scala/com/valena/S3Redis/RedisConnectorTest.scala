package com.valena.S3Redis

import com.github.sebruck.EmbeddedRedis
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll
import redis.embedded.RedisServer


// Test ignored until embedded RedisTimeSeries found
class RedisConnectorTest extends AnyFunSuite with EmbeddedRedis with BeforeAndAfterAll {

  var redis: RedisServer = null
  var redisPort: Int = 0
  var redisHost: String = "localhost"

  override def beforeAll(): Unit = {
    redis = startRedis()
    redisPort = redis.ports().get(0)
  }

  ignore("Write Map to redis") {
    val FileContent: Map[String, Any] = Map("test" -> 123, "date" -> 456)
    val redisConnector: RedisConnector = new RedisConnector(redisHost, redisPort)
    // Test if write to redis fails, due to lack of time series
    intercept["redis.clients.jedis.exceptions.JedisDataException"]{
      redisConnector.parseMapToRedis(FileContent)
    }
  }

  override def afterAll(): Unit  = {
    stopRedis(redis)
  }

}
