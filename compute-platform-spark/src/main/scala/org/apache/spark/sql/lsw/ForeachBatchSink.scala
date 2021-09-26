package org.apache.spark.sql.lsw

import org.apache.spark.sql.execution.LogicalRDD
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.{DataFrame, Dataset}

/**
 * 2.3.x only. when lib is 2.4.x+, This feature is built in
 *
 * @param batchWriter func that can use dataFrame.write
 */
case class ForeachBatchSink(batchWriter: (DataFrame, Long) => Unit)
  extends Sink {

  override def addBatch(batchId: Long, data: DataFrame): Unit = {
    val rdd = data.queryExecution.toRdd
    val schema = data.schema
    val spark = data.sparkSession
    val logicalPlan = LogicalRDD(schema.toAttributes, rdd)(spark)
    val ds = Dataset.ofRows(spark, logicalPlan)
    batchWriter(ds, batchId)
  }

  override def toString: String = "ForeachBatchSink"
}