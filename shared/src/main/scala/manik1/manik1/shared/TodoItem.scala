package spatutorial.shared

case class Item(itemnumber: Int = 0, key: String = "", description: String = "", amount: Int = 0)


case class Items[A](items: Seq[A]= Seq.empty[A]) {

	def updated(newItem: A, idFunction: (A, A) => Boolean) = {

		val cond = idFunction( newItem, _:A )

		items.indexWhere { item => cond(item) } match {
			case -1 => Items(items :+ newItem) // add new
			case idx => Items(items.updated(idx, newItem)) // replace old
		}
	}

	def remove(item: A) = Items(items.filterNot(_ == item))

}