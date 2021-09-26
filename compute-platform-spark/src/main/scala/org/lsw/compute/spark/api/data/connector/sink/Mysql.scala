package org.lsw.compute.spark.api.data.connector.sink

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{Dataset, Row}

class Mysql extends JDBCSink {

  private val driver = "com.mysql.cj.jdbc.Driver"

  /**
   * 覆盖写
   */
  override protected def BatchOverwrite(inData: Dataset[Row]): Unit = ???

  /**
   * 增量写
   */
  override protected def BatchAppend(inData: Dataset[Row]): Unit = ???

  /**
   * 不存在则创建。
   */
  override protected def createSinkIfNotExists(): Unit = ???

}
