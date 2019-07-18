package outwatch_components.grid

import outwatch.util.Store
import monix.execution.Scheduler.Implicits.global
import outwatch_components.domain.Items

trait StateGrid {

  val initState = State()

  sealed trait ActionsGrid
  case object Clean extends ActionsGrid

  case class Update(items: Items, rowActive: Map[String, Any]) extends ActionsGrid
  case class InsertRow(index: Int) extends ActionsGrid
  //case class UpdateRowActive(n: Int) extends ActionsGrid
  case class UpdateRowActive(item: Map[String, Any]) extends ActionsGrid
  case class UpdateRow(field: String, value: String, col: Int, row: Int) extends ActionsGrid
  case class UpdateIndex(index: Map[String, Any] = Map("itemnumber" -> ( 4 - 1 ) )) extends ActionsGrid
  case class SaveRowActive(item: Map[String, Any]) extends ActionsGrid
  case class DeleteRowActive(item: Map[String, Any]) extends ActionsGrid
  case class UpdateField(field: String, txt: String) extends ActionsGrid

  case class State( items: Items = Items ( items = Seq.empty ),
                        indice: Map[String, Any] = Map("itemnumber"-> ( 4 - 1 )),
                        rowActive: Map[String, Any] = Map.empty )

  val reduce: (State, ActionsGrid) => State = (s, a) => a match {
    case Clean =>
      s.copy(items = Items() )

    case Update(datos, activ) =>
      s.copy(items = datos, rowActive = activ)

    case UpdateRowActive( i ) =>
      s.copy( rowActive = i )

    case UpdateIndex(ind) =>
      s.copy(indice = ind)

    case SaveRowActive(item) =>
      val condIndex = (b: Int) => item.getOrElse("itemnumber","").toString.toInt == b
      val newItems = s.items.updated(item, condIndex)
      s.copy( items = newItems )

    case DeleteRowActive( item ) =>
      val newItems = s.items.remove( item )

      println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Items sin el elemento borrado: " + newItems)

      s.copy( items = newItems )

    case UpdateField(field, txt) =>
      val newRowActive = s.rowActive + (field -> txt)
      s.copy( rowActive = newRowActive )

  }

  val dataState = Store.create[ActionsGrid, State](Clean, initState, reduce).unsafeRunSync()

}