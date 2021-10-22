package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.xml.ws.LogicalMessage
import kotlin.experimental.xor

fun main() {
    while (true) {
        println("Task (hide, show, exit): ")
        when (val inputString = readLine()!!) {
            "exit" -> {
                println("Bye!")
                break
            }
            "hide" -> hideMessage()
            "show" -> showMessage()
            else -> println("Wrong task: $inputString")
        }
    }
}

fun hideMessage() {
    println("Input image file: ")
    val inputFilename = readLine()!!
    println("Output image file: ")
    val outputFilename = readLine()!!
    println("Message to hide: ")
    val message = readLine()!!.encodeToByteArray()
    println("Password: ")
    val password = readLine()!!.encodeToByteArray()
    val inputFile = File(inputFilename)
    if (inputFile.isFile) {
        val inputImage = ImageIO.read(inputFile)
        val numberOfPixels = inputImage.width * inputImage.height
        if (message.size * 8 <= numberOfPixels) {
            val outputImage = encodeMessageToImage(inputImage, message, password)
            val outputFile = File(outputFilename)
            ImageIO.write(outputImage, "png", outputFile)
            println("Message saved in $outputFilename image.")
        } else {
            println("The input image is not large enough to hold this message.")
        }
    } else {
        println("Can't read input file!")
    }
}

fun showMessage() {
    println("Input image file: ")
    val inputFilename = readLine()!!
    println("Password: ")
    val password = readLine()!!.encodeToByteArray()
    val inputFile = File(inputFilename)
    if (inputFile.isFile) {
        val inputImage = ImageIO.read(inputFile)
        val message = decodeMessageFromImage(inputImage, password)
        println("Message:")
        println(message)
    } else {
        println("Can't read input file!")
    }
}

fun encodeMessageToImage(image: BufferedImage, message: ByteArray, password: ByteArray): BufferedImage {
    val secretMessage = byteArrayOf(0x0, 0x0, 0x3)
    var bitIndex = 0
    var messageByteIndex = 0
    val resizedPassword = password.resize(message.size)
    val encryptedMessage =  xorMessage(message, resizedPassword) + secretMessage
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            val r = color.red
            val g = color.green
            var b = color.blue
            if (messageByteIndex < encryptedMessage.size) {
                val messageByte = encryptedMessage[messageByteIndex].toInt()
                b = updateLSB(b, messageByte.toBinary(8)[bitIndex])
                bitIndex++
                if (bitIndex > 7) {
                    messageByteIndex++
                    bitIndex = 0
                }
            }
            val newColor = Color(r, g, b)
            image.setRGB(x, y, newColor.rgb)
        }
    }
    return image
}

fun decodeMessageFromImage(image: BufferedImage, password: ByteArray): String {
    val rollingByteArray = byteArrayOf(0x0, 0x0, 0x0)
    val secretMessage = byteArrayOf(0x0, 0x0, 0x3)
    var bitIndex = 0
    var byteString = ""
    var messageByteArray = mutableListOf<Byte>()
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            val b = color.blue
            if (!rollingByteArray.contentEquals(secretMessage)) {
                byteString += b.toBinary(8)[7]
                bitIndex++
                if (bitIndex > 7) {
                    rollingByteArray[0] = rollingByteArray[rollingByteArray.lastIndex - 1]
                    rollingByteArray[rollingByteArray.lastIndex - 1] = rollingByteArray[rollingByteArray.lastIndex]
                    rollingByteArray[rollingByteArray.lastIndex] = byteString.toByte(2)
                    messageByteArray.add(byteString.toByte(2))
                    byteString = ""
                    bitIndex = 0
                }
            }
        }
    }
    messageByteArray = messageByteArray.subList(0, messageByteArray.lastIndex - 2)
    val decryptedMessage = xorMessage(messageByteArray.toByteArray(), password.resize(messageByteArray.size))
    return decryptedMessage.toString(Charsets.UTF_8)
}

fun updateLSB(x: Int, y: Char): Int {
    val xBinary = x.toBinary(8)
    val newX = (xBinary.subSequence(0, xBinary.length - 1)).toString() + y
    return newX.toInt(2)
}

fun Int.toBinary(length: Int): String = String.format("%" + length + "s", this.toString(2)).replace(" ", "0")

fun ByteArray.resize(length: Int): ByteArray {
    val byteArray = ByteArray(length)
    for (i in byteArray.indices) byteArray[i] = this[i % this.size]
    return byteArray
}

fun xorMessage(message: ByteArray, resizedPassword: ByteArray): ByteArray {
    for (i in message.indices) {
        message[i] = message[i] xor resizedPassword[i]
    }
    return message
}
