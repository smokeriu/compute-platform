package org.lsw.compute.spark.api.data.connector.source

import org.apache.spark.sql.{Dataset, Row}
import org.lsw.compute.spark.api.data.DataSource
import org.lsw.compute.spark.util.SparkUtil

class Socket extends DataSource[Row] {
  /**
   * 读取数据的实现
   */
  override protected def load(): Dataset[Row] = {
    val spark = SparkUtil.getSpark
    spark.readStream
      .format("socket")
      .option("host", "cdscistor01")
      .option("port", 9999)
      .load()
  }
}
