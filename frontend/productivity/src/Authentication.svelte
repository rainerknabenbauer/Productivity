<script>
    import { createEventDispatcher, onMount } from "svelte";
    import Button from './Button.svelte';
    import Authentication from './Authentication';
    const dispatch = createEventDispatcher();

    export let project;
    export let host;

    let token = "";
    let button = "Unlock!";
    let showPassword = false;

    onMount(async () => {

        if (project.isProtected) {
            verifyCookie();
            authenticate();
        } else {
            successfulAuthentication();
        }
    })

    function successfulAuthentication() {
        dispatch("authenticated");
    }

    function authenticate() {
        if (token != "") {
            const authentication = new Authentication();
            authentication.projectId = project.projectId;
            authentication.token = token;
            document.cookie = "token=" + authentication.token + ";path=/; max-age=31536000;SameSite=Lax"; 
            authenticationRequest(authentication);
        } else {
            showPassword = true;
        }
    }

    async function authenticationRequest(authentication) {
        let result = await fetch("https://" + host + ":8443/auth", {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': "application/json",
                'Media-Type': "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(authentication)
            }
        });

        if (result.ok) {
            console.log("Authentication success")
            successfulAuthentication();
        } else {
            console.log("Authentication failed")
            showPassword = true;
        }
    }

    function verifyCookie() {
        let verification = getCookieValue("token");
        
        if (!(verification === undefined)) {
            token = verification;
        }
    }

    function getCookieValue(name) {
        let result = document.cookie.match("(^|[^;]+)\\s*" + name + "\\s*=\\s*([^;]+)");
        return result ? result.pop() : "";
    }

</script>

<style>
    .passwordMessage {
        padding-bottom: 5px;
    }
    .wrapper {
        padding: 70px 0;
        text-align: center;
    }
    .textarea {
        margin: 0px;
        padding: 4px 7px 4px 7px;
        width: 350px;
        height: 22pt;
        resize: none;
        text-align: center;
    }

    .textarea:focus {
        outline: none;
    }
</style>

{#if showPassword}
    <div class="wrapper">
        <div class="passwordMessage">This project is locked.<br>Please provide your password.</div>
        <!-- svelte-ignore a11y-autofocus -->
        <input class="textarea" autofocus bind:value={token} />
        <br><Button text={button} on:click={authenticate} />
    </div>
{/if}