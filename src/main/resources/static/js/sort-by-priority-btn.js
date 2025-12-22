const tabs = document.querySelectorAll('.sort-by-priority-btn');

console.log(tabs);

tabs.forEach((tab, index) => {
    tab.addEventListener('click', (e)=>{
        tabs.forEach(tab=>{tab.classList.remove('active-tab')});
        tab.classList.add('active-tab');

        var underline = document.querySelector('.underline');
        underline.style.width = e.target.offsetWidth + "px";
        underline.style.left = e.target.offsetLeft + "px";
    })
})