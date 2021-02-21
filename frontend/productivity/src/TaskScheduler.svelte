<script>
    import { onMount } from 'svelte';

    export let task;

    let daysLeft;
    let deadline;
    let notifyInDays;
    let notifyDaysBeforeDeadline;

    onMount(async () => {
        deadline = new Date().toISOString().slice(0,10);

        if (task.deadline != undefined) {
            deadline = task.deadline;
            calculateRelativeDays();
        }
    })

    Date.prototype.addDays = function (days) {
        let date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
    }

    Date.prototype.subtractDays = function (days) {
        let date = new Date(this.valueOf());
        date.setDate(date.getDate() - days);
        return date;
    }

    function calculateRelativeDays() {
        let partialDaysLeft; 

        calculateDaysLeft();

        partialDaysLeft = (new Date(task.notifyRelativeDate).getTime() - new Date().getTime()) / (1000 * 3600 * 24);
        notifyInDays = Math.ceil(partialDaysLeft);

        partialDaysLeft = (new Date(task.deadline).getTime() - new Date(task.notifyDateBeforeDeadline).getTime()) / (1000 * 3600 * 24);
        notifyDaysBeforeDeadline = Math.ceil(partialDaysLeft);

    }

    async function resetField() {
        let date = document.getElementById("date");
        date.style.color = "black";
    }

    async function calculateDeadline() {
        deadline = new Date().addDays(daysLeft).toISOString().slice(0,10);
        task.deadline = deadline;
    }

    async function calculateDaysLeft() {
        if (deadline.length == 10) {
            let partialDaysLeft = (new Date(deadline).getTime() - new Date().getTime()) / (1000 * 3600 * 24);
            daysLeft = Math.ceil(partialDaysLeft);
        }
    }

    async function calculateReminder() {
        task.notifyRelativeDate = new Date().addDays(notifyInDays).toISOString().slice(0,10);
    }

    async function calculateDeadlineReminder() {
        task.notifyDateBeforeDeadline = new Date(deadline).subtractDays(notifyDaysBeforeDeadline).toISOString().slice(0,10);
    }

</script>

<style>
    .wrapper {
        height: 100%;
        margin: 3px;
        text-align: center;
    }
    .input {
        outline: none;
    }
    .days {
        width: 21pt;
        height: 18pt;
    }
    .date {
        width: 70pt;
        height: 18pt;
        margin-left: 2px;
        color: darkgrey;
    }
    .info {
        color: #9c9c9c;
    }

    /* Chrome, Safari, Edge, Opera */
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    /* Firefox */
    input[type=number] {
        -moz-appearance: textfield; 
    }
</style>

<div class="wrapper">
    <br>This task should be completed in <input class="input days" type="number" maxlength="2" bind:value={daysLeft} on:input={calculateDeadline} /> {#if daysLeft==1} day.{:else} days.{/if}
    <br>This task should be completed until <input id="date" class="input date" type="text" maxlength="10" bind:value={deadline} on:input={calculateDaysLeft} on:focus={resetField} />
    
    <br><br>Remind me in <input class="input days" type="number" maxlength="2" bind:value={notifyInDays} on:input={calculateReminder} /> {#if notifyInDays==1} day.{:else} days.{/if}
    <br>Remind me <input class="input days" type="number" maxlength="2" bind:value={notifyDaysBeforeDeadline} on:input={calculateDeadlineReminder} /> {#if notifyDaysBeforeDeadline==1} day {:else} days{/if} before deadline.

    <br><br><br><br><span class="info">You will receive an eMail on the day specified.</span>
</div>