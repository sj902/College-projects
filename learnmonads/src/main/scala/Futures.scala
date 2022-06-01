import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object Futures extends App {

  def cal: Int = {
      Thread.sleep(1000)
    42
  }

  val res = Future {
    cal
  }

  Await.result(res, 3.seconds)

  println(res)

  //  Thread.sleep(3000)
}
