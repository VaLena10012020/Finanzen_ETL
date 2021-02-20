package S3Connector

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

object jsonStrToMap {
  def parseJson(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats

    parse(jsonStr).extract[Map[String, Any]]
  }

}
