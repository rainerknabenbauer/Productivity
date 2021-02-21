<script>
    import { onMount } from "svelte";

    const days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
    const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    let weekday;
    let day;
    let month;
    let year;

    onMount(async () => {

        let date = new Date();

        console.log(date)

        weekday = days[date.getDay()];
        day = date.getDate();
        month = months[date.getMonth()];
        year = date.getFullYear();

    })
</script>

<style>
.wrapper {
    border-width: 1px;
    border-style: solid;
}
.weekday {
    padding-right: 10px;
    font-size: 8pt;
}
</style>

<div class="wrapper">
    <span class="weekday">{weekday}</span> <span>{day}</span> <span>{month}</span> <span>{year}</span>
</div>