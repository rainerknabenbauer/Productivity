<script>
    import { createEventDispatcher } from 'svelte';
    import Session from './Session.js'
    const dispatch = createEventDispatcher();

    export let tasks;
    const host = window.location.hostname;

    async function recover(task) {
        task.isDeleted = false;
        dispatch('undoDelete', {
			text: task.projectId
        });

        let token = getCookieValue("token");
        const session = new Session();
        session.projectId = task.projectId;
        session.token = token;
        
        const response = await fetch("https://" + host + ":8443/tasks", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(session)
            },
            body: JSON.stringify(task)
        });
    }

    function getCookieValue(name) {
        let result = document.cookie.match("(^|[^;]+)\\s*" + name + "\\s*=\\s*([^;]+)");
        return result ? result.pop() : "";
    }
</script>

<div class="containers w3-light-grey">
    <div class="sidenav">
        {#each tasks as task (task.id)}
            {#if task.isDeleted}
                <div class="sidenavelement" on:click={recover(task)}>
                    {task.title}
                </div>
            {/if}
        {/each}
    </div>
</div>

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
        right: 8px;

        max-height: 60%;
        overflow-y: auto;
        box-shadow: 0 3px 5px 0 rgba(0,0,0,.1);
    }

    .sidenav {
        height: 100%;
        width: 100%;
        position: relative;
        top: 0;
        left: 0;
        overflow-x: hidden;
        padding: 10px 0px 10px 0px;
        margin: 0px;
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
        font-family: "Verdana";
    }

    /* Change color on hover */
    .sidenavelement:hover {
        background-color: #e9e9e9;
        color: black;
    }
</style>
