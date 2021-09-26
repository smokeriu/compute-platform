package org.lsw.compute.spark.api

import org.apache.spark.internal.Logging

abstract class Plugin[T] extends Logging with Serializable {

  lazy val name: String = this.getClass.getName

  lazy val className: String = this.getClass.getCanonicalName

  protected var _config: Map[String, Any] = _

  def setConfig(config: Map[String, Any]): Unit = {
    _config = config
  }

  final def config: Map[String, Any] = _config

}
