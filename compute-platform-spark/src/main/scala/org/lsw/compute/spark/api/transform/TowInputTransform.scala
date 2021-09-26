package org.lsw.compute.spark.api.transform

import org.apache.spark.sql.Dataset
import org.lsw.compute.spark.api.{Plugin, TransformBase}

/**
 * 双输入的转换逻辑。
 *
 * @tparam LEFT  左边的输入
 * @tparam RIGHT 右边的输入
 * @tparam OUT   输出的数据类型
 */
abstract class TowInputTransform[LEFT, RIGHT, OUT] extends TransformBase[OUT] {

  private var left: TransformBase[LEFT] = _
  private var right: TransformBase[RIGHT] = _

  def setInput(left: TransformBase[LEFT], right: TransformBase[RIGHT]): Unit = {
    this.left = left
    this.right = right
  }


  /**
   * 得到Dataset[OUT]的实现。
   */
  override private[api] def transform():Dataset[OUT] = {
    val leftData = left.transform()
    val rightData = right.transform()
    if (leftData.isStreaming || rightData.isStreaming) {
      batchTransform(leftData, rightData)
    } else {
      streamTransform(leftData, rightData)
    }
  }

  /**
   * 批处理的转换逻辑
   */
  protected def batchTransform(leftData: Dataset[LEFT], rightData: Dataset[RIGHT]): Dataset[OUT]

  /**
   * 流处理的转换逻辑，一般情况下与批处理一致即可。
   *
   * note: 不能转换为rdd，或进行foreach, foreachPartition这类操作
   */
  protected def streamTransform(leftData: Dataset[LEFT], rightData: Dataset[RIGHT]): Dataset[OUT] = batchTransform(leftData, rightData)
}
