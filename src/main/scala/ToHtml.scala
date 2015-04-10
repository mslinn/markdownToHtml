import java.net.URI

import org.pegdown._
import java.io.File

object ToHtml extends App {
  if (args.isEmpty) {
    println("Usage: ToHtml filename")
    System.exit(-1)
  }
  val markdown = try {
    io.Source.fromFile(args(0)).mkString
  } catch {
    case e: Exception =>
      println(s"${e.getClass.getName} ${e.getMessage}")
      sys.exit(-2)
  }
  val processor = new PegDownProcessor(Extensions.ALL)
  val html = processor.markdownToHtml(markdown)
  if (!show(html))
    println(html)

  def show(arg: String): Boolean = {
    import java.awt.Desktop

    if (Desktop.isDesktopSupported) {
      val desktop = Desktop.getDesktop
      if (desktop.isSupported(Desktop.Action.BROWSE)) try {
        val tempFile = File.createTempFile(".toHtml", ".html")
        writeToFile(tempFile, arg) foreach { uri =>
          desktop.browse(uri)
        }
        true
      } catch {
        case e: Exception =>
          Console.err.println(e.getMessage)
          false
      } else false
    } else false
  }

  def writeToFile(file: File, string: String): Option[URI] = {
    import java.io.{BufferedWriter, FileWriter}

    val bw = new BufferedWriter(new FileWriter(file))
    try {
      bw.write(string)
      Some(file.toURI)
    } catch {
      case e: Exception =>
        Console.err.println(e.getMessage)
        None
    } finally {
      bw.close()
    }
  }
}
