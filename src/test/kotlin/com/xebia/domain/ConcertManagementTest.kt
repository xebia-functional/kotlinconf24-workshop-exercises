package com.xebia.domain

import net.jqwik.api.*
import net.jqwik.api.constraints.IntRange
import java.time.LocalDate
import kotlin.test.assertTrue

class ConcertManagementTest {
  @Property
  fun testSolution(@ForAll @IntRange(min = 1, max = 100) maximumCapacity: Int) {
    val concert = Concert.announceNewConcert(LocalDate.now())

    val preSalesTicketsOffered = 100

    val firstSetOfActions = listOf(
      ConcertManagementAction.OpenPresales(preSalesTicketsOffered),
      ConcertManagementAction.SellTickets(5),
      ConcertManagementAction.SellTickets(15),
      ConcertManagementAction.SellTickets(50)
    )

    val firstConcertState = concert.simulateActivity(firstSetOfActions)

    val preSalesTicketsSold = 70
    val preSalesRemainingTickets = preSalesTicketsOffered - preSalesTicketsSold

    assertTrue(
      firstConcertState.state is ConcertManagementState.PresalesOpen && firstConcertState.state.availableTickets == preSalesRemainingTickets
    )

    val generalTicketsOffered = 100
    val generalTicketsAvailable = generalTicketsOffered + preSalesRemainingTickets

    val secondConcertState = firstConcertState.simulateActivity(
      listOf(
        ConcertManagementAction.OpenGeneralTicketSales(generalTicketsOffered)
      )
    )

    assertTrue(secondConcertState.state is ConcertManagementState.TicketsOnSale && secondConcertState.state.availableTickets == generalTicketsAvailable)

    val thirdSetOfActions = (0..generalTicketsAvailable step 5).map { ConcertManagementAction.SellTickets(5) }

    val thirdConcertState = secondConcertState.simulateActivity(thirdSetOfActions)

    assertTrue(thirdConcertState.state is ConcertManagementState.SoldOut)

    val newConcertDate = LocalDate.now().plusDays(2)
    val forthConcertState =
      thirdConcertState.simulateActivity(listOf(ConcertManagementAction.AnnounceNewDate(newConcertDate)))
    assertTrue(
      forthConcertState.state is ConcertManagementState.ConcertAnnounced && forthConcertState.state.releaseDate == newConcertDate
    )
  }
}
