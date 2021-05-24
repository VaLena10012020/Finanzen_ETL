package com.valena.S3Redis

import com.redislabs.redistimeseries.RedisTimeSeries

case class RedisConnector(host: String, port: Int) {
  println("Init redis connector - start")
  val RedisConnector: RedisTimeSeries = new RedisTimeSeries(host,  port)
  println("Init redis connector - finished")
  def parseMapToRedis(MapFile: Map[String, Any]): Unit ={
    // Get Date of json and write all other keys to a redis time series
    // TODO: Check for 'date' key in Map and throw exception if not found
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