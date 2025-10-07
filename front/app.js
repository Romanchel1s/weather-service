const API_BASE = 'http://localhost:8080/weather';

document.addEventListener('DOMContentLoaded', () => {
  const cityInput = document.getElementById('cityInput');
  const loadBtn = document.getElementById('loadBtn');
  const message = document.getElementById('message');
  const ctx = document.getElementById('tempChart').getContext('2d');
  let chart = null;

  loadBtn.addEventListener('click', () => {
    const city = cityInput.value.trim();
    if (!city) {
      showMessage('Введите название города', true);
      return;
    }
    loadWeather(city);
  });

async function loadWeather(city) {
    showMessage('Загрузка...', false);
    try {
        const res = await fetch(`${API_BASE}?city=${encodeURIComponent(city)}`);
        if (!res.ok) throw new Error(`Ошибка ${res.status}: ${res.statusText}`);
        const data = await res.json();

        if (!data.hourly || !data.hourly.time || !data.hourly.temperature_2m) {
            throw new Error('Некорректный формат данных');
        }

        const times = data.hourly.time.map(t => t.slice(11)); // оставляем только HH:MM
        const temps = data.hourly.temperature_2m;

        renderChart(city, times, temps); // передаём отдельные значения
        showMessage('', false);
    } catch (err) {
        console.error(err);
        showMessage('Ошибка: ' + err.message, true);
    }
}

function renderChart(city, labels, temps) {
    if (chart) chart.destroy();
    chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels,
            datasets: [{
                label: `Температура, °C (${city})`,
                data: temps,
                borderColor: 'rgb(54, 162, 235)',
                tension: 0.3,
                fill: false,
                pointRadius: 3
            }]
        },
        options: {
            scales: {
                x: { title: { display: true, text: 'Часы' } },
                y: { title: { display: true, text: 'Температура °C' } }
            }
        }
    });
}


  function showMessage(text, isError = false) {
    if (!text) {
      message.className = 'hidden';
      message.textContent = '';
      return;
    }
    message.className = 'message' + (isError ? ' error' : '');
    message.textContent = text;
  }
});
