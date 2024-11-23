package com.harshjoshi.aroundthecorner

fun removeNonAlphanumericFromStartAndEnd(input: String): String {
    // Remove non-alphanumeric characters from the start and end of the string
    val cleanedString = input.replaceFirst(Regex("^[^a-zA-Z0-9]+"), "")
        .replaceLast(Regex("[^a-zA-Z0-9]+$"), "")

    // Now remove everything after the last non-space character (including spaces)
    val indexOfLastSpace = cleanedString.lastIndexOf(' ')
    return if (indexOfLastSpace >= 0) {
        cleanedString.substring(0, indexOfLastSpace)  // Keep only up to the last space
    } else {
        cleanedString // If no spaces, return the cleaned string itself
    }
}

fun String.replaceLast(regex: Regex, replacement: String): String {
    val matchResult = regex.find(this)
    return if (matchResult != null) {
        this.replaceRange(matchResult.range, replacement)
    } else {
        this
    }
}