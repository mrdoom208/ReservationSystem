document.getElementById("reservationForm").addEventListener("submit", function (e) {
  e.preventDefault();

  // Get user input
  const name = document.getElementById("name").value;
  const phone = document.getElementById("phone").value;
  const capacity = document.getElementById("capacity").value;
  const space = document.querySelector('input[name="space"]:checked').value;

  // Generate random reference and queue
  const ref = Math.floor(1000 + Math.random() * 9000);
  const queue = Math.floor(1 + Math.random() * 5);

  // Display details
  document.getElementById("displayName").textContent = name;
  document.getElementById("displayPhone").textContent = phone;
  document.getElementById("displayRef").textContent = ref;
  document.getElementById("displayQueue").textContent = queue;
  document.getElementById("displayCapacity").textContent = capacity + " Person";
  document.getElementById("displaySpace").textContent = space;

  // Switch to ticket view
  document.getElementById("form-section").classList.add("hidden");
  document.getElementById("ticket-section").classList.remove("hidden");

  // Start countdown timer (example 30s for demo)
  let seconds = 30;
  const timerDisplay = document.getElementById("time");
  const interval = setInterval(() => {
    const mins = String(Math.floor(seconds / 60)).padStart(2, "0");
    const secs = String(seconds % 60).padStart(2, "0");
    timerDisplay.textContent = `${mins}:${secs}`;
    if (seconds === 0) clearInterval(interval);
    seconds--;
  }, 1000);
});

// Reset form to make another reservation
document.getElementById("newReservation").addEventListener("click", () => {
  document.getElementById("reservationForm").reset();
  document.getElementById("ticket-section").classList.add("hidden");
  document.getElementById("form-section").classList.remove("hidden");
});
