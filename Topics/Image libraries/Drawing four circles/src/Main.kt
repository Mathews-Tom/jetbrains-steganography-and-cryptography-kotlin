import java.awt.Color
import java.awt.image.BufferedImage

const val FIFTY = 50
const val SEVENTY_FIVE = 75
const val HUNDRED = 100
const val HEIGHT: Int = 200
const val WIDTH: Int = 200

fun drawCircles(): BufferedImage {
    val image = BufferedImage(HEIGHT, WIDTH, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawOval(FIFTY, FIFTY, HUNDRED, HUNDRED)
    graphics.color = Color.YELLOW
    graphics.drawOval(FIFTY, SEVENTY_FIVE, HUNDRED, HUNDRED)
    graphics.color = Color.GREEN
    graphics.drawOval(SEVENTY_FIVE, FIFTY, HUNDRED, HUNDRED)
    graphics.color = Color.BLUE
    graphics.drawOval(SEVENTY_FIVE, SEVENTY_FIVE, HUNDRED, HUNDRED)
    graphics.drawImage(image, null, 0, 0)
    return image
}