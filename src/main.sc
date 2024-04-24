state("hello", (v) => {
  v.reply("Привет! Чем могу помочь?");
});

state("weather", (v) => {
  v.reply("Извините, функция получения прогноза погоды временно недоступна.");
});

state("currency", (v) => {
  v.reply("Извините, функция получения курса валют временно недоступна.");
});

state("NoMatch", (v) => {
  v.reply("Извините, я не понимаю ваш запрос.");
});

intent("Привет", (v) => {
  v.jump("hello");
});

intent("Какая сегодня погода", (v) => {
  v.jump("weather");
});

intent("Какой курс доллара", (v) => {
  v.jump("currency");
});

intent("Какой курс евро", (v) => {
  v.jump("currency");
});

fallback((v) => {
  v.jump("NoMatch");
});
