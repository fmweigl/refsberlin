package fm.weigl.refsberlin.gameslist.presenter

import android.util.Log
import fm.weigl.refdata.Game
import fm.weigl.refsberlin.base.LoadingState
import fm.weigl.refsberlin.base.UINavigator
import fm.weigl.refsberlin.calendar.CalendarEventCreator
import fm.weigl.refsberlin.di.ActivityScope
import fm.weigl.refsberlin.error.view.ErrorScreenDelegate
import fm.weigl.refsberlin.error.view.IErrorScreen
import fm.weigl.refsberlin.gameslist.net.GamesRepository
import fm.weigl.refsberlin.gameslist.view.GamesListEventDelegate
import fm.weigl.refsberlin.gameslist.view.IGamesListView
import fm.weigl.refsberlin.rx.Schedulers
import javax.inject.Inject

@ActivityScope
class GamesListPresenter @Inject constructor(private val gamesRepository: GamesRepository,
                                             private val schedulers: Schedulers,
                                             private val filter: GamesFilter,
                                             private val eventCreator: CalendarEventCreator,
                                             private val uiNavigator: UINavigator)
    : GamesListEventDelegate, ErrorScreenDelegate {

    companion object {
        const val TAG = "GamesListPresenter"
    }

    lateinit var view: IGamesListView
    lateinit var errorScreen: IErrorScreen
    private var games = listOf<Game>()

    fun loadGames() {

        val loadingState = if (games.isEmpty()) LoadingState.LOADING else LoadingState.REFRESHING

        view.setLoadingState(loadingState)
        errorScreen.hideError()

        gamesRepository.getGames()
                .subscribeOn(schedulers.new())
                .observeOn(schedulers.main())
                .subscribe({

                    this.games = it
                    view.setLoadingState(LoadingState.DONE)
                    showFilteredGames()

                },
                        {
                            Log.e(TAG, it.toString())
                            it.printStackTrace()
                            errorScreen.showError(it.toString())
                            view.setLoadingState(LoadingState.DONE)
                        })


    }

    override fun filterTextChanged() = showFilteredGames()

    override fun eventIconClickedForGame(game: Game) = eventCreator.createEventForGame(game)

    override fun navigationIconClickedForGame(game: Game) = uiNavigator.showNavigationToLocation(game.place.place)

    override fun retryClicked() = loadGames()

    override fun refreshPulled() = loadGames()

    private fun showFilteredGames() {
        val filterText = view.getFilterText()
        view.displayGames(filter.filterGames(games, filterText))
        view.highlightGamesWithText(filterText)
    }
}