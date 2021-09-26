package org.lsw.compute.spark.app.stream

import org.apache.spark.internal.Logging
import org.lsw.compute.spark.api.data.connector.sink.Mysql
import org.lsw.compute.spark.api.data.connector.source.Socket
import org.lsw.compute.spark.api.transform.impl.RenameTransform
import org.lsw.compute.spark.util.SparkUtil

import scala.collection.mutable

object StreamTest extends Logging {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.initSpark
    val url = "jdbc:mysql://localhost:3306/scistorTest?useSSL=false"
    val user = "root"
    val password = "root"
    val config = mutable.HashMap[String, Any]("url" -> url,
      "user" -> user,
      "password" -> password,
      "dbtable" -> "spark_in",
      "option" -> Map.empty[String, String],
      "autoCreate" -> false)

    val mySource = new Socket
    val myTransform = new RenameTransform()
    val mysqlSink = new Mysql
    myTransform.setInput(mySource)
    mysqlSink.setConfig(config.toMap)
    mysqlSink.setInput(myTransform)
    mysqlSink.save().left.get.awaitTermination()
  }
}
