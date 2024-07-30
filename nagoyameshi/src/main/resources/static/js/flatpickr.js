// 3か月後まで予約可能
let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr('#reservationDate', {
	locale: 'ja',
	dateFormat: "Y-m-d",
	minDate: 'today' ,
	maxDate: maxDate
});

flatpickr('#reservationTime', {
	enableTime: true,
	noCalendar: true,
	dateFormat: "H:i",
	time_24hr: true,
	minTime: openingTime,
	maxTime: closingTime
});