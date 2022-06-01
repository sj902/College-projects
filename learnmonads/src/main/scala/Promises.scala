import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

object Promises extends App {
  private val promise = Promise[Int]()
  val future: Future[Int] = promise.future

  future.onComplete {
    case Failure(exception) => println(exception)
    case Success(value) => println(value)
  }

  val prod = new Thread(() => promise.success(42))

  prod.start()

  Thread.sleep(1000)
}
