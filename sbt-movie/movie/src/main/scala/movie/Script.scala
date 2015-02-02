package movie

import akka.actor.ActorRef
import akka.actor.Actor

case class Role(val character: String) {
  require(character != null)

  override def toString = character
}

object Script {
  type Line = Tuple2[ActorRef, String]
  type Scene = Seq[Line]

  def apply(scene: String, roles: Map[ActorRef, Role], lines: Line*): Script = {
    new Script(scene, roles, lines)
  }
}

class Script private (val scene: String, val roles: Map[ActorRef, Role], val lines: Script.Scene) {}
case class Action(val it: BufferedIterator[Script.Line]) {}

class MovieActor(val name: String) extends Actor {
  import context._

  require(name != null)
  private var character: Option[Role] = None

  def receive = {
    case s: Script => {
      val role = s.roles(self)
      println(s"${name} got script for scene '${s.scene}'; his role is '${role.character}'")
      role match {
        case role: Role =>
          character = Some(role)
          val line = s.lines.head
          if (line._1 == self) {
            self ! new Action(s.lines.iterator.buffered)
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
      action.it.head._1 ! action
    } else {
      system.shutdown()
    }
  }
}