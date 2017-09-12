package fm.weigl.refsberlin.di

import android.content.res.Resources
import dagger.Component
import fm.weigl.refsberlin.base.App
import fm.weigl.refsberlin.gameslist.net.GamesRepository
import fm.weigl.refsberlin.rx.Schedulers
import fm.weigl.refsberlin.update.net.AppVersionRepository
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, WebserviceModule::class))
interface AppComponent {
    fun injectTo(app: App)
    fun gamesRepository(): GamesRepository
    fun appVersionRepository(): AppVersionRepository
    fun schedulers(): Schedulers
    fun resources(): Resources
}