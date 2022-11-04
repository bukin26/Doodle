import com.soywiz.korge.gradle.*

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}
plugins {
	alias(libs.plugins.korge)
}


korge {

	id = "com.gmail.bukin26.mg.bukin26.mg"

    this.orientation = Orientation.PORTRAIT
    this.title = "Doodle"
    androidMinSdk = 16
    androidCompileSdk = 28
    androidTargetSdk = 28


	targetJvm()
    targetAndroid()
}


