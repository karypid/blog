package movie

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.actorRef2Scala

object Movie {
  case class Role(val character: String) {
    require(character != null)
    override def toString = character
  }

  type Line = Tuple2[Role, String]
  type Scene = Seq[Line]

  class Action(val it: BufferedIterator[Line]) {}

  class Script private (val scene: String, val roles: Map[ActorRef, Role], val lines: Scene) {}

  object Script {
    def apply(scene: String, roles: Map[ActorRef, Role], lines: Line*): Script = {
      new Script(scene, roles, lines)
    }
  }
}

class MovieActor(val name: String) extends Actor {
  import context._
  import Movie._

  require(name != null)
  private var character: Option[Role] = None
  private var roleActors = Map[Role, ActorRef]()

  def receive = {
    case s: Script => {
      val role = s.roles(self)
      roleActors = s.roles.map(_.swap)
      println(s"${name} got script for scene '${s.scene}'; his role is '${role.character}'")
      role match {
        case role: Role =>
          character = Some(role)
          val line = s.lines.head
          if (roleActors(line._1) == self) {
            val it = s.lines.iterator
            self ! new Action(it.buffered)
          }
      }
    }

    case action: Action => {
      speak(action)
    }
  }

  def speak(action: Action): Unit = {
    val line = action.it.next()
    println(s"${character.get}: ${line._2}")
    if (action.it.hasNext) {
      val actor = roleActors(action.it.head._1)
      actor ! action
    } else {
      system.shutdown()
    }
  }
}
