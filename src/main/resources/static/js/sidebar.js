const ROUTES = {
    home: "/home",
    tasks: "/task-page",
    calendar: "/calendar",
    notifications: "/notifications"
};

const logoutURL = "/logout"

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
                <a class="sidebar-options" href="${ROUTES.home}">Home</a>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/tasks.svg">
                <a class="sidebar-options" href="${ROUTES.tasks}">Tasks</a>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons" src="../images/sidebar/calendar.svg">
                <a class="sidebar-options" href="${ROUTES.calendar}">Calendar</a>
            </div>
            <div class="sidebar-navs">
                <img class="sidebar-icons notification-icon" src="../images/sidebar/notification.svg">
                <a class="sidebar-options" href="${ROUTES.notifications}">Notifications</a>
            </div>
        </div>

        <div class="logout-section">
            <img
                src="../images/sidebar/logout.svg"
                class="sidebar-icons"
            >
            <a class="sidebar-options" href="${logoutURL}">Logout</a>
        </div>
    </div>
`