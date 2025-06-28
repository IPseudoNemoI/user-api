package dev.pseudo.userapi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val picture: Picture,
) : Parcelable

@Parcelize
data class Name(
    val title: String,
    val first: String,
    val last: String
) : Parcelable

@Parcelize
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: @RawValue Any,
    val coordinates: Coordinates,
) : Parcelable

@Parcelize
data class Street(
    val number: Int,
    val name: String,
) : Parcelable

@Parcelize
data class Coordinates(
    val latitude: String,
    val longitude: String,
) : Parcelable

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String,
) : Parcelable