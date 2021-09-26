package org.lsw.compute.spark.api.transform

import org.apache.spark.sql.Dataset
import org.lsw.compute.spark.api.TransformBase

/**
 * 单输入的转换逻辑
 *
 * @tparam IN  输入的数据类型
 * @tparam OUT 输出的数据类型
 */
abstract class OneInputTransform[IN, OUT] extends TransformBase[OUT] {

  private var inPlugin: TransformBase[IN] = _

  def setInput(inPlugin: TransformBase[IN]): Unit = {
    this.inPlugin = inPlugin
  }

  /**
   * 得到Dataset[OUT]的实现。
   */
  override private[api] def transform(): Dataset[OUT] = {
    val inData = this.inPlugin.transform()
    if (inData.isStreaming) {
      streamTransform(inData)
    } else {
      batchTransform(inData)
    }
  }

  /**
   * 批处理的转换逻辑
   *
   * @param inputData 输入数据
   */
  protected def batchTransform(inputData: Dataset[IN]): Dataset[OUT]

  /**
   * 流处理的转换逻辑，一般情况下与批处理一致即可。
   *
   * note: 不能转换为rdd，或进行foreach, foreachPartition这类操作
   *
   * @param inputData 输入数据
   */
  protected def streamTransform(inputData: Dataset[IN]): Dataset[OUT] = batchTransform(inputData)
}
