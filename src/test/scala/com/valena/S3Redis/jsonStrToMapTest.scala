package com.valena.S3Redis

import org.scalatest.funsuite.AnyFunSuite

class jsonStrToMapTest extends AnyFunSuite {
  test("An simple json should be parsed") {
    val TestJson = """{"test":123}"""
    val result = jsonStrToMap.parseJson(TestJson)
    assert(result == Map("test" -> 123))
  }
  test("A nested json should be parsed") {
    val TestJson = """{"test":123, "abc":{"def": 456}}"""
    val result = jsonStrToMap.parseJson(TestJson)
    assert(result == Map("test" -> 123, "abc" -> Map("def" -> 456)))
  }
  test("A wrong json should raise Error") {
    val TestJson = """{"test":123"""
    intercept[com.fasterxml.jackson.core.io.JsonEOFException]{
      jsonStrToMap.parseJson(TestJson)
    }
  }
}
