package outwatch_components.domain

import outwatch.dom.VDomModifier

case class ValCol(col: Int, field: String, title:String, styleTitle: VDomModifier, styleCell: VDomModifier, typeNumber: String = "")

case class CellVal(field: String, txt: String, col: Int, row: Int, styles: VDomModifier, cls: String = "")

case class Items(items: Seq[Map[String, Any]] = Seq.empty) {

  def updated(newItem: Map[String, Any], idFunction: Int => Boolean) = {

    items.indexWhere { i =>
      idFunction(i.getOrElse("itemnumber","0").toString.toInt )
    } match {
      case -1 =>
        Items(items :+ newItem) // add new
      case idx =>
        Items(items.updated(idx, newItem)) // replace old
    }
  }

  def remove(item: Map[String, Any]) = Items(items.filterNot(_ == item))

}