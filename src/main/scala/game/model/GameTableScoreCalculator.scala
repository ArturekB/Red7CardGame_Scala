package org.sample.game.model


import org.sample.game.model.Card.{Card, Colour, Rank}
import org.sample.game.model.Card.Rank.precedingRank

import scala.language.implicitConversions

object GameTableScoreCalculator {

  def bestPalette(rule: Colour, palettes: List[List[Card]]): List[Card] = palettes
    .map(palette => (palette, rule.score(palette)))
    .maxBy {case(palette, score) => score}
    ._1

  implicit class RuleCalculator(rule: Colour) {

    def score(palette: List[Card]): Int = {
      rule match {
        //Highest card
        case Colour.Red => palette.max.value

        //Cards of one number
        case Colour.Orange =>
          groupedMapScore(palette
            .groupMap(card => card.rank)(card => card.value))

        //Card of one colour
        case Colour.Yellow =>
          groupedMapScore(palette
            .groupMap(card => card.colour)(card => card.value))

        //Even cards
        case Colour.Green =>
          listScore(palette.filter(card => card.isEven).sorted)

        //Cards of different colours
        case Colour.Blue =>
          val colourMaxValueMap = palette
            .groupMap(card => card.colour)(card => card.value)
            .map { case (colour, value) => (colour, value.max) }
          colourMaxValueMap.size * 50 + colourMaxValueMap.values.max

        //Cards that form a run
        case Colour.Indigo =>
          val grouped = palette
            .groupMap(card => card.rank)(card => card.value)
            .map { case (rank, value) => (rank, value.max) }
          grouped
            .map { case (rank: Rank, highestCardValue: Int) => numberOfPreceding(rank, grouped.keySet) * 50 + highestCardValue }
            .max

        //Cards below 4
        case Colour.Violet =>
          listScore(palette.filter(card => card.isBelowFour).sorted)
      }
    }
  }

  def groupedMapScore[K](map: Map[K, List[Int]]): Int = {
    val maxQuantity = map.values.maxBy(list => list.size).size
    val mostValuableCard = map.values
      .filter(list => list.size == maxQuantity)
      .flatten
      .max
    mostValuableCard + 50 * maxQuantity
  }

  def listScore(cards: List[Card]): Int = cards.size * 50 + cards.lastOption.map(card => card.value).getOrElse(0)

  def numberOfPreceding(rank: Rank, ranks: Set[Rank]): Int = {
    precedingRank(rank) match {
      case Some(value) if ranks.contains(value) => numberOfPreceding(value, ranks) + 1
      case _                                    => 0
    }
  }
}
