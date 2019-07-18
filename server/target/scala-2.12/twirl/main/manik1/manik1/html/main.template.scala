
package manik1.manik1.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
  <head>
        <title>"""),_display_(/*6.17*/title),format.raw/*6.22*/("""</title>
        <link rel="stylesheet" href="/assets/stylesheets/main.min.css">
  </head>
  <body>
   <div id="root">
   </div>
   """),_display_(/*12.5*/scalajs/*12.12*/.html.scripts("client", name => s"/assets/$name", name => getClass.getResource(s"/public/$name") != null)),format.raw/*12.117*/("""
  """),format.raw/*13.3*/("""</body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 18 12:04:24 CDT 2019
                  SOURCE: /home/elyphas/PrjsScalaWS/tobuy/server/src/main/twirl/manik1/manik1/main.scala.html
                  HASH: 086eaf1f3447a216f9f9930edd4f6ea9ef55d6a4
                  MATRIX: 578->1|702->32|729->33|803->81|828->86|987->219|1003->226|1130->331|1160->334
                  LINES: 14->1|19->2|20->3|23->6|23->6|29->12|29->12|29->12|30->13
                  -- GENERATED --
              */
          