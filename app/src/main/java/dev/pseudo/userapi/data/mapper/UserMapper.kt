package dev.pseudo.userapi.data.mapper

import dev.pseudo.userapi.data.model.Coordinates
import dev.pseudo.userapi.data.model.Location
import dev.pseudo.userapi.data.model.Name
import dev.pseudo.userapi.data.model.Picture
import dev.pseudo.userapi.data.model.Street
import dev.pseudo.userapi.data.model.User
import dev.pseudo.userapi.data.model.UserEntity

fun User.toEntity(): UserEntity = UserEntity(
    email = email,
    firstName = name.first,
    lastName = name.last,
    phone = phone,
    city = location.city,
    country = location.country,
    imageUrl = picture.medium,
)

fun UserEntity.toUser(): User = User(
    name = Name("", firstName, lastName),
    location = Location(
        street = Street(0, ""),
        city = city,
        state = "",
        country = country,
        postcode = "",
        coordinates = Coordinates("", ""),
    ),
    email = email,
    phone = phone,
    picture = Picture(imageUrl, imageUrl, imageUrl),
)