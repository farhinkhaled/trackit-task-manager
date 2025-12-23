document.addEventListener("DOMContentLoaded", () => {

    const total = taskStats.total || 1;

    console.log(taskStats.completed);

    createChart("progressChart", taskStats.inProgress, total, "#498dd0");
    createChart("overdueChart", taskStats.overdue, total, "#E67474");
    createChart("completedChart", taskStats.completed, total, "#00CC66");
});

function createChart(chartId, value, total, colorCode){
    const percentage = Math.round((value / total) * 100);
    const remaining = 100 - percentage;
    const ctx = document.getElementById(chartId);

    new Chart(ctx, {
        type: "doughnut",
        data: {
            datasets: [{
                data: [percentage, remaining],
                backgroundColor: [
                    colorCode,
                    "#eeeeee"
                ],
                borderWidth: 0
            }]
        },
        options: {
            cutout: "80%",
            responsive: true,
            plugins: {
                legend: { display: false },
                tooltip: { enabled: false }
            }
        },
        plugins: [{
            id: "centerText",
            beforeDraw(chart) {
                const { width } = chart;
                const { ctx } = chart;
                ctx.restore();

                const fontSize = (width / 5).toFixed(2);
                ctx.font = `bold ${fontSize}px Roboto`;
                ctx.textBaseline = "middle";
                ctx.fillStyle = "#000";

                const text = percentage + "%";
                const textX = Math.round((width - ctx.measureText(text).width) / 2);
                const textY = chart._metasets[0].data[0].y;

                ctx.fillText(text, textX, textY);
                ctx.save();
            }
        }]
    });
}
