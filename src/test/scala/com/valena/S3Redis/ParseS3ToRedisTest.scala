package com.valena.S3Redis

import awscala.Region
import awscala.s3.{Bucket, S3}
import com.amazonaws.auth.{AWSStaticCredentialsProvider, AnonymousAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.{AmazonS3, AmazonS3Builder, AmazonS3Client, AmazonS3ClientBuilder}
import com.github.sebruck.EmbeddedRedis
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll
import redis.embedded.RedisServer
import io.findify.s3mock.S3Mock
import scala.jdk.CollectionConverters._


// Test ignored until embedded RedisTimeSeries found
class ParseS3ToRedisTest extends AnyFunSuite with EmbeddedRedis with BeforeAndAfterAll {

  var redis: RedisServer = null
  var redisPort: Int = 0
  var redisHost: String = "localhost"
  var api: S3Mock = null
  var client: S3 = null
  val TargetBucketName: String = "valena1databucket"
  val BucketPrefix: String = "database/raw/"


  override def beforeAll(): Unit = {
    redis = startRedis()
    redisPort = redis.ports().get(0)
    val api = S3Mock(port = 8001, dir = "/tmp/s3")
    api.start
    val endpoint = new EndpointConfiguration("http://localhost:8001", "us-east-2")
    val client = AmazonS3ClientBuilder
      .standard
      .withPathStyleAccessEnabled(true)
      .withEndpointConfiguration(endpoint)
      .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
      .build
    client.createBucket(TargetBucketName)
  }

  ignore("Write S3 json to redis") {

    // Init S3
    implicit val s3: S3 = client
    val bucket: com.amazonaws.services.s3.model.Bucket = s3.listBuckets.get(0)
    val TargetBucket = bucket

    // Init RedisConnector
    implicit val redisConnector: RedisConnector = new RedisConnector(redisHost, redisPort)

    // Start Pipeline
    val pipe = new ParseS3ToRedis
    // Test if write to redis fails, due to lack of time series
    intercept["java.lang.RuntimeException"]{

    }

  }

  override def afterAll(): Unit  = {
    stopRedis(redis)
    api.stop
  }

}
