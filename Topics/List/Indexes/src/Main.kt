fun solution(products: List<String>, product: String) {
    val productIndices = products.mapIndexedNotNull { index, elem -> if (elem == product) index else null }
    println(productIndices.joinToString(" "))
}