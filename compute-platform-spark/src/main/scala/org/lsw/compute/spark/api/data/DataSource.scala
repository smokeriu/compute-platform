package org.lsw.compute.spark.api.data

import org.apache.spark.sql.Dataset
import org.lsw.compute.spark.api.TransformBase

/**
 * 数据源抽象
 *
 * @tparam OUT 输出的数据类型
 */
abstract class DataSource[OUT] extends TransformBase[OUT] {


  /**
   * 得到Dataset[OUT]的实现。
   */
  override private[api] def transform(): Dataset[OUT] = {
    load()
  }

  /**
   * 读取数据的实现
   */
  protected def load(): Dataset[OUT]
}
