package org.lsw.compute.spark.api.data.connector.sink

import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.lsw.ForeachBatchSink
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.{OutputMode, StreamingQuery}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SQLContext}
import org.lsw.compute.spark.api.data.DataSink

abstract class JDBCSink extends DataSink[Row] {

  /**
   * 流式任务中的输出实现
   */
  override protected def streamSave(inData: Dataset[Row]): StreamingQuery = {
    inData.writeStream
      .options(config.map(kv => (kv._1, kv._2.toString)))
      .format("org.lsw.compute.spark.api.data.connector.sink.JDBCSink.StreamJDBCSink")
      .start()
  }

  private class StreamJDBCSink extends StreamSinkProvider {
    override def createSink(sqlContext: SQLContext, parameters: Map[String, String], partitionColumns: Seq[String], outputMode: OutputMode): Sink = {
      new JDBCBatchWriter(parameters).sink
    }

    private class JDBCBatchWriter(parameters: Map[String, String]) extends Serializable {
      private val writer = (df: DataFrame, id: Long) => {
        df.write
          .format("jdbc")
          .mode("append")
          .options(parameters)
          .save()
      }

      val sink: Sink = ForeachBatchSink(writer)
    }
  }

}

