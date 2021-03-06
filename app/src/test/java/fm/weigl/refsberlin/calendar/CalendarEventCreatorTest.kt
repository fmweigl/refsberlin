package fm.weigl.refsberlin.calendar

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import fm.weigl.refsberlin.R
import fm.weigl.refsberlin.TestGames
import fm.weigl.refsberlin.TestGames.Companion.placeName
import fm.weigl.refsberlin.TestGames.Companion.ref1Name
import fm.weigl.refsberlin.TestGames.Companion.ref1Pos
import fm.weigl.refsberlin.TestGames.Companion.ref2Name
import fm.weigl.refsberlin.TestGames.Companion.ref2Pos
import fm.weigl.refsberlin.TestGames.Companion.startTime
import fm.weigl.refsberlin.TestGames.Companion.team1Name
import fm.weigl.refsberlin.TestGames.Companion.team2Name
import fm.weigl.refsberlin.base.UINavigator
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify

class CalendarEventCreatorTest {

    val uiNavigator: UINavigator = mock()
    val resources: Resources = mock()

    val ref1Representation = "ref1Repr"
    val ref2Representation = "ref2Repr"
    val eventRepresentation = "eventRepresentation"
    val crew = "Crew:"

    val game = TestGames.testGame

    val classToTest = CalendarEventCreator(uiNavigator, resources)

    @Before
    fun setUp() {
        given(resources.getString(R.string.crew)).willReturn(crew)
        given(resources.getString(R.string.match_representation, team1Name, team2Name)).willReturn(eventRepresentation)
        given(resources.getString(R.string.referee_representation, ref1Name, ref1Pos)).willReturn(ref1Representation)
        given(resources.getString(R.string.referee_representation, ref2Name, ref2Pos)).willReturn(ref2Representation)
    }

    @Test
    fun createsEvent() {

        classToTest.createEventForGame(game)

        val endTime = startTime + CalendarEventCreator.THREE_HOURS

        val crewRepresentation = "$crew\n$ref1Representation\n$ref2Representation"

        verify(uiNavigator).createCalendarEventForGame(startTime, endTime, eventRepresentation, crewRepresentation, placeName)

    }

}