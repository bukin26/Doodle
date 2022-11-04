import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class MenuScene() : Scene() {

    override suspend fun SContainer.sceneInit() {

        val background = image(resourcesVfs["background.png"].readBitmap())

        val myFont = TtfFont(resourcesVfs["font.ttf"].readAll())

        val startButton = image(resourcesVfs["smallButton.png"].readBitmap()).centerOnStage().onDown {
            sceneContainer.changeTo<PlayScene>()
        }
        val startText = text("Start Game", 45.00, Colors.BLACK, myFont).centerOn(startButton!!)

        val privacyButton =
            image(resourcesVfs["smallButton.png"].readBitmap()).centerXOnStage().alignTopToBottomOf(startButton, 50)
        val privacyText = text("Privacy Policy", 45.00, Colors.BLACK, myFont).centerOn(privacyButton)
    }
}
