package S3Connector

import awscala._
import com.amazonaws.services.s3.model.S3ObjectInputStream
import s3._

import java.io.{BufferedReader, InputStreamReader}
import com.redislabs.redistimeseries._

class ListS3Files {
  // TODO: Load as external parameters
  val TargetBucketName: String = "valena1databucket"
  val BucketPrefix: String = "database/raw/"
  val S3Region: Region = Region.US_EAST_2

  // Init S3
  implicit val s3: S3 = S3.at(S3Region)
  val TargetBucket: Bucket = s3.bucket(TargetBucketName).get

  // Connect to redis
  val RedisConnector: RedisTimeSeries = new RedisTimeSeries("localhost", 6379)

  // Get all json files in target
  for (file <- TargetBucket.keys(BucketPrefix) if file.endsWith(".json")) {

    // Get file input stream
    val S3JsonObject: S3Object = TargetBucket.getObject(file).get
    val DataStream: S3ObjectInputStream = S3JsonObject.getObjectContent
    val InputStream = new BufferedReader(new InputStreamReader(DataStream))
    // Get content and parse to Map Object
    val FileContent = jsonStrToMap.parseJson(InputStream.readLine())

    // Get Date of json and write all other keys to a redis time series
    val JsonDate = FileContent("date")
    //TODO: Is there a more elegant way?
    val vJsonDate: Long = JsonDate.toString.toLong

    for ((k,v) <- FileContent if k != "date") {
      //TODO: Is there a more elegant way?
      val vDouble: Double = v.toString.toDouble

      RedisConnector.add(k, vJsonDate, vDouble)
    }
    println(s"Pared $file with content $FileContent")
  }

}
