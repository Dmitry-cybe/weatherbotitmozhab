import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.jaicp.logging.chatapi
import com.justai.jaicf.channel.jaicp.reactions.jaicp
import com.justai.jaicf.helpers.logging.WithLogger
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.reactions.Reactions
import com.justai.jaicf.template.scenario.main

object MainScenario : Scenario(), WithLogger {

    override fun handle(request: BotRequest, reactions: Reactions) {
        val intent = request.processedIntent()

        when (intent) {
            "/hello" -> reactions.say("Приветствую!")
            "/weather" -> {
                val weatherInfo = getWeather()
                reactions.say(weatherInfo)
            }
            "/currency" -> {
                val currencyInfo = getCurrency()
                reactions.say(currencyInfo)
            }
            else -> reactions.say("Извините, я не понимаю ваш запрос.")
        }
    }

    private fun getWeather(): String {
        val city = "Moscow" // Замените на нужный город
        val weatherApiUrl = "http://api.openweathermap.org/data/2.5/weather?q=$city&appid=$WEATHER_API_KEY&units=metric"
        val response = requests.get(weatherApiUrl)
        val data = response.json()
        return if (data["cod"] == 200) {
            val temperature = data["main"]["temp"]
            val description = data["weather"][0]["description"]
            "Погода в $city: $description. Температура: $temperature°C"
        } else {
            "Извините, не удалось получить информацию о погоде."
        }
    }

    private fun getCurrency(): String {
        val currencyApiUrl = "https://open.er-api.com/v6/latest"
        val response = requests.get(currencyApiUrl)
        val data = response.json()
        return if (response.status_code == 200) {
            val usdRate = data["rates"]["RUB"]
            "Курс доллара к рублю: $usdRate"
        } else {
            "Извините, не удалось получить информацию о курсе валют."
        }
    }

}
