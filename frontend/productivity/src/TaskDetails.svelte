<script>
    import Button from './Button.svelte';
    import { createEventDispatcher, onMount } from 'svelte';
    import LinkTask from './LinkTask.svelte';
    import Session from './Session.js'
    import TaskScheduler from './TaskScheduler.svelte';
import Priority from './Priority.svelte';

    export let project;
    export let tasks;
    export let task;

    const dispatch = createEventDispatcher();
    let selected = "title";
    const host = window.location.hostname;

    onMount(async () => {
        if(task.projectId === undefined) {
            task.projectId = project.projectId;
        }
    });

    function loadPage(name) {
        selected = name;
    }

    function refresh(session) {
		dispatch('refresh', {
            text: session,
        });
	}

    async function addTask() {

        let token = getCookieValue("token");
        const session = new Session();
        session.projectId = project.projectId;
        session.token = token;

        const response = await fetch("https://" + host + ":8443/tasks", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': "application/json",
                'Media-Type': "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(session)
            },
            body: JSON.stringify(task)
        });

        refresh(session);
    }

    function getCookieValue(name) {
        let result = document.cookie.match("(^|[^;]+)\\s*" + name + "\\s*=\\s*([^;]+)");
        return result ? result.pop() : "";
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
    box-shadow: 0 3px 5px 0 rgba(0,0,0,.1);
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
        background-color: rgb(247, 247, 247);
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
        <div class="sidenavelement" on:click={() => loadPage("title")}>Titel {#if task.title !== ""}<div class="characters">{getLength(task.title)}</div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("shortDescription")}>Short description {#if task.description.shortDescription !== ""}<div class="characters">{getLength(task.description.shortDescription)}</div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("longDescription")}>Long description {#if task.description.longDescription !== ""}<div class="characters">{getLength(task.description.longDescription)}</div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("additionalNotes")}>Additional notes {#if task.description.additionalNotes !== null && task.description.additionalNotes !== undefined}<div class="characters">{getLength(task.description.additionalNotes)}</div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("timePeriod")}>Deadline {#if task.deadline !== null && task.deadline !== undefined}<div class="characters"><i class="fa fa-check" /></div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("priority")}>Priority {#if task.priority !== 0}<div class="characters">{task.priority}</div>{/if}</div>
        <div class="sidenavelement" on:click={() => loadPage("parentTasks")}>Link to task </div>
        <div class="sidenavelement save" on:click={addTask}>SAVE</div>
    </div>
    
    <div class="content">
        {#if selected.localeCompare("title") === 0}
        <!-- svelte-ignore a11y-autofocus -->
        <textarea class="textarea" autofocus bind:value={task.title}></textarea>
        {:else if selected.localeCompare("shortDescription") === 0}
        <!-- svelte-ignore a11y-autofocus -->
        <textarea class="textarea" autofocus bind:value={task.description.shortDescription}></textarea>
        {:else if selected.localeCompare("longDescription") === 0}
        <!-- svelte-ignore a11y-autofocus -->
        <textarea class="textarea" autofocus bind:value={task.description.longDescription}></textarea>
        {:else if selected.localeCompare("additionalNotes") === 0}
        <!-- svelte-ignore a11y-autofocus -->
        <textarea class="textarea" autofocus bind:value={task.description.additionalNotes}></textarea>
        {:else if selected.localeCompare("timePeriod") === 0}
        <TaskScheduler bind:project={project} bind:task={task} on:showReminder />
        {:else if selected.localeCompare("priority") === 0}
        <Priority />
        {:else if selected.localeCompare("parentTasks") === 0}
        <LinkTask {tasks} {task} />
        {:else}
        <div class=""><Button text="add" on:click={addTask} /></div>
        {/if}
    </div>
</div>



