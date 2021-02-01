<script>
    import { createEventDispatcher, onMount } from 'svelte';
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
    import SetToken from './SetToken.svelte';
import { text } from 'svelte/internal';

    const dispatch = createEventDispatcher();

    export let project;
    export let tasks;

    let task;

    let isLocked = true;
    let isTaskDetailsVisible = false;
    let isReminderVisible = false;
    let isFAQvisible = false;
    let isTrashbinVisible = false;
    let isHistoryVisible = false;
    let isFirstStepsVisible = false;
    let isSetTokenVisible = false;


    onMount(async () => {
        document.title = project.name == undefined ? "loading" : project.name;
    });

    async function drawLines() {
        var wrapper = document.getElementById("canvas-wrapper");
        var canvas = document.getElementById("canvas");
        var context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.width, canvas.height);

        tasks.forEach((task) => {
            if (!task.isDeleted) {
                context.beginPath();

                if (task.parentTasks != null && task.parentTasks.length > 0) {

                    task.parentTasks.forEach(parent => {
                        let connections = tasks.filter(task => task.id == parent);

                        for (const connection of connections) {
                            
                            if (connection.isDeleted) {
                                context.moveTo(
                                window.outerWidth / 2,
                                window.outerHeight / 2 - wrapper.offsetTop,
                                0);
                            } else {
                                context.moveTo(
                                    connection.ui.xposition - wrapper.offsetLeft + 175,
                                    connection.ui.yposition - wrapper.offsetTop + 25,
                                    0);
                            }

                            context.lineTo(
                                task.ui.xposition - wrapper.offsetLeft + 175,
                                task.ui.yposition - wrapper.offsetTop + 25
                            );

                            context.strokeStyle = "#dddddd";
                            context.stroke();
                            context.closePath();
                        };
                    });
                } else {
                    context.moveTo(
                        window.outerWidth / 2,
                        window.outerHeight / 2 - wrapper.offsetTop,
                        0
                    );

                    context.lineTo(
                    task.ui.xposition - wrapper.offsetLeft + 175,
                    task.ui.yposition - wrapper.offsetTop + 25
                    );

                    context.strokeStyle = "#dddddd";
                    context.stroke();
                    context.closePath();
                }
            }
        });
    }

    function closeAllViews() {
        isReminderVisible = false;
        isSetTokenVisible = false;
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

    function showSetToken() {
        let state = isSetTokenVisible;
        closeAllViews();
        isSetTokenVisible = !state;
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

    async function addTask(authentication) {
		dispatch("saveTask", {
            text: authentication,
        });
		isTaskDetailsVisible = !isTaskDetailsVisible;
	}
</script>

    <ActionItems
    bind:project={project}
    on:showTaskDetails={() => toggleTaskDetailsVisibility(null)}
    on:showReminder={showReminder}
    on:showFAQ={showFAQ}
    on:showTrashbin={showTrashbin}
    on:showHistory={showHistory}
    on:showFirstSteps={showFirstSteps}
    on:saveProject
    on:logout
    on:showPinboard={closeAllViews}
    on:showSetToken={showSetToken}
    />

    {#if isTaskDetailsVisible}
    <TaskDetails {project} {tasks} {task} on:refresh={event => addTask(event.detail.text)} />
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
    <Reminder bind:project={project} on:showReminder={showReminder} />
    {/if}

    {#if isSetTokenVisible}
    <SetToken bind:project={project} on:showSetToken={showSetToken} />
    {/if}

    {#if isFAQvisible}
    <Faq bind:project={project} />
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

    <Canvas on:canvasReady={drawLines} />

    <QRcode />



<style>
</style>
