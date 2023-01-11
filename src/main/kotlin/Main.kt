import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception

typealias Filepath = String


fun isFile(filename:Filepath): Boolean {
    val file = File(filename)
    return file.exists()
}

fun readFile(filename:Filepath): Array<String>? {
    return try {
        val file = File(filename)
        var lines = arrayOf<String>()
        file.forEachLine { line ->
            lines += line
        }
        lines
    } catch(e:Exception) {
        when(e) {
            is SecurityException -> println("Access to file $filename is denied")
            is FileNotFoundException -> println("File $filename not found!")
            is IOException -> println("File $filename is currently busy")
            // is OutOfMemoryError -> println("File $filename too large") unreachable
            // is UnsupportedEncodingException -> println("Encoding for file $filename is not supported") unreachable
            else -> println("Error reading file: ${e.message}")
        }
        null
    }
}

fun main(args: Array<String>) {
        //val args = arrayOf(
            //"text_file.txt", // [normal file]
            //"non-existing_file.txt", // [non-existing file]
            //"text_file2.txt", // [normal file]
            //"/etc/shadow", // [permission denied]
            // "--plain",      // [command line args]
            // "--no-banner",  // [command line args]
            // "--no-number"   // [command line args]
        //) // testing purposes

        val commands = arrayOf("--banner", "--number", "--plain")
        var showBanner      = true
        var showLineNumber = true
        for (arg in args) {
            when (arg) {
                "--no-banner" -> showBanner = false
                "--no-number" -> showLineNumber = false
                "--plain"  -> {
                    showBanner = false
                    showLineNumber = false
                }
            }
        }
    println("Program arguments: ${args.joinToString()}\n")
    for (arg in args) {
        if (isFile(arg) && !commands.contains(arg)) {
            //println("isFile($arg) =  ${isFile(arg)}") // Debug purposes
            val lines = readFile(arg)

            if (showBanner && lines != null) {
                println("CONTENTS OF $arg\n${'-'.toString().repeat(12+arg.length)}")
            }
            lines?.forEachIndexed { index, line ->
                if (showLineNumber) {
                    println("${index+1} $line")
                } else {
                    println(line)
                }
            }
            println()
        }
    }
}