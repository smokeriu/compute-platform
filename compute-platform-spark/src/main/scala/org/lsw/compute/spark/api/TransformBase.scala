package org.lsw.compute.spark.api

import org.apache.spark.sql.Dataset

/**
 * 所有会产生输出的都应该继承该类。包括读取，转换。
 *
 * @tparam OUT 输出的数据类型
 */
abstract class TransformBase[OUT] extends Plugin[OUT] {

  /**
   * 得到Dataset[OUT]的实现。
   * 这个方法应该仅在sink时被调用。
   */
  private[api] def transform(): Dataset[OUT]
}
