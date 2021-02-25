package hashcode

object Parser {

  import Representation._

  def parseStreet(line: String): Street = {
    val in :: out :: name :: length :: Nil = line.split(" ").toList
    Street(name, in.toInt, out.toInt, length.toInt)
  }

  def parseCar(line: String): Car = {
    Car(line.split(" ").tail.toList)
  }

  def parseSimulation(lines: List[String]): Simulation = {
    val duration :: numIntersections :: numStreets :: numCars :: bonus :: Nil = lines.head.split(" ").toList.map(_.toInt)
    val streets = (1 to numStreets).map(i => parseStreet(lines(i))).toList
    val cars = (numStreets to (numStreets + numCars)).map(i => parseCar(lines(i))).toList

    Simulation(streets, cars, numIntersections, duration, bonus)
  }

  def fromFileToLines(filename: String) = scala.io.Source.fromFile(filename).getLines.toList

  def main(args: Array[String]): Unit = {
    println(parseSimulation(fromFileToLines("inputs/a.txt")))
  }
}


