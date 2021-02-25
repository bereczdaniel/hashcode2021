package hashcode

object Representation {
  case class Simulation(streets: List[Street], cars: List[Car], numIntersections: Int, duration: Int, bonus: Int)
  case class Street(name: String, in: Intersection, out: Intersection, length: Int)
  case class Car(route: List[String])

  type Intersection = Int

}

