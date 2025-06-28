package dev.pseudo.userapi.data.model

data class User(
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val picture: Picture,
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Any,
    val coordinates: Coordinates,
)

data class Street(
    val number: Int,
    val name: String,
)

data class Coordinates(
    val latitude: String,
    val longitude: String,
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String,
)

