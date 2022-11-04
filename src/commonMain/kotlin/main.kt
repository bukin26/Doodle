import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*


suspend fun main() = Korge(Korge.Config(module = MyModule, title = "Doodle Jump", icon = "icon.png"))

object MyModule : Module() {

    override val mainScene = MenuScene::class

    override val size: SizeInt = SizeInt(800, 1440)

    override suspend fun AsyncInjector.configure() {
        mapPrototype { MenuScene() }
        mapPrototype { PlayScene() }
        mapPrototype { GameOverScene(get()) }
    }
}

