package manik1.manik1

import outwatch.router._
import outwatch.router.dsl.C

import monix.execution.Scheduler
import outwatch.dom._
import outwatch.dom.dsl._

import monix.reactive.Observable
import manik1.modules._

import cats.effect.IO

sealed trait Page
case object ItemsMenu extends Page
case object HomeMenu extends Page
case object HelpMenu extends Page
case object NotFoundMenu extends Page

object Menu {
  val ipWS = "localhost"
  val portWS = "8090"

  def router: AppRouter[Page] = AppRouter.create[Page](NotFoundMenu){
    case Root / "items" => ItemsMenu
    case Root / "home" => HomeMenu
    case Root / "help" => HelpMenu
    case _ => NotFoundMenu
  }

  def pageContainer()(implicit S: Scheduler, router: RouterStore[Page]): IO[Observable[VDomModifier]] =
    IO(
      AppRouter.render[Page]{
        case ItemsMenu =>
          val frm = new FrmItems
          frm.render
        case HelpMenu =>
          FrmHelp.render
        case HomeMenu =>
            div(clear.both,
                h3("Start"),
                p("Welcome! :)")
            )
        case NotFoundMenu => div()
      }
    )

  def render()(implicit scheduler: Scheduler, router: RouterStore[Page]): VDomModifier =
    pageContainer().map { pc =>

      div( id:="mainMenu", cls := "ui two column grid",
        div( clear.both, width := "2000px",
          ul(
            li( cls := "itemMenu", C.a[Page]( "/" + "home" )( "Home" ) ),
            li( cls := "menuDropDown", "Shopping",
              div( cls := "menuDropDown2",
                li( cls := "itemMenuDropDown", C.a[Page]( "/" + "items" )( "Items" ) ),
              )
            ),

            li( cls := "itemMenu", C.a[Page]( "/" + "help" )( "Help" ) ),
          ),
        ),
        div( clear.both, marginTop := "50px", cls := "twelve wide fluid column", //this is where we are going to place the forms.
          pc
        )
      )

    }
}

object HelloWoutWatch {
  import monix.execution.Scheduler.Implicits.global
  def main(args: Array[String]): Unit = {
    val program = for {
      implicit0(exRouterStore: RouterStore[Page]) <- Menu.router.store
      program <- OutWatch.renderInto("#root", div( Menu.render ) )

    } yield program
    program.unsafeRunSync()
  }
}
