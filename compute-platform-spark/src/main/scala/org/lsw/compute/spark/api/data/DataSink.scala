package org.lsw.compute.spark.api.data

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.StreamingQuery
import org.lsw.compute.spark.api.{Plugin, TransformBase}

/**
 * 输出的抽象
 *
 * @tparam IN
 */
abstract class DataSink[IN] extends Plugin[Unit] {

  protected var input: TransformBase[IN] = _

  def setInput(input: TransformBase[IN]): Unit = {
    this.input = input
  }

  /**
   * 一般输出实现
   */
  def save(): Either[StreamingQuery, Unit] = {

    if (this.input == null) {
      return Right()
    }

    if (config("autoCreate").toString.equals("true")) {
      createSinkIfNotExists()
    }
    val inData = this.input.transform()
    if (this.input.transform().isStreaming) {
      Left(streamSave(inData))
    } else {
      // We don't have to worry about batch output
      Right(batchSave(inData))
    }
  }

  /**
   * 批量dataframe的输出
   */
  private def batchSave(inData: Dataset[IN]): Unit = {

    config("saveMode").toString.toLowerCase match {
      case "overwrite" => BatchOverwrite(inData)
      case "append" => BatchAppend(inData)
      case other => throw new Exception(s"Unsupported types: $other")
    }
  }

  /**
   * 覆盖写
   */
  protected def BatchOverwrite(inData: Dataset[IN]): Unit

  /**
   * 增量写
   */
  protected def BatchAppend(inData: Dataset[IN]): Unit

  /**
   * 不存在则创建。
   */
  protected def createSinkIfNotExists(): Unit

  /**
   * 流式任务中的输出实现
   */
  protected def streamSave(inData: Dataset[IN]): StreamingQuery


}
