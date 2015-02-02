package bigLebowski

import akka.actor.ActorSystem
import akka.actor.Props
import bigLebowski.actors.BenGazzara
import bigLebowski.actors.JeffBridges
import akka.actor.Actor
import movie.Role

object BigLebowski extends App {
  val jackie = new Role("Jackie Treehorn")
  val dude = new Role("The Dude")

  implicit val system = ActorSystem("the-stage")

  val gazzara = system.actorOf(Props[BenGazzara])
  val bridges = system.actorOf(Props[JeffBridges])

  val script = movie.Script("BigLebowski-Scene-1",
    Map(gazzara -> jackie, bridges -> dude), (gazzara, "Interactive erotic software. The wave of the future, Dude. " +
      "One hundred percent electronic!"),
    (bridges, "Yeah well, I still jerk off manually."))

  system.actorSelection("/user/*") ! script
}
