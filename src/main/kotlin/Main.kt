import java.io.File

fun fixName(name: String): String {
    val cleaned = name.trim().replace(Regex("\\s+"), " ")

    return when {
        cleaned.contains(" ") -> cleaned
        cleaned.length > 2 -> {
            // Пытаемся разделить слитное имя и фамилию
            val middle = cleaned.length / 2
            cleaned.substring(0, middle) + " " + cleaned.substring(middle)
        }
        else -> ""
    }
}

fun fixAge(age: String): String {
    val digits = age.filter { it.isDigit() }

    return if (digits.isNotEmpty()) digits else ""
}

fun fixPhone(phone: String): String {
    val digits = phone.filter { it.isDigit() }

    return if (digits.length == 11 && digits.startsWith("7")) {
        "+7 (${digits.substring(1, 4)}) ${digits.substring(4, 7)}-${digits.substring(7, 9)}-${digits.substring(9, 11)}"
    } else {
        ""
    }
}

fun fixEmail(email: String): String {
    var corrected = email.trim()

    corrected = corrected.replace(Regex("@+"), "@")
    corrected = corrected.replace(Regex("\\.+"), ".")
    corrected = corrected.replace("@.", "@")
    corrected = corrected.replace(".@", "@")

    val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    return if (regex.matches(corrected)) corrected else ""
}

fun processLine(line: String): String {
    val parts = line.split("|")

    val name = if (parts.isNotEmpty()) fixName(parts[0]) else ""
    val age = if (parts.size > 1) fixAge(parts[1]) else ""
    val phone = if (parts.size > 2) fixPhone(parts[2]) else ""
    val email = if (parts.size > 3) fixEmail(parts[3]) else ""

    return "$name|$age|$phone|$email"
}

fun main() {
    val inputFile = File("input.txt")
    val outputFile = File("output.txt")

    val result = inputFile.readLines().map { processLine(it) }

    outputFile.writeText(result.joinToString("\n"))

    println("Обработка завершена. Результат записан в output.txt")
}