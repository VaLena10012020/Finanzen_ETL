package S3Connector

import com.amazonaws.auth.{DefaultAWSCredentialsProviderChain}
import com.amazonaws.services.s3.AmazonS3Client

class ListS3Files {
  val target_bucket = "valena1databucket"
  val bucket_prefix = "database/raw/"

  val credentials = new DefaultAWSCredentialsProviderChain
  val amazonS3Client = new AmazonS3Client(credentials)

  val objects = amazonS3Client.listObjects(target_bucket, bucket_prefix)
  objects.toString
}
