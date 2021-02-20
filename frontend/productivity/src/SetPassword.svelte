<script>
    import { createEventDispatcher } from 'svelte';
    import Credentials from './Credentials.js';

    const dispatch = createEventDispatcher();

    export let project;
    let password = "";

    const host = window.location.hostname;

    function showSetPassword() {
        dispatch('showSetPassword');
	}

    async function setPassword() {
        project.isProtected = !(password == "")

        let credentials = new Credentials();
        credentials.projectId = project.projectId;
        credentials.password = password;

        console.log("SetPassword = " + credentials)
        
		let result = await fetch("https://" + host + ":8443/auth", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(credentials)
        });

        console.log("SetPassword.returnedToken = " + JSON.stringify(result))

        document.cookie = "token=" + result.token + ";path=/; max-age=31536000;SameSite=Lax"; 

        saveProject(project);
        showSetPassword();
    }
    
    function saveProject(project) {
		fetch("https://" + host + ":8443/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(project)
        });
	}
</script>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="shortDescription">
            Protect your project, set a password.
        </div>
        <!-- svelte-ignore a11y-autofocus -->
        <textarea class="token" autofocus bind:value={password} />
        <button on:click={setPassword}>Set password</button>
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

    .token {
        width: 100%;
    }
</style>
