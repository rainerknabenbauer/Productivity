<script>
    import { createEventDispatcher, onMount } from "svelte";
    import Button from './Button.svelte';
    import Credentials from "./Credentials";
    import Session from './Session.js';
    const dispatch = createEventDispatcher();

    export let project;
    export let host;

    let password = "";
    let token = "";
    let button = "Unlock!";
    let showPassword = false;

    onMount(async () => {

        document.title = project.name == undefined ? "Sesame open..?" : project.name;

        if (project.isProtected) {
            verifyCookie();
            authenticate();
        } else {
            successfulAuthentication("");
        }
    })

    function successfulAuthentication(session) {
        dispatch("authenticated", {
            text: session,
        });
    }

    function authenticate() {
        if (token != "") {
            const session = new Session();
            session.projectId = project.projectId;
            session.token = token;

            sessionRequest(session);
        } else {
            showPassword = true;
        }
    }

    async function sessionRequest(session) {

        console.log("Session Request: " + session)

        let result = await fetch("https://" + host + ":8443/session", {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': "application/json",
                'Media-Type': "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(session)
            }
        });

        if (result.ok) {
            console.log("Authentication success with " + session.token + " and " + session.projectId)
            successfulAuthentication(session);
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

    async function recovery() {
        if (!(project.email === undefined && project.email == "")) {
            const response = await fetch("https://" + host + ":8443/projects/recovery/", {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                    'Media-Type': "MediaType.APPLICATION_JSON"
                },
                body: project.projectId
            });
        } else {
            console.log("email undefined")
        }
    }

    async function login() {
        const credentials = new Credentials();
        credentials.projectId = project.projectId;
        credentials.password = password;

        loginRequest(credentials)
    }

    async function loginRequest(credentials) {
        let result = await fetch("https://" + host + ":8443/auth", {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': "application/json",
                'Media-Type': "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(credentials)
            }
        });

        if (result.ok) {
            console.log("Authentication success")

            let session = await result.json()
            document.cookie = "token=" + session.token + ";path=/; max-age=31536000;SameSite=Lax"; 

            successfulAuthentication(Session.from(session));
        } else {
            console.log("Authentication failed")
            showPassword = true;
        }
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
        <input class="textarea" type="password" autofocus bind:value={password} />
        <br><Button text={button} on:click={login} />
        <Button text="Send recovery eMail" on:click={recovery} />
    </div>
{/if}