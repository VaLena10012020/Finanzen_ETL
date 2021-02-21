import S3Connector.{ParseS3ToRedis, RedisConnector}
import awscala.Region
import awscala.s3.{Bucket, S3}

object main extends App{
  // TODO: Load as external parameters
  val TargetBucketName: String = "valena1databucket"
  val BucketPrefix: String = "database/raw/"
  val S3Region: Region = Region.US_EAST_2

  // Init S3
  implicit val s3: S3 = S3.at(S3Region)
  val TargetBucket: Bucket = s3.bucket(TargetBucketName).get

  // Init RedisConnector
  implicit val redisConnector: RedisConnector = new RedisConnector

  // Start Pipeline
  val pipe = new ParseS3ToRedis
  pipe.loadS3redisPipeline(TargetBucket, BucketPrefix)
}
