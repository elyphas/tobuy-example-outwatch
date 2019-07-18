package manik1.manik1

import java.nio.ByteBuffer

import scala.util.Success
import akka.http.scaladsl.server.Directives
import manik1.manik1.twirl.Implicits._
//import postg.CUsuarios
//import share.covenantAPI.{Api, ApiError}
import sloth.{Router, ServerFailure}
import monix.execution.Scheduler.Implicits.global
import scala.util.Failure

/*********Covenant   ***/
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
/*import covenant.ws.AkkaWsRoute
import covenant.ws.api.WsApiConfigurationWithDefaults
import covenantBackend.Covenant.{ApiImpl, Dsl, Event, State}*/
/**********************/

/** for Mycelium**/
/*import mycelium.core.AkkaMessageBuilder.AkkaMessageBuilderByteBuffer
import boopickle.Default._
import chameleon.ext.boopickle._
import mycelium.server.WebsocketServerConfig*/
/**  */

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