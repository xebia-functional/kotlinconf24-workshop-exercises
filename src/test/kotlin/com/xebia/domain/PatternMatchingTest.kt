package com.xebia.domain

import com.xebia.com.xebia.domain.Concert
import com.xebia.com.xebia.domain.ConcertAction
import com.xebia.com.xebia.domain.ConcertState
import com.xebia.com.xebia.domain.action
import net.jqwik.api.*
import net.jqwik.api.constraints.IntRange
import org.junit.jupiter.api.Assertions.assertTrue

class PatternMatchingTest {
  @Property
  fun testSolution(@ForAll @IntRange(min = 1, max = 100) maximumCapacity: Int) {
    var concert = Concert(maximumCapacity)

    for (i in 1 .. maximumCapacity) {
      concert = Concert(maximumCapacity).action(ConcertAction.SellTickets(1))
      assertTrue(concert.state is ConcertState.TicketsOnSale)
    }

    concert = concert.action(ConcertAction.SellTickets(1))
    assertTrue(concert.state is ConcertState.SoldOut)

    concert = concert.action(ConcertAction.NewDateAvailable)
    assertTrue(concert.state is ConcertState.TicketsOnSale)
  }
}