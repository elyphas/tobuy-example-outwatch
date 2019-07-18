package outwatch_components

import monix.execution.Cancelable
import outwatch.dom.{BasicVNode, Handler, VDomModifier}
import outwatch.dom.dsl._
import outwatch.dom._
import monix.execution.Scheduler.Implicits.global
import cats.implicits._
import monix.reactive.Observer

object inputs {


  def cmpCombo[S, F](lbl: String, hdl: Handler[String], lst: Map[String, String], events: VDomModifier, itemSelected: String, w: Int) = {

    val optDefault = option(disabled, selected := true, display.none)

    val lstOpt = lst.map { case ( k, v ) =>
        val selectedd = k == itemSelected
        option( k, value := v, selected := selectedd, fontSize:="6px" )
    }.toSeq

    div(id := "control",
      float.left, width := ( w + 35 ).toString  + "px", label( lbl, width := "60px" ),
      select ( id := lbl.replace(" ", ""),
        float.left, marginTop := "10px",
        value <-- hdl.map( r => r ),
         optGroup( cls:="optgroup",
           optDefault +: lstOpt
        ),
        events
      )
    )

  }

  /*def cmpInput22[S, T](lbl: String, hdl: Handler[S], valueHdl: S, w: Int, events: VDomModifier)(read: S => T)(write: (S, T)=> S) = {

      val txt = hdl.lens[T](valueHdl)(read)(write)
      val cancel = txt.connect()

      val node = div(id := "control",
        float.left, width := ( w + 35 ).toString  + "px", label( lbl, width := "60px" ),
        input ( width := w.toString + "px", cls:="selectall",
          value <-- txt.map ( r => r.toString ),
          onChange.target.value.map(t => t.toUpperCase()) --> txt,
          events,
        )
      )
      (node, txt, cancel)
    }*/


  def cmpInput2(lbl: String, hdl: Handler[String], w: Int, events: VDomModifier) =
    div(id := "control",
      float.left, width := ( w + 35 ).toString  + "px", label( lbl, width := "60px" ),
      input ( width := w.toString + "px", cls:="selectall",
        value <-- hdl.map ( r => r ),
        onChange.target.value.map(t => t.toUpperCase()) --> hdl,
        events,
      )
    )

  def cmpInput(lbl: String, hdl: Handler[String], w: Int) =
    div(id := "control",
      float.left, width := ( w + 35 ).toString  + "px", label( lbl, width := "60px" ),
      input (
        value <-- hdl.map ( r => r ),
        onChange.target.value --> hdl,
        width := w.toString + "px"
      )
    )

}
