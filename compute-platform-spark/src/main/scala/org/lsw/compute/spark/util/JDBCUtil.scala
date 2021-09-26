package org.lsw.compute.spark.util

import java.util.Properties

import org.apache.spark.internal.Logging

object JDBCUtil extends Logging {
  def initProps(url: String, user: String, password: String, driver: String, table: String): Properties = {
    val options = Map[String, String]("url" -> url,
      "user" -> user,
      "password" -> password,
      "driver" -> driver,
      "table" -> table)
    initProps(options)
  }

  def initProps(options: Map[String, String]): Properties = {
    val props = new Properties()
    options.foreach(kv => props.setProperty(kv._1, kv._2))
    props
  }
}
