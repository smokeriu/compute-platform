package org.lsw.compute.spark.api.data.connector.source

import java.util.Properties

import org.apache.spark.sql.{Dataset, Row}
import org.lsw.compute.spark.api.data.DataSource
import org.lsw.compute.spark.util.SparkUtil

abstract class JDBCSource extends DataSource[Row] {


  /**
   * 读取数据的实现
   */
  override protected def load(): Dataset[Row] = {
    val spark = SparkUtil.getSpark

    // init base config
    val options = config("options").asInstanceOf[Map[String, String]]
    val url = config("url").toString
    val user = config("user").toString
    val password = config("password").toString
    val partColumnName = config("partColumnName").toString
    // TODO: get executor-num and executor-core
    val partitionNum = spark.conf.get("").toInt * spark.conf.get("").toInt

    // build read table
    val table = config("table").toString
    val readTable = if (table.contains("select")) {
      val tb = s"( $table ) as tmp"
      logInfo(s"sql push down: $tb")
      tb
    } else {
      table
    }

    // build props
    val props = initProps(user: String, password: String, options: Map[String, String])

    // build predicates

    val predicates = initPredicates(partColumnName, partitionNum)
    spark.read.jdbc(url, readTable, predicates.toArray, props)
  }

  /**
   * init props
   *
   * @return props
   */
  protected def initProps(user: String, password: String, options: Map[String, String]): Properties


  /**
   * init predicates
   *
   * @return predicates Seq
   */
  protected def initPredicates(partColumnName: String, partitionNum: Int): Seq[String]
}
