package outwatch_components.grid

import monix.execution.Ack.Continue
import outwatch_components.domain._
import org.scalajs.dom.html
import outwatch.dom._
import outwatch.dom.dsl._

class Grid(colFmt: Seq[ValCol], transitionCell: Map[Int, Int], fieldIndx: String) extends GridEditable(colFmt, transitionCell) {

  def titleTbl(titles: Seq[ValCol]) =
      titles ++: Seq(
          ValCol(col = titles.size + 1, field = "save", title = "Save", styleTitle = Seq(width := "50px"), styleCell = Seq(textAlign := "center")),
          ValCol(col = titles.size + 2, field = "delete", title = "Delete", styleTitle = Seq(width := "50px"), styleCell = Seq(textAlign := "center"))
        ).sortWith( (prev, next) => prev.col < next.col) map( i => td(i.title, i.styleTitle))



  def showDetails(l: Seq[Map[String, Any]], activeItem: Map[String, Any]) = l.map { i =>
      tr( id := "row" + i.getOrElse( fieldIndx, "" ).toString,
        newRow(
          colFmt.map { ii =>
            val valor = i.getOrElse(ii.field, "") match {
                case Some(v) => v.toString
                //case Fechas(v) => v.toString
                //case v: Double if ii.typeNumber == "money" => fmtMiles(v)
                case v => v.toString
            }
            CellVal( ii.field, valor, col = ii.col, row = i.getOrElse( fieldIndx, "" ).toString.toInt, styles = ii.styleCell )
          } ++: Seq(

            CellVal( "", "Save",
                      col = colFmt.size + 1,
                      row = i.getOrElse( fieldIndx, "" ).toString.toInt,
                      styles = VDomModifier( onClick.mapTo(SaveRowActive(activeItem)) --> dataState),
                      cls = "textlikebutton" ),

            CellVal( "", "Delete",
                      col = colFmt.size + 2,
                      row = i.getOrElse( fieldIndx, "" ).toString.toInt,
                      styles = VDomModifier ( onClick.mapTo {
                        println("*************************** Elemento a borrar: " + i)
                        DeleteRowActive(i)
                      } --> dataState),
                      cls = "textlikebutton" )
          )
        ),
        onClick.mapTo( UpdateRowActive( i ) ) --> dataState
      )

    }



  def render(events: VDomModifier) =
    dataState.map { case (a, s) =>
      table( id := "tblGrid",
          thead(tr(titleTbl(colFmt)), height := "20px"),
          tbody(
            //events,
            keyInsert.map { r =>
              val cell = r.target.asInstanceOf[html.TableCell]
              val row = cell.parentNode.asInstanceOf[html.TableRow]
              InsertRow(row.rowIndex) } --> dataState,
            keyEnterOnCell.map { case (field, value, col, row) => UpdateRow(field, value, col, row)} --> dataState,
            showDetails(s.items.items, s.rowActive),
        )
      )
    }

}