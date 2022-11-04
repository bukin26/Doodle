import com.soywiz.klock.*
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*

class PlayScene : Scene() {

    override suspend fun SContainer.sceneInit() {

        var score = 0
        var speedY = -25.0f
        var speedX = 0.0f
        val gravity = 0.5f
        var isGameOver = false

        val background = image(resourcesVfs["background.png"].readBitmap())

        val left = image(resourcesVfs["left.png"].readBitmap())
            .xy(views.virtualWidth / 2 - 300, views.virtualHeight - 150)
            .alpha(0.5)
            .onDown {
                speedX = -10.0f
            }
            .onUp {
                speedX = 0.0f
            }

        val right = image(resourcesVfs["right.png"].readBitmap())
            .xy(views.virtualWidth / 2 + 100, views.virtualHeight - 150)
            .alpha(0.5)
            .onDown {
                speedX = 10.0f
            }
            .onUp {
                speedX = 0.0f
            }

        val platforms = mutableListOf<Image>()
        for (i in 100..1300 step 200) {
            val platform = image(resourcesVfs["platform.png"].readBitmap())
            platform.randomPosition(i.toDouble())
            platforms.add(platform)
        }

        val coins = mutableListOf<Sprite>()
        for (i in 100..1300 step 400) {
            val coinMap = resourcesVfs["coin.png"].readBitmap()
            val coinAnimation = SpriteAnimation(
                spriteMap = coinMap,
                spriteWidth = 56,
                spriteHeight = 50,
                columns = 6,
                rows = 1
            )
            val coin = sprite(coinAnimation)
                .xy(5.00 + (0..681).random(), views.virtualHeight - i.toDouble() - (0..200).random())
            coin.playAnimationLooped(spriteDisplayTime = 0.2.seconds)
            coins.add(coin)
        }

        val doodleMap = resourcesVfs["doodle.png"].readBitmap()
        val doodleAnimation = SpriteAnimation(
            spriteMap = doodleMap,
            spriteWidth = 128,
            spriteHeight = 100,
            columns = 2,
            rows = 1
        )
        val doodle = sprite(doodleAnimation).xy(views.virtualWidth / 2 - 25, views.virtualHeight - 100)

        right.onDown {
            doodle.playAnimation(spriteDisplayTime = 0.0.seconds, startFrame = 1, endFrame = 1, times = 1)
        }
        left.onDown {
            doodle.playAnimation(spriteDisplayTime = 0.0.seconds, startFrame = 0, endFrame = 0, times = 1)
        }

        coins.forEach { sprite ->
            sprite.onCollision({ it == doodle }) {
                sprite.changePosition()
                score++
            }
        }

        addOptFixedUpdater(TimeSpan(16.0)) {

            if (!isGameOver) {
                if (doodle.x < 0 && speedX < 0) {
                    doodle.x = views.virtualWidth + doodle.x
                }
                if (
                    doodle.x > (views.virtualWidth - doodle.width) && speedX > 0
                ) {
                    doodle.x = 0 - views.virtualWidth + doodle.x
                }

                doodle.x += speedX

                if (doodle.y < 700) {
                    if (speedY < 0) {
                        platforms.forEach {
                            it.y -= speedY
                        }
                        coins.forEach {
                            it.y -= speedY
                        }
                    } else {
                        doodle.y += speedY
                    }
                } else if (doodle.y > views.virtualHeight) {
                    isGameOver = true
                } else {
                    if (speedY < 0) {
                        doodle.y += speedY
                    } else {
                        doodle.y += speedY
                        doodle.onCollision({ platforms.contains(it) }, null) {
                            if (speedY > 2) {
                                speedY = -25.0f
                            }
                        }
                    }
                }

                speedY += gravity

                platforms.forEach {
                    it.checkPosition()
                }
                coins.forEach {
                    it.checkPosition()
                }
            } else {
                left?.alpha = 0.0
                right?.alpha = 0.0
                speedY = 30f
                platforms.forEach {
                    it.y -= speedY
                }
                coins.forEach {
                    it.y -= speedY
                }
                if (doodle.y < 720.00) {
                    launchImmediately {
                        sceneContainer.changeTo<GameOverScene>(score)
                    }
                } else {
                    doodle.y -= speedY / 2
                }
            }
        }
    }

    private fun Image.checkPosition() {
        if (this.y > 1440) {
            this.y = 0.00 - (0..50).random()
            this.x = 5.00 + (0..681).random()
        }
    }

    private fun Sprite.checkPosition() {
        if (this.y > 1440) {
            this.y = 0.00 - (0..150).random()
            this.x = 5.00 + (0..681).random()
        }
    }

    private fun Sprite.changePosition() {
        this.y = 0.00 - (0..1440).random()
        this.x = 5.00 + (0..681).random()
    }

    private fun Image.randomPosition(y: Double) {
        if (y == 100.00) {
            this.x = views.virtualWidth / 2 - 57.00
            this.y = views.virtualHeight - y
        } else {
            this.x = 5.00 + (0..681).random()
            this.y = views.virtualHeight - y + +(0..50).random()
        }
    }
}

