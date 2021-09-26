package org.lsw.compute.spark.api.data.connector.source

import java.util.Properties

import org.apache.commons.lang3.StringUtils

class Mysql extends JDBCSource {
  val driver = "com.mysql.cj.jdbc.Driver"

  /**
   * init props
   *
   * @return props
   */
  override protected def initProps(user: String, password: String, options: Map[String, String]): Properties = {
    val props = new Properties()
    props.setProperty("user", user)
    props.setProperty("password", password)
    props.setProperty("driver", driver)
    options.foreach(kv => props.setProperty(kv._1, kv._2))
    props
  }

  /**
   * init predicates
   *
   * @return predicates Seq
   */
  override protected def initPredicates(partColumnName: String, partitionNum: Int): Seq[String] = {

    if (!StringUtils.isEmpty(partColumnName)) {
      (0 until partitionNum).map(idx => s"SHA1($partColumnName)%$partitionNum = $idx")
    } else {
      Seq.empty[String]
    }
  }
}
