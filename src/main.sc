import requests
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters

# Токен вашего бота Telegram
TOKEN = "7181487125:AAH-WQ7lJduNtMWiJGiTqEQDkPJgJN_Z3-Q"

# API ключ для OpenWeatherMap
WEATHER_API_KEY = "bd5e378503939ddaee76f12ad7a97608"

# API ключ для Open Exchange Rates
CURRENCY_API_KEY = "2867f356612532794eedd029"

def start(update, context):
    update.message.reply_text("Привет! Я бот-помощник. Чем могу помочь?")

def hello(update, context):
    update.message.reply_text("Привет!")

def weather(update, context):
    city = "Moscow"  # Замените на нужный город
    weather_api_url = f"http://api.openweathermap.org/data/2.5/weather?q={city}&appid={WEATHER_API_KEY}&units=metric"
    response = requests.get(weather_api_url)
    data = response.json()
    if data.get("cod") == 200:
        temperature = data["main"]["temp"]
        description = data["weather"][0]["description"]
        update.message.reply_text(f"Погода в {city}: {description}. Температура: {temperature}°C")
    else:
        update.message.reply_text("Извините, не удалось получить информацию о погоде.")

def currency(update, context):
    currency_api_url = f"https://open.er-api.com/v6/latest"
    response = requests.get(currency_api_url)
    data = response.json()
    if response.status_code == 200:
        usd_rate = data["rates"]["RUB"]
        update.message.reply_text(f"Курс доллара к рублю: {usd_rate}")
    else:
        update.message.reply_text("Извините, не удалось получить информацию о курсе валют.")

def no_match(update, context):
    update.message.reply_text("Извините, я не понимаю ваш запрос.")

def main():
    updater = Updater(TOKEN, use_context=True)
    dp = updater.dispatcher

    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(CommandHandler("hello", hello))
    dp.add_handler(CommandHandler("weather", weather))
    dp.add_handler(CommandHandler("currency", currency))
    dp.add_handler(MessageHandler(Filters.command, no_match))

    updater.start_polling()
    updater.idle()

if __name__ == '__main__':
    main()
