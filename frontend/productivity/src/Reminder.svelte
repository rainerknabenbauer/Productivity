<script>
    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher();

    export let project;

    const host = window.location.hostname;

    function showReminder() {
        dispatch('showReminder');
	}

    function bindEmail() {
        if (project.email == "") {
            project.isProtected = false;
        }
        document.cookie = "email=" + project.email + ";path=/; max-age=31536000;Lax"; 
        
		fetch("https://" + host + ":8443/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(project)
        });
        showReminder();
	}
</script>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="shortDescription">
            Did you know you could link your eMail to your projects?
        </div>
        <div class="shortDescription">
            Never again lose your notes because you forgot the link.
        </div>
        <textarea class="email" autofocus bind:value={project.email} />
        <button on:click={bindEmail} >Set reminder</button>
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
        width: 35%;
        right: 8px;
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
        padding: 5px;
    }

    .email {
        width: 100%;
    }
</style>
