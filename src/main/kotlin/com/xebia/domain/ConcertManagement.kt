package com.xebia.domain

import java.time.LocalDate

/**
 * In this exercise, we will implement a naive event/state application to simulate how to manage
 * a [Concert]. To do so, we need to implement [ConcertManagementState.processAction] by using
 * pattern matching.
 *
 * Some aspects to consider:
 * - In the action doesn't have effect on the current state of the concert, we'll return the current state.
 * - Take into account that users might want to buy more tickets than are available! If there's not enough
 *   tickets, the state won't change. We can revisit this behavior later, once we explain typed errors.
 * - If there are still pre-sales tickets left when the general sales is open, we should sum them to the
 *   total general tickets available.
 * - We'll only hang the Sold Out letter when someone buy exactly the number of tickets available.
 */
sealed interface ConcertManagementAction {
    data class AnnounceNewDate(val concertDate: LocalDate) : ConcertManagementAction
    data class OpenPresales(val presalesTicketsCount: Int) : ConcertManagementAction
    data class OpenGeneralTicketSales(val generalTicketSales: Int) : ConcertManagementAction
    data class SellTickets(val ticketsCount: Int) : ConcertManagementAction
}

sealed interface ConcertManagementState {
    fun processAction(action: ConcertManagementAction): ConcertManagementState

    data class ConcertAnnounced(val releaseDate: LocalDate) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState = TODO()
    }

    data class PresalesOpen(val availableTickets: Int) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState = TODO()
    }

    data class TicketsOnSale(val availableTickets: Int) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState = TODO()
    }

    data object SoldOut : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState = TODO()
    }
}

data class Concert(val state: ConcertManagementState) {
    fun simulateActivity(
        actions: List<ConcertManagementAction>
    ): Concert {
        val finalState = actions.fold(state) { state, action -> state.processAction(action) }
        return copy(state = finalState)
    }

    companion object {
        fun announceNewConcert(date: LocalDate): Concert =
            Concert(ConcertManagementState.ConcertAnnounced(date))
    }
}

fun main() {
    val firstConcertDate = LocalDate.now().plusMonths(2)
    val secondConcertDate = firstConcertDate.plusDays(1)

    val actions = listOf(
        ConcertManagementAction.OpenPresales(10),
        ConcertManagementAction.SellTickets(2),
        ConcertManagementAction.SellTickets(2),
        ConcertManagementAction.SellTickets(4),
        ConcertManagementAction.OpenGeneralTicketSales(25),
        ConcertManagementAction.SellTickets(2),
        ConcertManagementAction.SellTickets(5),
        ConcertManagementAction.SellTickets(6),
        ConcertManagementAction.SellTickets(4),
        ConcertManagementAction.SellTickets(3),
        ConcertManagementAction.SellTickets(4),
        ConcertManagementAction.SellTickets(4),
        ConcertManagementAction.SellTickets(3),
        ConcertManagementAction.AnnounceNewDate(secondConcertDate),
    )

    val concertAfterSimulation = Concert
        .announceNewConcert(firstConcertDate)
        .simulateActivity(actions)

    assert(
        concertAfterSimulation.state is ConcertManagementState.ConcertAnnounced
                && concertAfterSimulation.state.releaseDate == secondConcertDate
    )

    println(concertAfterSimulation)
}
