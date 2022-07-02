package com.lek.spontan.android.presentation.eventslist.composablecomponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lek.spontan.android.presentation.authentication.AuthScreenRoute
import com.lek.spontan.android.presentation.authentication.login.LoginViewEvent
import com.lek.spontan.android.presentation.authentication.login.composables.LoginScreen
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewEvent
import com.lek.spontan.android.presentation.authentication.signup.composables.SignupScreen
import com.lek.spontan.android.presentation.eventslist.EventScreenRoute
import com.lek.spontan.android.presentation.eventslist.EventsListViewModel
import com.lek.spontan.android.presentation.eventslist.eventdetails.EventDetailViewModel
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailEvent
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailState
import com.lek.spontan.events.data.toDataModel
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData

@Composable
fun EventScreen(
    eventListViewModel: EventsListViewModel,
    eventDetailsViewModel: EventDetailViewModel
) {
    val navController = rememberNavController()
    val eventListViewState by eventListViewModel.uiState.collectAsState()

    NavHost(navController, startDestination = EventScreenRoute.EVENT_LIST) {

        composable(route = EventScreenRoute.EVENT_LIST) {
            EventListScreen(
                events = eventListViewState.events,
                isEmptyState = eventListViewState.isEmptyState,
                isLoading = eventListViewState.isLoading,
                onEventSelected = {
                    eventDetailsViewModel.onEvent(
                        EventDetailEvent.InitialiseData(
                            EventDetailState.EMPTY.copy(
                                title = it.title,
                                description = it.description ?: "",
                                dateData = it.dateData ?: DateData.EMPTY,
                                timeData = it.timeData ?: TimeData.EMPTY,
                            )
                        )
                    )
                    navController.navigate(EventScreenRoute.EVENT_DETAIL)
                }
            ) {
                eventDetailsViewModel.onEvent(
                    EventDetailEvent.InitialiseData(EventDetailState.EMPTY)
                )
                navController.navigate(EventScreenRoute.EVENT_DETAIL)
            }
        }

        composable(route = EventScreenRoute.EVENT_DETAIL) {
            EventDetailScreen(viewModel = eventDetailsViewModel) {
                eventDetailsViewModel.resolveBackClicked()
                navController.navigate(EventScreenRoute.EVENT_LIST)
            }
        }
    }
}