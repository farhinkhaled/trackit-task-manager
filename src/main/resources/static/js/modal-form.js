var form = document.getElementById("openForm");

var btn = document.getElementById("addTaskBtn");

var createBtn = document.getElementById("createButton");

var editBtn = document.querySelectorAll('.edit-button');

var closeForm = document.getElementsByClassName("closeForm")[0];

var backdrop = document.getElementById("backdrop");

document.getElementById("addTaskBtn").addEventListener("click", () => {
  form.querySelector("input[name='id']").value = "";
  form.querySelector("#title").value = "";
  form.querySelector("#deadline").value = "";
  form.querySelector("#priority").value = "high";
  form.querySelector("#description").value = "";

  form.style.display = "block";
  backdrop.classList.add("active");
});

document.querySelectorAll(".edit-button").forEach(btn => {
  btn.addEventListener("click", (e) => {
    e.preventDefault();

    form.querySelector("input[name='id']").value = btn.dataset.id;
    form.querySelector("#title").value = btn.dataset.title;
    form.querySelector("#deadline").value = btn.dataset.deadline;
    form.querySelector("#priority").value = btn.dataset.priority;
    form.querySelector("#description").value = btn.dataset.description;

    form.style.display = "block";
    backdrop.classList.add("active");
  });
});

createBtn.onclick = function() {
  form.style.display = "none";
  backdrop.classList.remove("active");
}

closeForm.onclick = function() {
  form.style.display = "none";
  backdrop.classList.remove("active");
}

form.addEventListener("click", (e) => {
  e.stopPropagation();
});

backdrop.addEventListener("click", () => {
  form.style.display = "none";
  backdrop.classList.remove("active");
});

