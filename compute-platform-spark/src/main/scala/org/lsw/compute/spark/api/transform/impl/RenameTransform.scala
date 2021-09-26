package org.lsw.compute.spark.api.transform.impl

import org.apache.spark.sql.{Dataset, Row}
import org.lsw.compute.spark.api.transform.OneInputTransform

class RenameTransform extends OneInputTransform[Row,Row]{
  /**
   * 批处理的转换逻辑
   *
   * @param inputData 输入数据
   */
  override protected def batchTransform(inputData: Dataset[Row]): Dataset[Row] = {
    inputData.withColumnRenamed("value","info")
  }
}
