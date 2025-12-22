const datetxtEl = document.querySelector(".date-txt");
const datesEl = document.querySelector(".dates");
const btnEl = document.querySelectorAll(".select-month-year .arrow-key");
const monthYearEl = document.querySelector(".months-year-container");

let dmObj = {
  days: [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ],
  months: [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ],
};

let dateObj = new Date();
let dayName = dmObj.days[dateObj.getDay()];

let month = CALENDAR_MONTH - 1;
let year = CALENDAR_YEAR;
let date = dateObj.getDate();

datetxtEl.innerHTML = `${date} ${dmObj.months[month]} ${year}`;

console.log(deadlineCounts)

const displayCalendar = () => {
    let firtDayOfMonth = new Date(year, month, 1).getDay(); // first day of the month
    let lastDateofMonth = new Date(year, month + 1, 0).getDate(); // last date of the month
    let lastDayofMonth = new Date(year, month, lastDateofMonth).getDay(); // last day of month
    let lastDateofLastMonth = new Date(year, month, 0).getDate(); // last date of previous month
    let days = "";

    for (let i = firtDayOfMonth; i > 0; i--) {
        days +=
            `
            <div class="date-num">
                <div class="num-dim">${lastDateofLastMonth - i + 1}</div>
            </div>`;
    }

    for (let i = 1; i <= lastDateofMonth; i++) {
        let checkToday =
        i === dateObj.getDate() &&
        month === new Date().getMonth() &&
        year === new Date().getFullYear()
            ? "active"
            : "";

        let monthStr = String(month + 1).padStart(2, "0");
        let dayStr = String(i).padStart(2, "0");
        let dateKey = `${year}-${monthStr}-${dayStr}`;
        let taskCount = deadlineCounts[dateKey];

        let taskCountHtml = "";

        if (taskCount) {
            if(taskCount == 1){
                taskCountHtml = `
                    <a
                        class="task-count"
                        href="/tasks-by-date?deadline=${dateKey}"
                    >
                        ${taskCount} Task
                    </a>
                `;
            }
            else{
                taskCountHtml = `
                    <a
                        class="task-count"
                        href="/tasks-by-date?deadline=${dateKey}"
                    >
                        ${taskCount} Tasks
                    </a>
                `;
            }
        }

        days +=
            `
            <div class="${checkToday} date-num">
                <div class="num">${i}</div>
                ${taskCountHtml}
            </div>`;
    }

    for (let i = lastDayofMonth; i < 6; i++) {
        days += `
            <div class="date-num">
                <div class="num-dim">${i - lastDayofMonth + 1}</div>
            </div>`;
    }

    datesEl.innerHTML = days;

    monthYearEl.innerHTML =
        `
            <div class="months-year">${dmObj.months[month]} ${year}</div>
            <img
                class="calendar-icon"
                src="./images/calendar/calendar.svg">
        `;
};

displayCalendar();

btnEl.forEach((btn) => {
  btn.addEventListener("click", () => {

    let newMonth = btn.id === "prev" ? month - 1 : month + 1;
    let newYear = year;

    if (newMonth < 0) {
      newMonth = 11;
      newYear--;
    }

    if (newMonth > 11) {
      newMonth = 0;
      newYear++;
    }

    window.location.href = `/calendar?month=${newMonth + 1}&year=${newYear}`;
  });
});
