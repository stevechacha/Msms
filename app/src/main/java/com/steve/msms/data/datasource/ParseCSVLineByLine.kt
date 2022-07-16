package com.steve.msms.data.datasource

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@Throws(IOException::class)
fun File.readAsCSV(): List<List<String>> {
    val splitLines = mutableListOf<List<String>>()
    forEachLine {
        splitLines += it.split(", ")
    }
    return splitLines
}

@Throws(IOException::class)
fun File.writeAsCSV(values: List<List<String>>) {
    val csv = values.joinToString("\n") { line -> line.joinToString(", ") }
    writeText(csv)
}


@Throws(IOException::class)
fun FileInputStream.readAsCSV() : List<List<String>> {
    val splitLines = mutableListOf<List<String>>()
    reader().buffered().forEachLine {
        splitLines += it.split(", ")
    }
    return splitLines
}

@Throws(IOException::class)
fun FileOutputStream.writeAsCSV(values: List<List<String>>) {
    val csv = values.joinToString("\n") { line -> line.joinToString(", ") }
    writer().buffered().use { it.write(csv) }
}
