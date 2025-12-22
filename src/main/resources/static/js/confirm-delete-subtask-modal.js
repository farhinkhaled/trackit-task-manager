// Get the modal
var confirmationModal = document.getElementById("confirmationModel");

// Get the <span> element that closes the modal
var closeModal = document.getElementsByClassName("cancle-button")[0];

var backdrop = document.getElementById("backdrop");

// When the user clicks on the button, open the modal
document.querySelectorAll(".delete-button").forEach(btn => {
    btn.addEventListener("click", () => {
        selectedSubTaskId = btn.dataset.id;

        confirmationModal.style.display = "block";
        backdrop.classList.add("active");
    });
});

document.getElementById("confirmDeleteBtn").addEventListener("click", () => {
    if (selectedSubTaskId) {
        window.location.href = `/sub-task/delete/${selectedSubTaskId}`;
    }
});

closeModal.onclick = function() {
  confirmationModal.style.display = "none";
  backdrop.classList.remove("active");
}

confirmationModal.addEventListener("click", (e) => {
  e.stopPropagation();
});

backdrop.addEventListener("click", () => {
  confirmationModal.style.display = "none";
  backdrop.classList.remove("active");
});

