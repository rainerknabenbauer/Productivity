<script>
    import { createEventDispatcher, onMount } from 'svelte';
    const dispatch = createEventDispatcher();

    export let host;

    let isRecoveryVisible = false;

    let email;

    function createProject() {
        dispatch("createProject")
    }

    function showRecovery() {
        isRecoveryVisible = !isRecoveryVisible
    }

    async function sendEmail() {
        showRecovery();

        if (!(email === undefined)) {
            fetch("http://" + host + ":8080/email", {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                    'Media-Type': "MediaType.APPLICATION_JSON"
                },
                body: JSON.stringify(email)
            });
        } else {
            console.log("email undefined")
        }
    }

    function validateEmail() {
        var button = document.getElementById("sendButton");
        if(email.match(/[A-z0-9]+@[A-z0-9]+.[A-z]{2,3}/g)) {
            button.disabled = false;
        } else {
            button.disabled = true;
        }
    }
</script>

<style>
.wrapper {
    width: 100%;
    height: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.video {
    width: 50%;
    height: 50%;
    border: none;
    padding: 3px;
}

.textarea {
        margin: 0px;
        padding: 4px 7px 4px 7px;
        width: 225px;
        line-height: 0.75;
        resize: none;
    }
.recovery {
    display: inline-block
}
.recoveryButton {
    font-size: 9pt;
}

</style>

<div class="wrapper">
        <button on:click={createProject}>Create new project</button>
        <iframe src="https://player.vimeo.com/video/504868454" class="video" title="Create a task" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {#if isRecoveryVisible}
        <div class="recovery">
            <input class="textarea" bind:value={email} on:input={validateEmail} on:/>
            <button id="sendButton" class="recoveryButton" disabled="true" on:click={sendEmail}>send</button>
        </div>
        {:else}
        <button on:click={showRecovery}>Send recovery eMail</button>
        {/if}
        
</div>
