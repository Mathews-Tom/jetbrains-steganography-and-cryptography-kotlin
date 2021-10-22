fun square(value: Int, context: Any, continuation: (Int, Any) -> Unit) {
    return continuation(value * value, context)
}