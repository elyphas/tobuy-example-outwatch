package outwatch_components.grid

import monix.execution.Ack.Continue
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{Element, html}
import outwatch.dom.{Sink, StaticVDomModifier, VDomModifier, dsl}
import outwatch.dom.dsl.{contentEditable, _}
import cats.implicits._
import org.scalajs
import outwatch.Handler
import outwatch.dom.dsl.events.{document, window}
import outwatch_components.domain._
/**
  * How to use transitionCell, from the caller assign the `key` `value` of `Map`
  * where the `key` would be the origin cell and `value` the destiny.
  * */
abstract class GridEditable(colFmt: Seq[ValCol], transitionCell: Map[Int, Int]) extends StateGrid {

  var onInsertFocusCol: Int = -1

  val keyEnter = onKeyDown.filter(_.keyCode == KeyCode.Enter)

  case class CellKeyBoardEvent(cell: html.TableCell, key: Int)

  val handler = Handler.create[Option[String]](Some("cell17")).unsafeRunSync()

  def transitionOnEnter = Sink.create[(Int, Int)]{ case (c, r) =>
    transitionCell.get( c + 1 ).foreach(nextCol => handler.onNext(Some("r" + r + "c" + nextCol)))
    Continue
  }

  def insertRow = Sink.create[(Int, Int)]{ case (c, r) =>
    handler.onNext( Some("r" + (r+1) + "c" + 7) )
    Continue
  }

  def getPositionCellEnter = keyEnter.map { k =>
    val cell = k.target.asInstanceOf[html.TableCell]
    val row = cell.parentNode.asInstanceOf[html.TableRow]
    (cell.cellIndex, row.rowIndex)
  }

  def getPositionCellInsert = keyInsert.map { k =>
    val cell = k.target.asInstanceOf[html.TableCell]
    val row = cell.parentNode.asInstanceOf[html.TableRow]
    (cell.cellIndex, row.rowIndex)
  }

  def newCell( cellVal: CellVal ) = td ( if( cellVal.cls != "") cls := cellVal.cls else VDomModifier.empty,
    id := "r" + cellVal.row.toString + "c" + cellVal.col.toString,
    key := "r" + cellVal.row.toString + "c" + cellVal.col.toString,
    getPositionCellInsert --> insertRow,
    getPositionCellEnter --> transitionOnEnter,
    onBlur.map { txt => UpdateField(cellVal.field, txt.target.textContent) } --> dataState,
    cellVal.txt, cellVal.styles
  )

  def newRow( value: Seq[CellVal] ) = value.map { cellVal => newCell( cellVal ) }

  val keyEnterOnCell = keyEnter.map { v =>
    v.preventDefault()
    v.stopPropagation()
    val cellActive = v.target.asInstanceOf[html.TableCell]
    val row = cellActive.parentNode.asInstanceOf[html.TableRow]
    val numberRow = row.rowIndex
    val col = cellActive.cellIndex
    val field = colFmt.filter( i => i.col == (col + 1)).map(_.field).head
    (field, cellActive.textContent, col, numberRow)
  }

  val keyInsert = onKeyDown.filter(k => k.keyCode == KeyCode.Insert)

  def MoveRowDonw(cell: html.TableCell, numbCol: Int) = {
    val currRow = cell.parentNode.asInstanceOf[ html.TableRow ]
    val tbl = currRow.parentNode.asInstanceOf[html.Table]
    val maxRows = tbl.rows.length
    if ( (currRow.rowIndex + 1) < maxRows ) {
      val nextRow = cell.parentNode.nextSibling.asInstanceOf[html.TableRow] //MoveRowDonw(cell)
      val nextCell = nextRow.cells(numbCol).asInstanceOf[html.TableCell]
      nextCell
    } else cell
  }

  sealed trait MoveCursor
  final case object CursLeft extends MoveCursor
  final case object CursRight extends MoveCursor
  final case object CursDown extends MoveCursor
  final case object CursUp extends MoveCursor

  def changePosCell(cellAct: html.TableCell, mov: MoveCursor) = {
    val tbl = dom.document.getElementById("tblRenglones").asInstanceOf[html.Table]
    val currRow = cellAct.parentNode.asInstanceOf[ html.TableRow ]
    val maxRows = tbl.rows.length

    mov match {
      case CursDown =>
        val nextCell = MoveRowDonw(cellAct, cellAct.cellIndex)
        nextCell.focus()
      case CursUp =>
        if ( currRow.rowIndex > 1) {
          val nextRow = cellAct.parentNode.previousSibling.asInstanceOf[html.TableRow]
          val nextCell = nextRow.cells(cellAct.cellIndex).asInstanceOf[html.TableCell]
          nextCell.focus()
        }
      case CursLeft =>
        if (cellAct.cellIndex >= 1) {
          val nextCell = cellAct.previousSibling.asInstanceOf[html.TableCell]
          nextCell.focus()
        }
      case CursRight => //El indice de las columnas empieza en 0
        val nextCell = cellAct.cellIndex match {
          case col if col >= 3 => MoveRowDonw(cellAct, 0)
          case _ => cellAct.nextSibling.asInstanceOf[ html.TableCell ]
        }
        nextCell.focus()
    }
  }

  val onKeyDownTable = Sink.create[CellKeyBoardEvent]{ v: CellKeyBoardEvent =>
    val cellActive =  v.cell
    val t1 = cellActive.parentNode.asInstanceOf[html.TableRow]
    val t2 = t1.parentNode.asInstanceOf[html.Table]
    if (v.key == 38) changePosCell( cellActive, CursUp )
    else if (v.key == 40) changePosCell( cellActive, CursDown )
    else if (v.key == 37) changePosCell( cellActive, CursLeft )
    else if (v.key == 39) changePosCell( cellActive, CursRight )
    Continue
  }
}
