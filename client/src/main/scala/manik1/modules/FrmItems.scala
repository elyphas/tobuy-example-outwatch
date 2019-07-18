package manik1.modules

import monix.execution.Scheduler.Implicits.global

import spatutorial.shared.{Item, Items}
import outwatch.dom._
import outwatch.dom.dsl._
import monix.execution.Ack._
import manik1.modules.lstBienesStore._
import outwatch_components.transformdata._
import outwatch_components.grid.Grid
import outwatch_components.domain.{Items => ItemsGrid, ValCol}

class FrmItems {

  val colsFmt = Seq (
    ValCol(col =  1, field = "itemnumber", title = "Item", styleTitle = Seq(width := "50px"), styleCell = Seq(textAlign := "center")),
    ValCol(col =  2, field = "key", title = "Key", styleTitle = Seq(width := "50px"), styleCell = Seq(contentEditable:=true, textAlign := "justify")),
    ValCol(col =  3, field = "description", title = "Description", styleTitle = Seq(width := "70px"), styleCell = Seq(contentEditable:=true, textAlign := "justify")),
    ValCol(col =  4, field = "amount", title = "Amount", styleTitle = Seq(width := "50px"), styleCell = Seq(contentEditable:=true, textAlign := "right"))
  )

  val transition = Map( 1 -> 2, 2 -> 3 )     //onkeydown enter jump from column to column.

  val grid = new Grid( colsFmt, transition, "itemnumber")
  grid.dataState.onNext( grid.UpdateIndex( Map( "itemnumber" -> 1 )))


  val itemsStore = new ItemsStore( )

  /*val updateItemStore = Sink.create[String]{ s =>
    itemsStore.store.onNext{
      val initItem = Item(itemnumber = 1)
      itemsStore.UpdatetItems(Items(items = Seq(initItem)))
    }
  }*/

  grid.dataState.foreach { case (a, s) =>
    a match {
      case grid.InsertRow(r) =>

        for {
          s <- itemsStore.store
        } yield {
          val nextItem = s._2.items.items.size + 1
          val tmpItem = Item( itemnumber = nextItem, key = "", description = "", amount = 0)
          itemsStore.store.onNext( itemsStore.InsertItem(tmpItem))
        }

      case grid.UpdateRow(field, value, col, row) =>
        if ( (col + 1) == 7)
          itemsStore.store.onNext( itemsStore.UpdateItemCveArticulo(field, value, col, row))
        else if ( (col + 1) == 12)
          itemsStore.store.onNext(itemsStore.UpdateItemCantidad(field, value, col, row))
      /*case grid.UpdateRowActive(n) =>
        itemsStore.store.onNext(itemsStore.UpdateRowActive(n))*/

      case grid.SaveRowActive(item) =>
        itemsStore.store.onNext(itemsStore.SaveItem(item))

      case grid.DeleteRowActive( item ) =>
        itemsStore.store.onNext(itemsStore.DeleteItem(item))

      case otroEvento =>
        println("otro evento: " + otroEvento)
    }
  }

  val addItemStore = Sink.create[Int]{ r =>
    itemsStore.store.onNext( itemsStore.InsertItem(Item(itemnumber = r + 1)) )
  }

  def render = for { s <- itemsStore.store } yield {

    val lst = s._2.items.items.map( i => TransformData(i))
    val rowActive = TransformData(s._2.rowActive)

    grid.dataState.onNext(grid.Update(ItemsGrid(lst), rowActive))

    div(
      h1("Shopping!"),
      button( cls:="myButton", "Add Item", marginTop := "20px", onClick.mapTo(s._2.items.items.size) --> addItemStore),
      div(clear.both, grid.render(Seq.empty[VDomModifier]))
    )

  }

}