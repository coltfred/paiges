package org.typelevel.paiges

import cats.Cartesian
import cats.functor.Contravariant

trait CatsDocument {
  implicit val contravariantDocument: Contravariant[Document] =
    new Contravariant[Document] {
      def contramap[A, Z](d: Document[A])(f: Z => A): Document[Z] = d.contramap(f)
    }

  def cartesianDocument(sep: Doc): Cartesian[Document] =
    new Cartesian[Document] {
      def product[A, B](fa: Document[A], fb: Document[B]): Document[(A, B)] =
        Document.instance { case (a, b) =>
          fa.document(a) + sep + fb.document(b)
        }
    }
}

object CatsDocument extends CatsDocument