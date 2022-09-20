package com.jc.weather.pages.details.model

data class CollapsedModel(
    val id: Int,
    var isExpanded: Boolean,
    val time: String,
    val iconUrl: String,
    val humidity: Int,
    val generalDescription: String,
    val temperature: Float,
    val details: ExpandedModel,
)

data class ExpandedModel(
    val feelsLike: Float,
    val pressure: Int,
    val dewPoint: Float,
    val uvIndex: Float,
    val clouds: Int,
    val visibility: Int,
    val rain: Float,
    val wind: WindModel,
    val detailedDescription: String,
)

data class WindModel(
    val speed: Float,
    val deg: Int,
    val gust: Float,
)
