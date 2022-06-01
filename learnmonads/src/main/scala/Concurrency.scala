import java.util.concurrent.Executors

object Concurrency extends App {
  // Thread Basics

//  val aThread = new Thread(() => {
//    //      Thread.sleep(10000)
//    println("Thread")
//  })
//
//  aThread.start()
//
//  aThread.join() // blocks until aThread finishes exexuting
//
//  val helloThread = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
//
//  val goodByeThread = new Thread(() => (1 to 5).foreach(_ => println("Bye")))
//
//  helloThread.start()
//  goodByeThread.start()
//
//
//  val pool = Executors.newFixedThreadPool(10)
//  pool.execute(() => println("In parallel"))
//  //  pool.shutdown() // shutdown after the threads running are done
//  //  pool.shutdownNow() // interrupts the sleeping threads too
//  //
//  //  pool.execute(() => println("This throws error"))
//  println(pool.isShutdown) // tells if shutDown has been called



}
