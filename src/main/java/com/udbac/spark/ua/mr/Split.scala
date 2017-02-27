package com.udbac.spark.ua.mr

import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * Created by root on 2017/2/27.
  */
class RDDMultipleTextOutputFormat extends MultipleTextOutputFormat[Any, Any] {
  override def generateFileNameForKeyValue(key: Any, value: Any, name: String): String = key.asInstanceOf[String]
}


object Split {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Split Test").setMaster("local")
    val sc = new SparkContext(conf)

    sc.parallelize(List(("w", "www"), ("b", "blog"), ("c", "com"), ("w", "bt")))
      .map(value => (value._1, value._2 + "Test"))
      .partitionBy(new HashPartitioner(3))
      .saveAsHadoopFile("D:\\UDBAC\\LEARN_SPARK\\data\\iteblog", classOf[String], classOf[String], classOf[RDDMultipleTextOutputFormat])
    sc.stop()
  }

}
