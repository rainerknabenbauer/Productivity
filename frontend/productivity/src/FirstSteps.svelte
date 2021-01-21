<script>
    import Button from './Button.svelte';

    let selected = "theidea";

    const host = window.location.hostname;

    function loadPage(name) {
        selected = name;
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
    right: 8px;
}

.sidenav {
  height: 315px;
  width: 50%;
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
  letter-spacing: 0.1px;
  text-decoration: none;
  font-family: 'Verdana';
}

.topic {
    display: inline;
    width: 30%;
}

.task {
    background-color: rgb(146, 146, 146);
    display: inline;
    width: 30%;
}

.project {
    background-color: rgb(116, 116, 116);
    display: inline;
    width: 30%;
}

.description {
    width: 65%;
    display: inline;
}

/* Change color on hover */
.description:hover {
  background-color: #e9e9e9;
  color: black;
}

.navigationWrapper {
    display: flex
}

/* Style the content */
.content {
    width: 100%;
    height: 315px;
}
.video {
    width: 100%;
    height: 100%;
    border: none;
    padding: 3px;
}
</style>

<div class="containers w3-light-grey">
    <div class="sidenav">
        <div class="sidenavelement inline" on:click={() => loadPage("theidea")}>The idea behind it</div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic task" on:click={() => loadPage("spacer")}>Task</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>Create</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic task" on:click={() => loadPage("spacer")}>Task</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>Modify</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic task" on:click={() => loadPage("spacer")}>Task</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>Delete</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic project" on:click={() => loadPage("spacer")}>Project</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>Create</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic" on:click={() => loadPage("spacer")}>tbd</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>tbd</div>
        </div>
    </div>
    
    <div class="content">
        {#if selected.localeCompare("theidea") === 0}
        <iframe src="https://player.vimeo.com/video/3873878" class="video" title="tut" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else if selected.localeCompare("spacer") === 0}
        spacer
        {:else}
        <div class="">nothing specified yet</div>
        {/if}
    </div>
</div>



