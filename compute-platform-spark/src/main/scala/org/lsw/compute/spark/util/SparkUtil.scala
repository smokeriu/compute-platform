package org.lsw.compute.spark.util

import org.apache.spark.sql.SparkSession

object SparkUtil {

  lazy val getSpark: SparkSession = SparkSession.builder().getOrCreate()

  def initSpark: SparkSession = {
    val spark = SparkSession.builder().master("local[2]").getOrCreate()
    spark.conf.set("spark.sql.streaming.checkpointLocation", "file:///Users/liushengwei/IdeaProjects/compute-platform/compute-platform/chk")
    spark
  }
}
