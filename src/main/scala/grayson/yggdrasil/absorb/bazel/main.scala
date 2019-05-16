package grayson.yggdrasil
package absorb.bazel

import scala.collection.JavaConverters._
import java.io.InputStream
import java.io.FileInputStream

import com.google.devtools.build.lib.query2.proto.proto2api.Build.QueryResult

object Main {
  def main(args: Array[String]): Unit = {
    val is: InputStream = new FileInputStream(args(0))
    val res = QueryResult.parseFrom(is)
    res.getTargetList.asScala.foreach { item =>
      println(item)
    }
    is.close()
  }
}
