package br.com.suzintech.listatelefonica.model

data class Contact(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: Int = 0,
    val imageId: Int = 0
)
