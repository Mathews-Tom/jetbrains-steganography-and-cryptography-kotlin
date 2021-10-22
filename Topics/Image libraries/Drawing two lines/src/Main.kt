import java.awt.Color
import java.awt.image.BufferedImage

const val HEIGHT: Int = 200
const val WIDTH: Int = 200
const val ZERO = 0

fun drawLines(): BufferedImage {
    val image = BufferedImage(HEIGHT, WIDTH, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawLine(ZERO, ZERO, WIDTH, HEIGHT)
    graphics.color = Color.GREEN
    graphics.drawLine(WIDTH, ZERO, ZERO, HEIGHT)
    graphics.drawImage(image, null, 0, 0)
    return image
}