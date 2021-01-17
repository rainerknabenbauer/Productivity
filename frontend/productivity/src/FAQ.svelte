<script>
    import Button from './Button.svelte';

    let selected = "sendEmail";
    let email;

    function loadPage(name) {
        selected = name;
    }

    async function sendEmail() {
        if (!(email === undefined)) {
            console.log("sending email: " + email)
            const response = await fetch('http://localhost:8080/email', {
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
</script>

<style>
* {
    position: relative;
    box-sizing: border-box;
    z-index: 5;
}

/* Style the side navigation */

.containers { 
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: center;
    border-style: solid;
    border-width: 1px;
    border-color: lightslategray;
    width: 45%;
    float: right;
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
</style>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="sidenavelement" on:click={() => loadPage("sendEmail")}>Lost projects?</div>
        <div class="sidenavelement" on:click={() => loadPage("spacer")}>spacer</div>
        <div class="sidenavelement" on:click={() => loadPage("spacer")}>spacer</div>
    </div>
    
    <div class="content">
        {#if selected.localeCompare("sendEmail") === 0}
        <textarea class="sendEmail textarea" bind:value={email}></textarea>
        <Button text="eMail my projects" on:click={sendEmail} /> 
        {:else if selected.localeCompare("spacer") === 0}
        spacer
        {:else}
        <div class="">nothing specified yet</div>
        {/if}
    </div>
</div>



