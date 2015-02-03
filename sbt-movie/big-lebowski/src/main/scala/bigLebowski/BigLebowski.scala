package bigLebowski

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor
import movie.Movie
import bigLebowski.actors.BenGazzara
import bigLebowski.actors.JeffBridges

object BigLebowski extends App {
  implicit val system = ActorSystem("the-stage")
  // our actors
  val gazzara = system.actorOf(Props[BenGazzara])
  val bridges = system.actorOf(Props[JeffBridges])

  // the roles
  val jackie = new Movie.Role("Jackie Treehorn")
  val dude = new Movie.Role("The Dude")

  // the script for our scene
  val script = Movie.Script("BigLebowski-Scene-1",
    // gazzara plays jackie, and bridges plays "the dude"
    Map(gazzara -> jackie, bridges -> dude),
    // here's the scene's dialogue:
    (jackie, "Interactive erotic software. The wave of the future, Dude. " +
      "One hundred percent electronic!"),
    (dude, "Yeah well, I still jerk off manually."))

  // director distributes script, and... Action!
  system.actorSelection("/user/*") ! script
}
