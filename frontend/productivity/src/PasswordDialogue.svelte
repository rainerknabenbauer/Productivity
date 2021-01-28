<script>
    import { createEventDispatcher } from "svelte";
    import Button from "./Button.svelte";
    const dispatch = createEventDispatcher();

    export let project;
    let password = "";
    let button = "Unlock!";

    function checkPassword() {
        if (password == project.email) {
            dispatch("unlock");
            button = "unlocked";
        } else {
            button = "Wrong password";
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

<div class="wrapper">
    <div class="passwordMessage">This project is locked.<br>Please enter your eMail address.</div>
    <textarea class="textarea" autofocus bind:value={password}></textarea>
	<br><Button text={button} on:click={checkPassword} />
</div>