package manik1.manik1

import java.nio.ByteBuffer

import scala.util.Success
import akka.http.scaladsl.server.Directives
import manik1.manik1.twirl.Implicits._
import scala.util.Failure

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}

class WebService() extends Directives {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val route = {
    pathSingleSlash {
      get {
        //complete(manik1.manik1.html.logear.render("Pantalla de loggeo"))
        complete(manik1.manik1.html.index.render(""))
      }
    } ~ pathPrefix("assets" / Remaining) { file =>
        // optionally compresses the response with Gzip or Deflate  // if the client accepts compressed responses
        encodeResponse {
          getFromResource("public/" + file)
        }
      }
    }
}