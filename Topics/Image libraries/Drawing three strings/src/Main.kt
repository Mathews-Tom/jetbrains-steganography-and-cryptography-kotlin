import java.awt.Color
import java.awt.image.BufferedImage

const val FIFTY = 50
const val ONE = 1
const val TWO = 2
const val HEIGHT: Int = 200
const val WIDTH: Int = 200

fun drawStrings(): BufferedImage {
    val image = BufferedImage(HEIGHT, WIDTH, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    val message = "Hello, images!"
    graphics.color = Color.RED
    graphics.drawString(message, FIFTY, FIFTY)
    graphics.color = Color.GREEN
    graphics.drawString(message, FIFTY + ONE, FIFTY + ONE)
    graphics.color = Color.BLUE
    graphics.drawString(message, FIFTY + TWO, FIFTY + TWO)
    graphics.drawImage(image, null, 0, 0)
    return image
}