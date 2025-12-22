// Get the modal
var confirmationModal = document.getElementById("confirmationModel");

// Get the <span> element that closes the modal
var closeModal = document.getElementsByClassName("cancle-button")[0];

var backdrop = document.getElementById("backdrop");

// When the user clicks on the button, open the modal
document.querySelectorAll(".delete-button").forEach(btn => {
    btn.addEventListener("click", () => {
        selectedTaskId = btn.dataset.id;

        confirmationModal.style.display = "block";
        backdrop.classList.add("active");
    });
});

document.getElementById("confirmDeleteBtn").addEventListener("click", () => {
    if (selectedTaskId) {
        window.location.href = `/task/delete/${selectedTaskId}`;
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

