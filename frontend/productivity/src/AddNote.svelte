<script>
    import Button from './Button.svelte';
    import Task from './Task.js';
    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher();

    function sayHello() {
		dispatch('refresh', {
			text: 'refresh'
		});
	}

    let task = new Task();

    async function addTask() {
        console.log(JSON.stringify(task));
        const response = await fetch('http://localhost:8080/tasks', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(task)
        }).then(data => {
            console.log(data)
        });

        sayHello();
    }
</script>

<style>
    .main {
        background-color: lightgrey;
        position: absolute;
        z-index: 10;
        display: grid;
        grid-template-columns: auto auto;
        border: 1px solid darkgreen;
    }

    .name {
        border: 1px solid darkgreen;
        padding: 3px;
        letter-spacing: 1px;
    }

    .input {
        border: 1px solid darkgreen;
        padding: 0px;
        margin: 0px;
        min-width: 200px;
    }

    .textarea {
        margin: 0px;
    }

</style>

<div class="main">
    <div class="name">Titel</div>
    <div class="input"><textarea class="textarea" bind:value={task.title}></textarea></div>

    <div class="name">Short Description</div>
    <div class="input"><textarea class="textarea" bind:value={task.description.shortDescription}></textarea></div>

    <div class="name">Long Description</div>
    <div class="input"><textarea class="textarea" bind:value={task.description.longDescription}></textarea></div>

    <div class="name">Additional notes</div>
    <div class="input"><textarea class="textarea" bind:value={task.description.additionalNotes}></textarea></div>

    <div class="name">Time period</div>   <!-- Give relative period, eg 6 weeks, calculate exakt date from the input -->
    <div class="input">[(1-24][h]<br>[_] weeks<br>...</div>
    
    <div class="name">Priority</div>
    <div class="input" type="number"><textarea class="textarea" bind:value={task.priority}></textarea></div>

    <div class="name">Leading tasks</div>
    <div class="input"><textarea class="textarea" bind:value={task.preDependency}></textarea></div>

    <div class="name">Following Tasks</div>
    <div class="input"><textarea class="textarea" bind:value={task.postDependency}></textarea></div>
    
    <div>Add Task</div>
    <div class=""><Button text="add" on:click={addTask} /></div>
</div>


