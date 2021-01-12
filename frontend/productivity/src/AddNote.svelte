<script>
    import Button from './Button.svelte';
    import Task from './Task.js';
    import { createEventDispatcher } from 'svelte';

    $: selected = "title";

    function loadPage(name) {
        selected = name;
    }

    const dispatch = createEventDispatcher();

    function refresh() {
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

        refresh();
    }
</script>

<style>
* {
    position: relative;
  box-sizing: border-box;
  z-index: 5;
}

/* Style the side navigation */

.containers { 
    display: flex;
    flex-direction: row;
    justify-content: center;
    border-style: solid;
    border-width: 1px;
    width: 45%;
}

.sidenav {
  height: 100%;
  min-width: 190px;
  position: relative;
  top: 0;
  left: 0;
  overflow-x: hidden;
  padding: 10px 0px 10px 0px;
  margin: 0px;
  border-right: dashed;
  border-width: 1px;
}


/* Side navigation links */
.sidenavelement {
  padding: 3px 10px;
  text-decoration: none;
  display: block;
  width: 100%;
  letter-spacing: 0.1px;
  text-decoration: none;
  font-family: 'Verdana';
}

/* Change color on hover */
.sidenavelement:hover {
  background-color: #ddd;
  color: black;
}

/* Style the content */
    .textarea {
        margin: 0px;
        padding: 4px 7px 4px 7px;
        height: 100%;
        width: 100%;
        resize: none;
    }
    .textarea:focus {
        outline: none;
    }
    .content {
        width: 100%;
    }
    .save {
        background-color: rgb(190, 154, 160);
        display: flex;
        justify-content: center;
        margin-top: 7px;
    }

</style>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="sidenavelement" on:click={() => loadPage("title")}>Titel</div>
        <div class="sidenavelement" on:click={() => loadPage("shortDescription")}>Short description</div>
        <div class="sidenavelement" on:click={() => loadPage("longDescription")}>Long description</div>
        <div class="sidenavelement" on:click={() => loadPage("additionalNotes")}>Additional notes</div>
        <div class="sidenavelement" on:click={() => loadPage("timePeriod")}>Time period</div>
        <div class="sidenavelement" on:click={() => loadPage("priority")}>Priority</div>
        <div class="sidenavelement" on:click={() => loadPage("leadingTasks")}>Leading tasks</div>
        <div class="sidenavelement" on:click={() => loadPage("followingTasks")}>Following Tasks</div>
        <div class="sidenavelement save" on:click={addTask}>SAVE</div>
    </div>
    
    <div class="content">
        {#if selected.localeCompare("title") === 0}
        <textarea class="textarea" bind:value={task.title}></textarea>
        {:else if selected.localeCompare("shortDescription") === 0}
        <textarea class="textarea" bind:value={task.description.shortDescription}></textarea>
        {:else if selected.localeCompare("longDescription") === 0}
        <textarea class="textarea" bind:value={task.description.longDescription}></textarea>
        {:else if selected.localeCompare("additionalNotes") === 0}
        <textarea class="textarea" bind:value={task.description.additionalNotes}></textarea>
        {:else if selected.localeCompare("timePeriod") === 0}
        [(1-24][h]<br>[_] weeks<br>...
        {:else if selected.localeCompare("priority") === 0}
        <textarea class="textarea" bind:value={task.priority}></textarea>
        {:else if selected.localeCompare("leadingTasks") === 0}
        <textarea class="textarea" bind:value={task.preDependency}></textarea>
        {:else if selected.localeCompare("followingTasks") === 0}
        <textarea class="textarea" bind:value={task.postDependency}></textarea>
        {:else}
        <div class=""><Button text="add" on:click={addTask} /></div>
        {/if}
    </div>
</div>



