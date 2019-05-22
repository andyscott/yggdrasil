package grayson.yggdrasil
package prelude

import cats.Applicative
import cats.ApplicativeError

import cats.effect.IO

object `package` extends {}
    with cats.instances.EitherInstances
    with cats.instances.ListInstances
    with cats.syntax.TraverseSyntax
    with cats.syntax.EitherSyntax
{
  def printlnIO(msg: Any): IO[Unit] = IO(Console.println(msg))
}
