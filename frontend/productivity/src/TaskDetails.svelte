<script>
    import Button from './Button.svelte';
    import { createEventDispatcher, onMount } from 'svelte';
    import Task from './Task';
    import LinkTask from './LinkTask.svelte';

    export let tasks;
    export let task;

    const dispatch = createEventDispatcher();
    let selected = "title";
    const host = window.location.hostname;

    onMount(async () => {
        console.log(tasks)
        if(task === undefined) {
            task = new Task();
        }
    });

    function loadPage(name) {
        selected = name;
    }

    function refresh() {
		dispatch('refresh', {
			text: 'refresh'
		});
	}

    async function addTask() {
        const response = await fetch("http://" + host + ":8080/tasks", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(task)
        });

        refresh();
    }

    function getLength(node) {
        if (node === undefined) {
            return 0;
        }
        return node.length;
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
    position: absolute;
    display: flex;
    flex-direction: row;
    justify-content: center;
    border-style: solid;
    border-width: 1px;
    border-color: lightslategray;
    width: 45%;
}

.sidenav {
  height: 100%;
  min-width: 200px;
  position: relative;
  top: 0;
  left: 0;
  overflow-x: hidden;
  padding: 10px 0px 10px 0px;
  margin: 0px;
  border-right: dashed;
  border-width: 1px;
  background-color: #dddddd;
}


/* Side navigation links */
.sidenavelement {
  padding: 3px 10px;
  text-decoration: none;
  width: 100%;
  letter-spacing: 0.1px;
  text-decoration: none;
  font-family: 'Verdana';
}

/* Change color on hover */
.sidenavelement:hover {
  background-color: #e9e9e9;
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
        background-color: #999999;
        display: flex;
        justify-content: center;
        margin-top: 7px;
    }

    .save:hover {
        background-color: #f1f1f1;
    }

    .characters {
        float: right;
    }
</style>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="sidenavelement" on:click={() => loadPage("title")}>Titel <div class="characters">{getLength(task.title)}</div></div>
        <div class="sidenavelement" on:click={() => loadPage("shortDescription")}>Short description <div class="characters">{getLength(task.description.shortDescription)}</div></div>
        <div class="sidenavelement" on:click={() => loadPage("longDescription")}>Long description <div class="characters">{getLength(task.description.longDescription)}</div></div>
        <div class="sidenavelement" on:click={() => loadPage("additionalNotes")}>Additional notes <div class="characters">{task.description.additionalNotes === null ? "" : getLength(task.description.additionalNotes)}</div></div>
        <div class="sidenavelement" on:click={() => loadPage("timePeriod")}>Time period <div class="characters">x</div></div>
        <div class="sidenavelement" on:click={() => loadPage("priority")}>Priority <div class="characters">{task.priority}</div></div>
        <div class="sidenavelement" on:click={() => loadPage("followup")}>Link to task </div>
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
        <textarea class="textarea" disabled="true" bind:value={task.priority}></textarea>
        {:else if selected.localeCompare("followup") === 0}
        <LinkTask {tasks} {task} />
        {:else}
        <div class=""><Button text="add" on:click={addTask} /></div>
        {/if}
    </div>
</div>



