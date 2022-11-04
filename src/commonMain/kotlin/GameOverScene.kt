import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameOverScene(private val score: Int) : Scene() {

    override suspend fun SContainer.sceneInit() {

        val background = image(resourcesVfs["background.png"].readBitmap())

        val myFont = TtfFont(resourcesVfs["font.ttf"].readAll())

        val gameOverText = text("Game over", 45.00, Colors.BLACK, myFont).centerOnStage()
        val scoreText = text("Your score is $score", 45.00, Colors.BLACK, myFont).centerXOnStage()
            .alignTopToBottomOf(gameOverText, 10.00)

        val restartButton = image(resourcesVfs["smallButton.png"].readBitmap()).centerXOnStage()
            .alignTopToBottomOf(scoreText, 20.00).onDown {
                sceneContainer.changeTo<PlayScene>()
            }
        val restartText = text("Restart", 45.00, Colors.BLACK, myFont).centerOn(restartButton!!)

        val exitButton =
            image(resourcesVfs["smallButton.png"].readBitmap())
                .centerXOnStage()
                .alignTopToBottomOf(restartButton, 20.00)
                .onDown {
                    views.gameWindow.close()
                }
        val exitText = text("Exit", 45.00, Colors.BLACK, myFont).centerOn(exitButton!!)
    }


    override suspend fun SContainer.sceneMain() {

    }
}
