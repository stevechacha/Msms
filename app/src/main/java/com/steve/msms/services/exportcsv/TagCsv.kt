package com.steve.msms.services.exportcsv

import com.opencsv.bean.CsvBindByName
import com.steve.msms.data.local.Tag
import com.steve.msms.domain.model.Message


data class TagCSV(
    @CsvBindByName(column = "tag")
    val tag: String,
    @CsvBindByName(column = "id")
    val id: String,
    @CsvBindByName(column = "note")
    val message: Message

)

fun List<Tag>.toCsv() = map {
    TagCSV(
        tag = it.tag,
        id = it.id,
        message = it.message,

    )
}
