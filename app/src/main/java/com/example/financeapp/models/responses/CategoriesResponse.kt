package com.example.financeapp.models.responses

data class CategoriesResponse(
    val records: List<Record>
){
    data class Record (
        val id: String, 
        val balanceId: String, 
        val type: String, 
        val title: String,
        val value: Number, 
        val method: String, 
        val category: String, 
        val reccurent: Boolean,
        val repeating: String
    )
}
