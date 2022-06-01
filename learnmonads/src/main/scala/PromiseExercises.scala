import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.concurrent.impl.Promise
import scala.util.{Failure, Success}

object PromiseExercises {

  val futureI: Future[Int] = Future {
    42
  }

  def InSeq[A, B](fa: Future[A], fb: Future[B]): Future[B] = {
    fa.flatMap(_ => fb)
  }

  def fout[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val p1 = Promise[A]()

    fa.onComplete {
      case Failure(exception) => p1.failure(exception)
      case Success(value) => p1.success(value)
    }

    fb.onComplete {
      case Failure(exception) => p1.failure(exception)
      case Success(value) => p1.success(value)
    }

    p1.future
  }

}
