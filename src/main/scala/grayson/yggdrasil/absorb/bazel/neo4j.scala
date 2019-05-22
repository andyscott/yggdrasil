package grayson.yggdrasil
package absorb.bazel

import prelude._

import cats.effect.IO

import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.graphdb.GraphDatabaseService
import java.io.File

// TODO: move under yddrasil.???

trait Neo4j[F[_]] {
  def shutdown(): F[Unit]
}

object Neo4J {
  def startEmbedded(dir: File): IO[Neo4j[IO]] =
    IO(Embedded(new GraphDatabaseFactory().newEmbeddedDatabase(dir)))

  final case class Embedded(g: GraphDatabaseService) extends Neo4j[IO] {
    def shutdown(): IO[Unit] =
      IO(g.shutdown())
  }
}
