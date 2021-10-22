import java.awt.Color
import java.awt.image.BufferedImage

const val HUNDRED = 100
const val THREE_HUNDRED = 300
const val HEIGHT: Int = 500
const val WIDTH: Int = 500

fun drawSquare(): BufferedImage {
    val image = BufferedImage(HEIGHT, WIDTH, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawRect(HUNDRED, HUNDRED, THREE_HUNDRED, THREE_HUNDRED)
    graphics.drawImage(image, null, 0, 0)
    return image
}