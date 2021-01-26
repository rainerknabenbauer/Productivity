<script>
    import { onMount } from "svelte";
    import MoveableBlock from "./MoveableBlock.svelte";
    import TaskDetails from "./TaskDetails.svelte";
    import Task from "./Task.js";
    import QRcode from "./QRcode.svelte";
    import Reminder from "./Reminder.svelte";
    import Canvas from "./Canvas.svelte";
    import ActionItems from "./ActionItems.svelte";
    import Faq from "./FAQ.svelte";
    import Trashbin from "./Trashbin.svelte";
    import NotImplementedView from "./NotImplementedView.svelte";
    import FirstSteps from "./FirstSteps.svelte";

    export let project;
    export let tasks;

    let task;

    let isTaskDetailsVisible = false;
    let isReminderVisible = false;
    let isFAQvisible = false;
    let isTrashbinVisible = false;
    let isHistoryVisible = false;
    let isFirstStepsVisible = false;


    onMount(async () => {
        console.log(tasks)
		drawLines();
    });

    function closeAllViews() {
        isReminderVisible = false;
        isFAQvisible = false;
        isTrashbinVisible = false;
        isHistoryVisible = false;
        isFirstStepsVisible = false;
    }

    function toggleTaskDetailsVisibility(taskJson) {
        let state = isTaskDetailsVisible;
        closeAllViews();
        isTaskDetailsVisible = !state;
        task = taskJson === null ? new Task() : JSON.parse(taskJson);
    }

    function showReminder() {
        let state = isReminderVisible;
        closeAllViews();
        isReminderVisible = !state;
    }

    function showFAQ() {
        let state = isFAQvisible;
        closeAllViews();
        isFAQvisible = !state;
    }

    function showTrashbin() {
        let state = isTrashbinVisible;
        closeAllViews();
        isTrashbinVisible = !state;
    }

    function showHistory() {
        let state = isHistoryVisible;
        closeAllViews();
        isHistoryVisible = !state;
    }

    function showFirstSteps() {
        let state = isFirstStepsVisible;
        closeAllViews();
        isFirstStepsVisible = !state;
    }

    async function addTask() {
		tasksPromise = getTasks();
		isTaskDetailsVisible = !isTaskDetailsVisible;
	}

    async function drawLines() {
        var wrapper = document.getElementById("canvas-wrapper");
        var canvas = document.getElementById("canvas");
        var context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.width, canvas.height);

        tasks.forEach((task) => {
            if (!task.isDeleted) {
                context.beginPath();

                if (task.parentTasks === undefined) {
                    context.moveTo(
                        window.outerWidth / 2,
                        window.outerHeight / 2 - wrapper.offsetTop,
                        0
                    );
                } else {
                    // move context pointer to center of parent task
                }

                context.lineTo(
                    task.ui.xposition - wrapper.offsetLeft + 175,
                    task.ui.yposition - wrapper.offsetTop + 25
                );

                context.strokeStyle = "#dddddd";
                context.stroke();
                context.closePath();
            }
        });
    }
</script>

<ActionItems
    {project}
    on:showTaskDetails={() => toggleTaskDetailsVisibility(null)}
    on:showReminder={showReminder}
    on:showFAQ={showFAQ}
    on:showTrashbin={showTrashbin}
    on:showHistory={showHistory}
    on:showFirstSteps={showFirstSteps}
    on:saveProject
    on:showPinboard={closeAllViews}
/>

{#if isTaskDetailsVisible}
    <TaskDetails on:refresh={addTask} {tasks} {task} />
{/if}

{#each tasks as task (task.id)}
    {#if !task.isDeleted}
        <MoveableBlock
            {task}
            on:edit={(e) => toggleTaskDetailsVisibility(e.detail.text)}
            on:move={() => drawLines()}
            on:deleteTask={() => {
                isTaskDetailsVisible = false;
                drawLines();
            }}
        />
    {/if}
{/each}

{#if isReminderVisible}
    <Reminder {project} on:showReminder={showReminder} />
{/if}

{#if isFAQvisible}
    <Faq {project} />
{/if}

{#if isTrashbinVisible}
    <Trashbin {tasks} on:undoDelete />
{/if}

{#if isHistoryVisible}
    <NotImplementedView />
{/if}

{#if isFirstStepsVisible}
    <FirstSteps />
{/if}

<Canvas />

<QRcode />

<style>
</style>
