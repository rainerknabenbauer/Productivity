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
    import SetPassword from './SetPassword.svelte';

    const dispatch = createEventDispatcher();

    export let project;
    export let tasks;

    let task;

    let isTaskDetailsVisible = false;
    let isReminderVisible = false;
    let isFAQvisible = false;
    let isTrashbinVisible = false;
    let isHistoryVisible = false;
    let isFirstStepsVisible = false;
    let isSetTokenVisible = false;
    let focusFollowsMouse = false;


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

                let focusX = window.outerWidth / 2;
                let focusY = window.outerHeight / 2;

                if (project.ui.xposition != 0 && project.ui.yposition != 0) {
                    focusX = project.ui.xposition;
                    focusY = project.ui.yposition;
                }

                if (task.parentTasks != null && task.parentTasks.length > 0) {

                    task.parentTasks.forEach(parent => {
                        let connections = tasks.filter(task => task.id == parent);

                        for (const connection of connections) {
                            
                            if (connection.isDeleted) {

                                context.moveTo(
                                    focusX,
                                    focusY - wrapper.offsetTop,
                                    0
                                );
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
                        focusX,
                        focusY - wrapper.offsetTop,
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

    function showSetPassword() {
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

    function repositionFocus() {
        focusFollowsMouse = !focusFollowsMouse;
    }

    async function addTask(authentication) {
		dispatch("saveTask", {
            text: authentication,
        });
		isTaskDetailsVisible = !isTaskDetailsVisible;
	}

    function canvasReady() {
        drawLines();

        var wrapper = document.getElementById("canvas-wrapper");
        
        // Set focus point
        wrapper.addEventListener("click", (event) => {
            if (focusFollowsMouse) {
                focusFollowsMouse = false;
                dispatch('saveProject', {
			        text: JSON.stringify(project)
		        });
            }
        });

        // Calculate position of focus point
        wrapper.addEventListener("mousemove", (event) => {
            if (focusFollowsMouse) {
                project.ui.xposition = event.clientX;
                project.ui.yposition = event.clientY;
                drawLines();
            }
        });
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
    on:showSetPassword={showSetPassword}
    on:repositionFocus={repositionFocus}
    />

    {#if isTaskDetailsVisible}
    <TaskDetails {project} {tasks} {task} 
    on:refresh={event => addTask(event.detail.text)} 
    on:showReminder={showReminder}
    />
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
    <SetPassword bind:project={project} on:showSetPassword={showSetPassword} />
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

    <Canvas on:canvasReady={canvasReady} />

    <QRcode />



<style>
</style>
