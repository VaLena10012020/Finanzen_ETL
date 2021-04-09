package com.valena.S3Redis

import awscala.s3.{Bucket, S3, S3Object}
import com.amazonaws.services.s3.model.S3ObjectInputStream

import java.io.{BufferedReader, InputStreamReader}

class ParseS3ToRedis {
  def loadS3redisPipeline(TargetBucket: Bucket, BucketPrefix: String)
                         (implicit s3: S3, redisConnector: RedisConnector): Unit = {
    for (file <- TargetBucket.keys(BucketPrefix) if file.endsWith(".json")) {
      // Get file input stream
      val DataStream: S3ObjectInputStream = TargetBucket.getObject(file).get.getObjectContent
      val InputStreamReader: BufferedReader = new BufferedReader(new InputStreamReader(DataStream))

      // Get content and parse to Map Object
      val FileContent: Map[String, Any] = jsonStrToMap.parseJson(InputStreamReader.readLine())
      redisConnector.parseMapToRedis(FileContent)

      // TODO: Build logging schema
      println(s"Parsed $file with content $FileContent")
    }
  }
}

