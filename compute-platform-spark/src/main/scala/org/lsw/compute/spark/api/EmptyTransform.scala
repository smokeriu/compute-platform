package org.lsw.compute.spark.api

import org.apache.spark.sql.{Dataset, Row}
import org.lsw.compute.spark.util.SparkUtil

/**
 * An empty transform instance to avoid exception
 *
 */
class EmptyTransform extends TransformBase[Row] {
  /**
   * 得到Dataset[OUT]的实现。
   * 这个方法应该仅在sink时被调用。
   */
  override private[api] def transform(): Dataset[Row] = {
    SparkUtil.getSpark.emptyDataFrame
  }
}
