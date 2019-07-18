package manik1.modules.lstBienesStore

import outwatch.util.Store
import monix.execution.Scheduler.Implicits.global
import outwatch_components.maptocc.ConvertHelper
import shapeless.Generic
import spatutorial.shared.{Item, Items}
import outwatch_components.transformdata._

class ItemsStore {

  case class State(items: Items[Item] = Items(Seq.empty[Item]), rowActive: Item = Item() )

  sealed trait Actions
  case object Search extends Actions
  case class UpdatetItems(l: Items[Item]) extends Actions
  case class UpdateItem(item: Item) extends Actions

  case class UpdateItemCveArticulo(field: String, value: String, col: Int, row: Int) extends Actions
  case class UpdateItemCantidad(field: String, value: String, col: Int, row: Int) extends Actions

  case class UpdateDescripcionArticulo(descr: String) extends Actions
  case class UpdateRowActive(n: Int) extends Actions
  case class InsertItem(item: Item) extends Actions
  case class SaveItem(item: Map[String, Any]) extends Actions
  case class DeleteItem(item: Map[String, Any]) extends Actions

  case object Clean extends Actions

  def condIndex(a: Item, b: Item): Boolean = {
    val genericItem1 = Generic[Item].to(a)
    val genericItem2 = Generic[Item].to(b)
    genericItem1.take(1) == genericItem2.take(1)
  }

  //def condIndex(a: Item, b: Item): Boolean = a == b

  val reduce: (State, Actions) => State = (s, a) => {
    def updateFieldInRowActive ( field: String, value: String ) = {
      val rowActiveMap = TransformData(s.rowActive)
      val rowActiveMap2 = rowActiveMap + (field -> value)

      def to[A]: ConvertHelper[A] = new ConvertHelper[A]
      to[Item].from(rowActiveMap2).getOrElse(Item())
    }

    def MapToCC(map: Map[String, Any]) = Item (   key = map("key").toString,
                                                  description = map("description").toString,
                                                  amount = map("amount").toString.toInt )

    //Helper to make MapToCC
    def to[A]: ConvertHelper[A] = new ConvertHelper[A]

    a match {
      case Clean =>
        s.copy(items = Items(Seq.empty[Item]))
      case UpdatetItems( lst ) =>
        s.copy(items = lst)
      case InsertItem(item) =>
        val newItems: Seq[Item] = s.items.items ++ Seq(item)
        s.copy ( items = s.items.copy( items = newItems ), rowActive = item )
      case UpdateItem(renglon) =>
        val newListBienes = s.items.updated(renglon, condIndex)
        s.copy(items = newListBienes)

      case SaveItem(item) =>

        val newItem = Item (itemnumber = item("itemnumber").toString.toInt,
                            key = item("key").toString,
                            description = item("description").toString,
                            amount = item("amount").toString.toInt  )

        val newItems = s.items.updated( newItem, condIndex )

        s.copy( items = newItems, rowActive = newItem )

      case DeleteItem( item ) =>

        val newItem = Item ( itemnumber = item("itemnumber").toString.toInt,
          key = item("key").toString,
          description = item("description").toString,
          amount = item("amount").toString.toInt  )

        println( "En el itemsStore el elemento a borrar: " + newItem )

        val newItems = s.items.remove( newItem )

        s.copy( items = newItems )

      case otros =>
        println( "En el reduce no lo esta catchando: " + otros )
        s
    }
  }

  val initState = State()
  val store = Store.create[Actions, State](Clean, initState, reduce).unsafeRunSync()

}