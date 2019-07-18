
package manik1.manik1.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml

object logear extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(mensage: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>"""),_display_(/*7.17*/mensage),format.raw/*7.24*/("""</title>
    </head>
    <body>
        <h3> Introducir Tú usuario y contraseña</h3>
        <h3> """),_display_(/*11.15*/mensage),format.raw/*11.22*/("""</h3>
        <form action="http://10.51.253.205:8090/validate" method="post" enctype="application/x-www-form-urlencoded">
            <label for="username">Usuario</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>

            <button type="submit" id="Sessión">Sesión</button>
        </form>
    </body>
</html>"""))
      }
    }
  }

  def render(mensage:String): play.twirl.api.HtmlFormat.Appendable = apply(mensage)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (mensage) => apply(mensage)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 18 12:04:24 CDT 2019
                  SOURCE: /home/elyphas/PrjsScalaWS/tobuy/server/src/main/twirl/manik1/manik1/logear.scala.html
                  HASH: 639930c46e54b686948de2cedfef63b1ce8cc4b4
                  MATRIX: 575->1|686->19|804->111|831->118|957->217|985->224
                  LINES: 14->1|19->2|24->7|24->7|28->11|28->11
                  -- GENERATED --
              */
          