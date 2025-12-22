document.querySelector('.sidebar').innerHTML = 
`
    <div>
        <div class="logo-section">
            <img 
                class="logo" 
                src="../images/logo/logo.png"
            >
        </div>
        <div class="navs-section">
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/home.svg">
                <span class="sidebar-options">Home</span>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/tasks.svg">
                <span class="sidebar-options">Tasks</span>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/calendar.svg">
                <span class="sidebar-options">Calendar</span>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/notification.svg">
                <span class="sidebar-options">Notifications</span>
            </div>
        </div>
    </div>
    <div class="mod-user-info">
        <div class="user-navs">
            <img 
                src="../images/sidebar/profile.jpg"
                class="profile-picture"
            >
            <span class="sidebar-options">Farhin Khaled</span>
            <img
                src="../images/sidebar/three-dots.png"
                class="three-dots-icon"
            >
        </div>
    </div>
`