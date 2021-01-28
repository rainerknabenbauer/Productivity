<script>
    import { createEventDispatcher, onMount } from "svelte";
    import Button from "./Button.svelte";
    const dispatch = createEventDispatcher();

    export let project;
    let password = "";
    let button = "Unlock!";

    onMount(() => {
        verifyCookie();
    })

    function verifyCookie() {
        let verification = getCookieValue("email");
        
        if (!(verification === undefined)) {
            password = verification;
            if (checkPassword()) {
                dispatch("unlock");
            }
        }
    }

    function getCookieValue(name) {
        let result = document.cookie.match("(^|[^;]+)\\s*" + name + "\\s*=\\s*([^;]+)");
        return result ? result.pop() : "";
    }

    function checkInput() {
        if (checkPassword()) {
            dispatch("unlock");
            document.cookie = "email=" + project.email; 
        } else {
            button = "Wrong password";
        }
    }

    function checkPassword() {
        return password == project.email;
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

<div class="wrapper">
    <div class="passwordMessage">This project is locked.<br>Please enter your eMail address.</div>
    <textarea class="textarea" autofocus bind:value={password}></textarea>
	<br><Button text={button} on:click={checkInput} />
</div>