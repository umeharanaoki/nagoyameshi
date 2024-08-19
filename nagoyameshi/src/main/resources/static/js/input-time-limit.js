const openingTimeInput = document.getElementById('#openingTimeInput');
const closingTimeInput = document.getElementById('#closingTimeInput');

openingTimeInput.addEventListener('input', function() {
	const [hours, minutes] = this.value.split(':').map(Number);
	const roundedMinutes = Math.round(minutes / 15) * 15;
	const formattedMinutes = String(roundedMinutes).padStart(2, '0');
	this.value = `${String(hours).padStart(2, '0')}:${formattedMinutes}`;
});

closingTimeInput.addEventListener('input', function() {
	const [hours, minutes] = this.value.split(':').map(Number);
	const roundedMinutes = Math.round(minutes / 15) * 15;
	const formattedMinutes = String(roundedMinutes).padStart(2, '0');
	this.value = `${String(hours).padStart(2, '0')}:${formattedMinutes}`;
});