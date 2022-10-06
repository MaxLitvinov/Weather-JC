package com.jc.weather.pages.home

import com.jc.weather.home_page.mapper.DailyDomainModelMapper
import com.jc.weather.home_page.mapper.WeatherDomainModelMapper
import com.jc.weather.home_page.model.DayForecast
import com.jc.weather.home_page.model.WeatherModel
import com.jc.weather.open_weather_map.domain.model.CurrentWeatherDomainModel
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.FeelsLikeDomainModel
import com.jc.weather.open_weather_map.domain.model.TemperatureDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDetailsDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherDomainModelMapperTest {

    private lateinit var dailyDomainModelMapper: DailyDomainModelMapper
    private lateinit var weatherDomainModelMapper: WeatherDomainModelMapper

    @Before
    fun setup() {
        dailyDomainModelMapper = mockk()
        weatherDomainModelMapper = WeatherDomainModelMapper(dailyDomainModelMapper)
    }

    @Test
    fun `Check domain model mapping into UI model`() {
        val todayDomain = DailyDomainModel(time = 1663146000, sunriseTime = 1663126067, sunsetTime = 1663172008, moonriseTime = 1663177560, moonsetTime = 1663143240, moonPhase = 0.64F, temperature = TemperatureDomainModel(day = 15.07F, min = 9.34F, max = 17.26F, night = 11.26F, evening = 12.31F, morning = 9.34F), feelsLike = FeelsLikeDomainModel(day = 14.43F, night = 10.9F, evening = 11.97F, morning = 8.52F), pressure = 1005, humidity = 69, dewPoint = 9.46F, windSpeed = 6.0F, windDeg = 254, windGust = 8.66F, clouds = 93, pop = 0.57F, rain = 2.39F, uvIndex = 2.34F, snow = null)
        val tomorrowDomain = DailyDomainModel(time = 1663232400, sunriseTime = 1663212562, sunsetTime = 1663258269, moonriseTime = 1663265160, moonsetTime = 1663234260, moonPhase = 0.67F, temperature = TemperatureDomainModel(day = 18.2F, min = 11.13F, max = 20.8F, night = 14.32F, evening = 17.43F, morning = 11.64F), feelsLike = FeelsLikeDomainModel(day = 17.96F, night = 14.31F, evening = 17.24F, morning = 11.31F), pressure = 1004, humidity = 72, dewPoint = 13.16F, windSpeed = 5.45F, windDeg = 210, windGust = 6.75F, clouds = 100, pop = 0.2F, rain = 0.44F, uvIndex = 3.32F, snow = null)
        val day3Domain = DailyDomainModel(time = 1663318800, sunriseTime = 1663299057, sunsetTime = 1663344530, moonriseTime = 1663353120, moonsetTime = 1663325160, moonPhase = 0.7F, temperature = TemperatureDomainModel(day = 15.62F, min = 12.63F, max = 20.5F, night = 14.1F, evening = 16.95F, morning = 12.63F), feelsLike = FeelsLikeDomainModel(day = 15.54F, night = 13.24F, evening = 16.42F, morning = 12.51F), pressure = 1003, humidity = 88, dewPoint = 13.51F, windSpeed = 5.91F, windDeg = 266, windGust = 8.47F, clouds = 100, pop = 0.22F, rain = 0.37F, uvIndex = 3.22F, snow = null)
        val day4Domain = DailyDomainModel(time = 1663405200, sunriseTime = 1663385552, sunsetTime = 1663430790, moonriseTime = 1663441500, moonsetTime = 1663415700, moonPhase = 0.73F, temperature = TemperatureDomainModel(day = 16.87F, min = 11.16F, max = 16.87F, night = 12.45F, evening = 13.06F, morning = 11.88F), feelsLike = FeelsLikeDomainModel(day = 16.07F, night = 11.89F, evening = 12.51F, morning = 10.95F), pressure = 1006, humidity = 56, dewPoint = 8.17F, windSpeed = 4.34F, windDeg = 215, windGust = 4.47F, clouds = 100, pop = 0.17F, uvIndex = 2.53F, rain = null, snow = null)
        val day5Domain = DailyDomainModel(time = 1663491600, sunriseTime = 1663472047, sunsetTime = 1663517051, moonriseTime = 1663530540, moonsetTime = 1663505760, moonPhase = 0.75F, temperature = TemperatureDomainModel(day = 11.49F, min = 9.91F, max = 18.69F, night = 9.91F, evening = 18.69F, morning = 11.99F), feelsLike = FeelsLikeDomainModel(day = 11.12F, night = 6.42F, evening = 18.91F, morning = 11.23F), pressure = 999, humidity = 93, dewPoint = 10.26F, windSpeed = 10.2F, windDeg = 163, windGust = 17.1F, clouds = 100, pop = 1.0F, rain = 10.49F, uvIndex = 0.24F, snow = null)
        val day6Domain = DailyDomainModel(time = 1663578000, sunriseTime = 1663558542, sunsetTime = 1663603311, moonriseTime = 1663620300, moonsetTime = 1663595100, moonPhase = 0.79F, temperature = TemperatureDomainModel(day = 13.92F, min = 8.35F, max = 15.78F, night = 9.08F, evening = 13.64F, morning = 9.3F), feelsLike = FeelsLikeDomainModel(day = 12.88F, night = 6.88F, evening = 12.65F, morning = 5.28F), pressure = 1007, humidity = 58, dewPoint = 5.73F, windSpeed = 10.52F, windDeg = 249, windGust = 17.94F, clouds = 5, pop = 0.23F, rain = 0.19F, uvIndex = 1.0F, snow = null)
        val day7Domain = DailyDomainModel(time = 1663664400, sunriseTime = 1663645037, sunsetTime = 1663689572, moonriseTime = 0, moonsetTime = 1663683840, moonPhase = 0.82F, temperature = TemperatureDomainModel(day = 11.98F, min = 7.19F, max = 12.35F, night = 11.08F, evening = 12.35F, morning = 7.19F), feelsLike = FeelsLikeDomainModel(day = 11.24F, night = 10.46F, evening = 11.73F, morning = 4.28F), pressure = 1014, humidity = 77, dewPoint = 8.06F, windSpeed = 6.64F, windDeg = 257, windGust = 11.41F, clouds = 100, pop = 0.27F, rain = 0.12F, uvIndex = 1.0F, snow = null)
        val day8Domain = DailyDomainModel(time = 1663750800, sunriseTime = 1663731533, sunsetTime = 1663775832, moonriseTime = 1663710600, moonsetTime = 1663771920, moonPhase = 0.85F, temperature = TemperatureDomainModel(day = 14.76F, min = 8.89F, max = 16.06F, night = 12.74F, evening = 14.19F, morning = 8.89F), feelsLike = FeelsLikeDomainModel(day = 13.78F, night = 11.66F, evening = 13.21F, morning = 7.93F), pressure = 1021, humidity = 57, dewPoint = 6.44F, windSpeed = 3.93F, windDeg = 35, windGust = 8.24F, clouds = 96, pop = 0.2F, rain = 0.13F, uvIndex = 1.0F, snow = null)
        val currentWeather = CurrentWeatherDomainModel(currentTime = 1663109294, sunriseTime = 1663126067, sunsetTime = 1663172008, temperature = 9.69F, feelsLike = 8.63F, pressure = 1006, humidity = 94, dewPoint = 8.77F, uvIndex = 0.0F, clouds = 100, visibility = 10000, windSpeed = 2.26F, windDeg = 317, windGust = 5.95F, weatherDetails = WeatherDetailsDomainModel(id = 500, mainDescription = "Rain", detailedDescription = "light rain", iconUrl = "https://openweathermap.org/img/wn/10n@2x.png"), lastHourRainVolume = 0.12F, lastHourSnowVolume = null)

        val domainModel = WeatherDomainModel(latitude = 51.5008F, longitude = 31.2945F, timezone = "Europe/Kiev", timezoneOffset = 10800, currentWeather = currentWeather, dailyForecasts = listOf(todayDomain, tomorrowDomain, day3Domain, day4Domain, day5Domain, day6Domain, day7Domain, day8Domain))

        val today = DayForecast("Today", 0.0F, 11.52F, 0.0F, 10.16F, 0.0F, 0.0F)
        val tomorrow = DayForecast("Tomorrow", 0.0F, 16.1F, 0.0F, 11.97F, 0.0F, 0.0F)
        val thursday = DayForecast("15.09 Thursday", 0.0F, 17.9F, 0.0F, 12.89F, 0.0F, 0.0F)
        val friday = DayForecast("16.09 Friday", 0.0F, 17.68F, 0.0F, 16.12F, 0.0F, 0.0F)
        val saturday = DayForecast("17.09 Saturday", 0.0F, 13.14F, 0.0F, 10.54F, 0.0F, 0.0F)
        val sunday = DayForecast("18.09 Sunday", 0.0F, 13.34F, 0.0F, 13.15F, 0.0F, 0.0F)
        val monday = DayForecast("19.09 Monday", 0.0F, 10.93F, 0.0F, 10.92F, 0.0F, 0.0F)
        val tuesday = DayForecast("20.09 Tuesday", 0.0F, 14.94F, 0.0F, 11.3F, 0.0F, 0.0F)

        every { dailyDomainModelMapper.mapToUiModel(todayDomain) } returns today
        every { dailyDomainModelMapper.mapToUiModel(tomorrowDomain) } returns tomorrow
        every { dailyDomainModelMapper.mapToUiModel(day3Domain) } returns thursday
        every { dailyDomainModelMapper.mapToUiModel(day4Domain) } returns friday
        every { dailyDomainModelMapper.mapToUiModel(day5Domain) } returns saturday
        every { dailyDomainModelMapper.mapToUiModel(day6Domain) } returns sunday
        every { dailyDomainModelMapper.mapToUiModel(day7Domain) } returns monday
        every { dailyDomainModelMapper.mapToUiModel(day8Domain) } returns tuesday

        val actualUiModel = weatherDomainModelMapper.mapToUiModel(domainModel)

        val expectedUiModel = WeatherModel(
            "",
            "https://openweathermap.org/img/wn/10n@2x.png",
            "9.69",
            "Light rain",
            listOf(today, tomorrow, thursday, friday, saturday, sunday, monday, tuesday)
        )

        assertEquals(expectedUiModel, actualUiModel)
    }
}
