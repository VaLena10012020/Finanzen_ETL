package com.valena.S3Redis

import awscala.Region
import awscala.s3.{Bucket, S3}

object main extends App{
  // TODO: Load as external parameters
  val TargetBucketName: String = "valena1databucket"
  val BucketPrefix: String = "database/raw/"
  val S3Region: Region = Region.US_EAST_2
  val RedisPort: Int = sys.env.get("redis_port").getOrElse("6379").toInt
  val RedisHost: String = sys.env.get("redis_host").getOrElse("localhost")
  println(s"Got Variables for run: RedisHost=$RedisHost, RedisPort=$RedisPort")

  // Init S3
  implicit val s3: S3 = S3.at(S3Region)
  val TargetBucket: Bucket = s3.bucket(TargetBucketName).get

  // Init RedisConnector
  implicit val redisConnector: RedisConnector = RedisConnector(RedisHost, RedisPort)

  // Start Pipeline
  val pipe = new ParseS3ToRedis
  pipe.loadS3redisPipeline(TargetBucket, BucketPrefix)
}
