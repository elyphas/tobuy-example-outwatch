
package manik1.manik1.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Xiimbal maak")/*3.22*/ {_display_(Seq[Any](format.raw/*3.24*/("""
    """),format.raw/*4.5*/("""<!-- <h2>Akka HTTP and Scala.js share a same message</h2>
    <ul>
        <li>Akka HTTP shouts out: <em>"""),_display_(/*6.40*/message),format.raw/*6.47*/("""</em></li>
        <li>Scala.js shouts out: <em id="scalajsShoutOut"></em></li>
    </ul> -->
""")))}),format.raw/*9.2*/("""
"""))
      }
    }
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 18 12:04:24 CDT 2019
                  SOURCE: /home/elyphas/PrjsScalaWS/tobuy/server/src/main/twirl/manik1/manik1/index.scala.html
                  HASH: f6727c9635d1d63fed0bc28af0674a2cb9e654fd
                  MATRIX: 574->1|685->19|712->21|740->41|779->43|810->48|942->154|969->161|1093->256
                  LINES: 14->1|19->2|20->3|20->3|20->3|21->4|23->6|23->6|26->9
                  -- GENERATED --
              */
          