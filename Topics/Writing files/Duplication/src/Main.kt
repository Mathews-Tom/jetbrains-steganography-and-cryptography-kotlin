// Write your code here. Do not import any libraries
val text = readLine()!!
val fileName = "MyFile.txt"
val content = text.repeat(2)
val myFile = File(fileName)
myFile.writeText(content)