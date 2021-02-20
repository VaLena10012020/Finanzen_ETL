package S3Connector

import awscala._
import com.amazonaws.services.s3.model.S3ObjectInputStream
import s3._

import java.io.{BufferedReader, InputStreamReader}


class ListS3Files {
  val TargetBucketName: String = "valena1databucket"
  val BucketPrefix: String = "database/raw/"
  val S3Region: Region = Region.US_EAST_2

  implicit val s3: S3 = S3.at(S3Region)
  val TargetBucket: Bucket = s3.bucket(TargetBucketName).get

  // Get all json files in target
  for (file <- TargetBucket.keys(BucketPrefix) if file.endsWith(".json")) {
    println(file)
    // Get file input stream
    val S3JsonObject: S3Object = TargetBucket.getObject(file).get
    val DataStream: S3ObjectInputStream = S3JsonObject.getObjectContent
    val InputStream = new BufferedReader(new InputStreamReader(DataStream))
    // Get content and parse to Map Object
    val FileContent = jsonStrToMap.parseJson(InputStream.readLine())
    println(FileContent)
    // Get Date of json and write all other keys to a redis time series
    val JsonDate = FileContent("date")
    for ((k,v) <- FileContent if k != "date") {
      println(s"TS.ADD $k $JsonDate $v")
    }

  }

}
