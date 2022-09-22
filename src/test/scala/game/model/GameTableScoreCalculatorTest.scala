package org.sample.game.model

import org.sample.game.model.Card.Colour._
import org.sample.game.model.Card.Rank._
import org.sample.game.model.Card._
import org.sample.game.model.GameTableScoreCalculator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class GameTableScoreCalculatorTest extends AnyFreeSpec
  with Matchers {

  "Red should" - {
    "score hand " in {
      Red.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(Six, Blue).value
      Red.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual Card(Seven, Green).value
    }
  }

  "Orange should" - {
    "score hand " in {
      Orange.score(List(Card(Six, Blue), Card(One, Blue))) shouldEqual Card(Six, Blue).value + 50
      Orange.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual Card(Seven, Green).value + 2 * 50
      Orange.score(List(Card(Four, Yellow), Card(Six, Blue), Card(Six, Yellow), Card(Four, Red))) shouldEqual Card(Six, Yellow).value + 2 * 50
    }
  }

  "Yellow should" - {
    "score hand " in {
      Yellow.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(Six, Blue).value + 2 * 50
      Yellow.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual Card(Seven, Green).value + 50
      Yellow.score(List(Card(Four, Yellow), Card(Six, Blue), Card(Six, Yellow), Card(Four, Blue))) shouldEqual Card(Six, Yellow).value + 2 * 50
    }
  }

  "Green should" - {
    "score hand " in {
      Green.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(Six, Blue).value + 50
      Green.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual 0
      Green.score(List(Card(Four, Blue), Card(Six, Blue), Card(Six, Yellow), Card(Two, Red))) shouldEqual Card(Six, Yellow).value + 4 * 50
    }
  }

  "Blue should" - {
    "score hand " in {
      Blue.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(Six, Blue).value + 50
      Blue.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual Card(Seven, Green).value + 2 * 50
      Blue.score(List(Card(Four, Yellow), Card(Six, Blue), Card(Six, Yellow), Card(Two, Red))) shouldEqual Card(Six, Yellow).value + 3 * 50
    }
  }

  "Indigo should" - {
    "score hand " in {
      Indigo.score(List(Card(Six, Blue))) shouldEqual Card(Six, Blue).value
      Indigo.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(Six, Blue).value
      Indigo.score(List(Card(Seven, Green), Card(Six, Violet))) shouldEqual Card(Seven, Green).value + 50
      Indigo.score(List(Card(Four, Yellow), Card(Seven, Blue), Card(Five, Yellow), Card(Three, Red))) shouldEqual Card(Five, Yellow).value + 2 * 50
      Indigo.score(List(Card(Four, Yellow), Card(Seven, Blue), Card(Five, Yellow), Card(Two, Red), Card(One, Orange))) shouldEqual Card(Five, Yellow).value + 50
    }
  }

  "Violet should" - {
    "score hand " in {
      Violet.score(List(Card(One, Blue), Card(Six, Blue))) shouldEqual Card(One, Blue).value + 50
      Violet.score(List(Card(Seven, Green), Card(Seven, Violet))) shouldEqual 0
      Violet.score(List(Card(Four, Yellow), Card(Two, Blue), Card(Six, Yellow), Card(Two, Red))) shouldEqual Card(Two, Red).value + 2 * 50
    }
  }

  "GameTableScoreCalculator should" - {
    val sixBlueThreeVioletTwoIndigoOneOrangeOneBlue = List(Card(One, Blue), Card(Six, Blue), Card(One, Orange), Card(Three, Violet), Card(Two, Indigo))
    val sixYellowFourYellowTwoRedTwoBlue = List(Card(Four, Yellow), Card(Two, Blue), Card(Six, Yellow), Card(Two, Red))
    val sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange = List(Card(Two, Yellow), Card(Seven, Blue), Card(Five, Yellow), Card(Two, Orange), Card(One, Orange))

    "pick best hand" in {
      val sampleHands =
        Table(
          ("rule", "hands", "result"),
          (Red, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Orange, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Yellow, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Green, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Blue, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Indigo, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Violet, List(sixYellowFourYellowTwoRedTwoBlue, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),

          (Red, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange),
          (Orange, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange),
          (Yellow, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Green, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Blue, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Indigo, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),
          (Violet, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixBlueThreeVioletTwoIndigoOneOrangeOneBlue), sixBlueThreeVioletTwoIndigoOneOrangeOneBlue),

          (Red, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange),
          (Orange, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Yellow, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Green, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sixYellowFourYellowTwoRedTwoBlue),
          (Blue, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange),
          (Indigo, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange),
          (Violet, List(sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange, sixYellowFourYellowTwoRedTwoBlue), sevenBlueFiveYellowTwoOrangeTwoYellowOneOrange)
        )

      forAll(sampleHands) { (rule, hands, result) =>
        bestPalette(rule, hands) shouldEqual result
      }
    }
  }

}
