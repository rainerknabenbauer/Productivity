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
    width: 50%;
    right: 8px;
    box-shadow: 0 3px 5px 0 rgba(0,0,0,.1);
}

.sidenav {
  height: 315px;
  width: 55%;
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

.general {
    width: 100%;
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
.description:hover, .general:hover{
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
        <div class="sidenavelement general" on:click={() => loadPage("theidea")}>The idea behind it</div>
        <div class="navigationWrapper" on:click={() => loadPage("create")}>
            <div class="sidenavelement topic task">Task</div>
            <div class="sidenavelement description">Create</div>
        </div>
        <div class="navigationWrapper" on:click={() => loadPage("edit")}>
            <div class="sidenavelement topic task">Task</div>
            <div class="sidenavelement description">Edit</div>
        </div>
        <div class="navigationWrapper" on:click={() => loadPage("deleteAndRecover")}>
            <div class="sidenavelement topic task">Task</div>
            <div class="sidenavelement description">Delete and recover</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic task" on:click={() => loadPage("link")}>Task</div>
            <div class="sidenavelement description" on:click={() => loadPage("link")}>Visualize a link</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic project" on:click={() => loadPage("protect")}>Project</div>
            <div class="sidenavelement description" on:click={() => loadPage("protect")}>Make it private</div>
        </div>
        <div class="navigationWrapper">
            <div class="sidenavelement topic project" on:click={() => loadPage("spacer")}>tbd</div>
            <div class="sidenavelement description" on:click={() => loadPage("spacer")}>tbd</div>
        </div>
    </div>
    
    <div class="content">
        {#if selected.localeCompare("theidea") === 0}
        <div class="">
            [introduction video]
            <br>
            <br>New here?
            <br>Check out the videos on the left.
            <br>Doesn't look like a button yet, but it's clickable.

        </div>
        {:else if selected.localeCompare("create") === 0}
        <iframe src="https://player.vimeo.com/video/503035883" class="video" title="Create a task" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else if selected.localeCompare("edit") === 0}
        <iframe src="https://player.vimeo.com/video/503031847" class="video" title="Edit a task" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else if selected.localeCompare("deleteAndRecover") === 0}
        <iframe src="https://player.vimeo.com/video/503038887" class="video" title="Delete and recover a task" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else if selected.localeCompare("link") === 0}
        <iframe src="https://player.vimeo.com/video/504803215" class="video" title="Create a link" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else if selected.localeCompare("protect") === 0}
        <iframe src="https://player.vimeo.com/video/505820837" class="video" title="Protect a project" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
        {:else}
        <div class="">nothing specified yet</div> 
        {/if}

    </div>
</div>



