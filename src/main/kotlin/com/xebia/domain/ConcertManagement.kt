package com.xebia.com.xebia.domain

import java.time.LocalDate

sealed interface ConcertManagementAction {
    data class AnnounceNewDate(val concertDate: LocalDate) : ConcertManagementAction
    data class OpenPresales(val presalesTicketsCount: Int) : ConcertManagementAction
    data class OpenGeneralTicketSales(val generalTicketSales: Int) : ConcertManagementAction
    data class SellTickets(val ticketsCount: Int) : ConcertManagementAction
}

sealed interface ConcertManagementState {
    fun processAction(action: ConcertManagementAction): ConcertManagementState

    data class ConcertAnnounced(val releaseDate: LocalDate) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState {
            return when (action) {
                is ConcertManagementAction.SellTickets -> this // No effect on the state
                is ConcertManagementAction.AnnounceNewDate -> this // No effect on the state
                is ConcertManagementAction.OpenGeneralTicketSales -> TicketsOnSale(action.generalTicketSales)
                is ConcertManagementAction.OpenPresales -> PresalesOpen(action.presalesTicketsCount)
            }
        }
    }

    data class PresalesOpen(val availableTickets: Int) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState {
            return when (action) {
                is ConcertManagementAction.AnnounceNewDate -> this // No effect on the state
                is ConcertManagementAction.OpenGeneralTicketSales ->
                    TicketsOnSale(availableTickets + action.generalTicketSales)

                is ConcertManagementAction.OpenPresales -> this // No effect on the state
                is ConcertManagementAction.SellTickets ->
                    if (action.ticketsCount <= availableTickets)
                        PresalesOpen(availableTickets - action.ticketsCount)
                    else this // There are not enough tickets to complete the request
            }
        }
    }

    data class TicketsOnSale(val availableTickets: Int) : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState {
            return when (action) {
                is ConcertManagementAction.AnnounceNewDate -> this // No effect on the state
                is ConcertManagementAction.OpenGeneralTicketSales -> this // No effect on the state
                is ConcertManagementAction.OpenPresales -> this // No effect on the state
                is ConcertManagementAction.SellTickets ->
                    if (action.ticketsCount < availableTickets)
                        TicketsOnSale(availableTickets - action.ticketsCount)
                    else if (action.ticketsCount == availableTickets)
                        SoldOut
                    else this // There are not enough tickets to complete the request
            }
        }
    }

    data object SoldOut : ConcertManagementState {
        override fun processAction(action: ConcertManagementAction): ConcertManagementState {
            return when (action) {
                is ConcertManagementAction.AnnounceNewDate -> ConcertAnnounced(action.concertDate)
                is ConcertManagementAction.OpenGeneralTicketSales -> this // No effect on the state
                is ConcertManagementAction.OpenPresales -> this // No effect on the state
                is ConcertManagementAction.SellTickets -> this // No effect on the state
            }
        }

    }
}

fun simulateActivity(
    initialState: ConcertManagementState,
    actions: List<ConcertManagementAction>
): ConcertManagementState {
    return actions.fold(initialState) { state, action -> state.processAction(action) }
}

fun main() {
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
        ConcertManagementAction.AnnounceNewDate(LocalDate.now()),
    )

    simulateActivity(ConcertManagementState.ConcertAnnounced(LocalDate.now()), actions)
}

