package com.valena.S3Redis

import com.redislabs.redistimeseries.RedisTimeSeries

case class RedisConnector() {

  val RedisConnector: RedisTimeSeries = new RedisTimeSeries("localhost",  6379)

  def parseMapToS3(MapFile: Map[String, Any]): Unit ={
    // Get Date of json and write all other keys to a redis time series
    val JsonDate = MapFile("date")
    //TODO: Is there a more elegant way?
    val vJsonDate: Long = JsonDate.toString.toLong

    for ((k,v) <- MapFile if k != "date") {
      //TODO: Is there a more elegant way?
      val vDouble: Double = v.toString.toDouble
      RedisConnector.add(k, vJsonDate, vDouble)
    }
  }
}