package com.xebia.com.xebia.domain

/**
 * Exercise: ADT State Machine
 *
 * We have an Event / State application here,
 * we're managing a Concert (in-memory),
 * and we need to implement [action].
 *
 * Implement [action] using pattern matching,
 * take into account that users might want to buy more tickets than are available!
 * Tip: In the case there are not enough remaining tickets, we sell the leftovers.
 */
sealed interface ConcertAction {
  data class SellTickets(val count: Int): ConcertAction
  data object NewDateAvailable: ConcertAction
}

sealed interface ConcertState {
  data class TicketsOnSale(val count: Int): ConcertState
  data object SoldOut: ConcertState
}

data class Concert(
  val capacity: Int,
  val state: ConcertState
) {
  init { require(capacity > 0) }
  constructor(maximumCapacity: Int): this(maximumCapacity, ConcertState.TicketsOnSale(0))
}

// TODO implement function
fun Concert.action(action: ConcertAction): Concert =
  when (action) {
    is ConcertAction.NewDateAvailable -> copy(state = ConcertState.TicketsOnSale(0))
    is ConcertAction.SellTickets -> when (state) {
      is ConcertState.SoldOut -> this
      is ConcertState.TicketsOnSale -> {
        val newCount = state.count + action.count
        when {
          newCount > capacity -> copy(state = ConcertState.SoldOut)
          else -> copy(state = ConcertState.TicketsOnSale(newCount))
        }
      }
    }
  }