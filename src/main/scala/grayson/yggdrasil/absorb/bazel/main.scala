package grayson.yggdrasil
package absorb.bazel

import prelude._

import scala.collection.JavaConverters._
import java.io.FileInputStream

import cats.effect.IO
import cats.effect.Resource

import com.google.devtools.build.lib.query2.proto.proto2api.Build

sealed trait DecodeError
object DecodeError {
  final case class UnknownTargetType(bt: Build.Target) extends DecodeError
}

object Main {
  def main(args: Array[String]): Unit = {
    val protoIO: IO[Build.QueryResult] =
      Resource
        .fromAutoCloseable(IO(new FileInputStream(args(0))))
        .use(is => IO(Build.QueryResult.parseFrom(is)))

    val io = for {
      proto <- protoIO
      result = proto
                 .getTargetList.asScala.toList
                 .traverse(decodeTarget(_).toValidatedNel)
                 .toEither
      _     <- result.fold(
                 _.traverse(e => printlnIO(s"Error! $e")),
                 _.traverse(t => printlnIO(s"> $t")))
    } yield ()

    io.unsafeRunSync()
  }

  def decodeTarget(bt: Build.Target): Either[DecodeError, Target] = bt.getType match {
    case Build.Target.Discriminator.RULE => decodeRule(bt.getRule)
    case Build.Target.Discriminator.SOURCE_FILE => Right(Target.SourceFile())
    case Build.Target.Discriminator.GENERATED_FILE => Right(Target.GeneratedFile())
    case Build.Target.Discriminator.PACKAGE_GROUP => Right(Target.PackageGroup())
    case Build.Target.Discriminator.ENVIRONMENT_GROUP => Right(Target.EnvironmentGroup())
    case _ => Left(DecodeError.UnknownTargetType(bt))
  }

  def decodeRule(r: Build.Rule): Either[DecodeError, Target] =
    Right(Target.Rule(
      name = r.getName,
      ruleClass = r.getRuleClass,
      location = r.getLocation,
      inputs = r.getRuleInputList.asScala.toList,
      outputs = r.getRuleOutputList.asScala.toList
    ))
}


sealed trait Target {
  final def widen: Target = this
}

object Target {
  final case class Rule(
    name: String,
    ruleClass: String,
    location: String,
    inputs: List[String],
    outputs: List[String],
  ) extends Target

  final case class SourceFile() extends Target
  final case class GeneratedFile() extends Target
  final case class PackageGroup() extends Target
  final case class EnvironmentGroup() extends Target
}
