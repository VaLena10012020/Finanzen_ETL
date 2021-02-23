package com.valena.S3Redis

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

/*
Inspired by
https://stackoverflow.com/questions/29908297/how-can-i-convert-a-json-string-to-a-scala-map
*/

object jsonStrToMap {
  def parseJson(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats

    parse(jsonStr).extract[Map[String, Any]]
  }

}
